package kr.gaion.armoredVehicle.algorithm.clustering;

import kr.gaion.armoredVehicle.algorithm.AlgorithmConfig;
import kr.gaion.armoredVehicle.algorithm.MLAlgorithm;
import kr.gaion.armoredVehicle.algorithm.ModelUtilService;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseStatus;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.ClusterTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClusterResponse;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.algorithm.featureSelector.PcaDimensionalityReduction;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.DatabaseSparkService;
import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
import lombok.NonNull;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.functions$;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import scala.jdk.CollectionConverters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Log4j2
public abstract class ClusterMlAlgorithm<TModel> extends MLAlgorithm<ClusterTrainInput, BaseAlgorithmPredictInput> {
    private final PcaDimensionalityReduction dimensionalityReduction;

    public ClusterMlAlgorithm(@NonNull ElasticsearchSparkService elasticsearchSparkService, @NonNull DatabaseSparkService databaseSparkService, @NonNull Utilities utilities, @NonNull StorageConfig storageConfig, @NonNull ModelUtilService modelUtil, @NonNull FSChiSqSelector chiSqSelector, @NonNull AlgorithmConfig algorithmConfig, @NonNull DataConfig dataConfig, @NonNull SparkSession sparkSession, @NonNull String algorithmName, @NonNull ModelService modelService, @NonNull PcaDimensionalityReduction dimensionalityReduction) {
        super(elasticsearchSparkService, databaseSparkService, utilities, storageConfig, modelUtil, chiSqSelector, algorithmConfig, dataConfig, sparkSession, algorithmName, modelService);
        this.dimensionalityReduction = dimensionalityReduction;
    }

    @Override
    public ClusterResponse predict(BaseAlgorithmPredictInput config) throws Exception {
        System.out.println("Start detecting outliers in data ..");

        Dataset<Row> orgData = this.getUnlabeledData(config);

        /* Preprocessing data */
        String idColName = config.getClassCol();
        List<String> fieldsForPredict = config.getListFieldsForPredict();
        var listColNames = Arrays.asList(orgData.columns());
        boolean isTagAvailable = listColNames.contains("tagging");

        for (String featureField : fieldsForPredict) {
            orgData = orgData.withColumn(featureField, convertStringToDoubleUDF().apply(orgData.col(featureField).cast(DataTypes.StringType)));
        }
        var fData = new VectorAssembler("cfa")
                .setInputCols(fieldsForPredict.toArray(new String[0]))
                .setOutputCol("features")
                .transform(orgData);
        var prepInputDF = fData.withColumn("label", fData.col(idColName));

        Dataset<Row> data = config.isDimensionalityReduction()
                ? PcaDimensionalityReduction.computePcaDataframeApiFromDF(config, prepInputDF)
                : prepInputDF.select(ClusterResponse.ID_COLUMN,
                ClusterResponse.FEATURES);
        /* End of preprocessing data */

        String modelDir = this.getModelFullPath(config.getModelName());
        var predictedData = predictUnlabeledData(data, isTagAvailable, config.isDimensionalityReduction(), modelDir);

        ClusterResponse response = new ClusterResponse(ResponseType.OBJECT_DATA);
        response.setStatus(ResponseStatus.SUCCESS);
        response.setListFeatures(fieldsForPredict.toArray(new String[0]));

        var enrichedPredictedData = this.enrichPredictedData(config, predictedData);
        Dataset<Row> testClusterDf = enrichedPredictedData
                .withColumn("v2a", functions$.MODULE$.vector_to_array(enrichedPredictedData.col("features"), "float64"))
                .withColumns(
                        CollectionConverters.asScalaBuffer(fieldsForPredict).toSeq(),
                        CollectionConverters.asScalaBuffer(
                                IntStream.range(0, fieldsForPredict.size())
                                        .mapToObj(index -> functions.col("v2a").getItem(index))
                                        .collect(Collectors.toList())).toSeq())
                .drop("features")
                .drop("v2a");
        var maxResults = testClusterDf.count();
        var predictionInfo = (Row[]) testClusterDf.take((int) maxResults);
        response.setPredictionInfo(
                Arrays.stream(predictionInfo)
                        .map(row -> row.mkString(","))
                        .collect(Collectors.toList()));

        return response;
    }

    protected Dataset<Row> enrichPredictedData(BaseAlgorithmPredictInput input, Dataset<Row> predictedData) throws Exception {
        return predictedData.withColumnRenamed("prediction", "clusterId");
    }

    protected abstract Dataset<Row> predictUnlabeledData(
            Dataset<Row> data, boolean isTagAvailable, boolean dimensionalityReductionEnableFlg, String modelDir
    );

    protected abstract TModel trainModel(ClusterTrainInput input, Dataset<Row> trainData);

    protected abstract Dataset<Row> predictData(Dataset<Row> input, TModel model);

    @Override
    public ClusterResponse train(ClusterTrainInput config) throws Exception {
        Dataset<Row> trainData;

//        if (config.isFeaturesSelectionEnableFlg()) {
//            // get input data
//            trainData = this.dimensionalityReduction.computePcaDataframeApi(config);
//        } else {
////      // get input data
////      trainData = this.elasticsearchSparkService.getLabeledDatasetFromElasticsearch(config);
//            trainData = this.databaseSparkService.getLabeledDatasetFromDatabase(config);
//        }

        Dataset<Row> originalData = this.databaseSparkService.getLabeledDatasetFromDatabase(config);
        StringIndexerModel labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("index").fit(originalData);
        Dataset<Row> indexedData = labelIndexer.transform(originalData);
//        String[] indicesLabelsMapping = labelIndexer.labels();
        String[] indicesLabelsMapping = {"normal", "outlier"};
        // 2. Split the data into train and test
        var splitData = this.splitTrainTest(indexedData, config.getLSeed(), config.getFraction());
        var train = splitData[0];
        var test = splitData[1];

        TModel model = this.trainModel(config, train);
        // map data to return

        Dataset<Row> predictions = this.predictData(test, model);
        Dataset<Row> trainedDfWithArrayFeatures = predictions.select("label", "features", "prediction")
                .withColumn("v2a", functions$.MODULE$.vector_to_array(originalData.col("features"), "float64"));
        Dataset<Row> resultDf = trainedDfWithArrayFeatures
                .withColumns(CollectionConverters.asScalaBuffer(config.getFeatureCols()).toSeq(),
                        CollectionConverters.asScalaBuffer(IntStream.range(0, config.getFeatureCols().size())
                                .mapToObj(index -> functions.col("v2a").getItem(index)).collect(Collectors.toList())).toSeq());

        // response to client
        ClusterResponse response = new ClusterResponse(ResponseType.OBJECT_DATA);

        JavaPairRDD<Object, Object> predictionAndLabelRdd = zipPredictResult(predictions);

        var maxResults = resultDf.count();
        var resultArray = (Row[]) resultDf.drop("v2a", "features").take((int) maxResults);
        response.setPredictionInfo(Arrays.stream(resultArray).map(row -> row.mkString(",")).collect(Collectors.toList()));
        response.setListFeatures(config.getFeatureCols().toArray(new String[0]));
        response.setIdCol(config.getClassCol());
        response.setStatus(ResponseStatus.SUCCESS);
        // PCA is applied or not?
        response.setProcessed(config.isFeaturesSelectionEnableFlg());

        var metrics = new MulticlassMetrics(predictionAndLabelRdd.rdd());

        response.setConfusionMatrix(metrics.confusionMatrix().toArray());
        response.setLabels(indicesLabelsMapping);
        this.doSaveModel(model, config);
        this.modelService.insertNewMlResponse(response, this.algorithmName, config.getModelName(), config.getPartType(), config.getFileName());
        this.modelUtil.saveTrainedResults(config, response, this.algorithmName);

//        enrichTrainResponse(response, model, resultDf, config);
//        log.debug("trained successfully.");
        return response;
    }

    protected abstract void doSaveModel(TModel model, ClusterTrainInput input) throws Exception;

    protected void enrichTrainResponse(ClusterResponse response, TModel model, Dataset<Row> resultDf, ClusterTrainInput config) throws Exception {
    }
}
