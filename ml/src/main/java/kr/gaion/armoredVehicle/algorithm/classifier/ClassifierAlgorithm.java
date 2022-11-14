package kr.gaion.armoredVehicle.algorithm.classifier;

import kr.gaion.armoredVehicle.algorithm.AlgorithmConfig;
import kr.gaion.armoredVehicle.algorithm.MLAlgorithm;
import kr.gaion.armoredVehicle.algorithm.ModelUtilService;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseStatus;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClassificationResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.RandomForestClassificationResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.SVMClassificationResponse;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.DatabaseSparkService;
import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.classification.*;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
public abstract class ClassifierAlgorithm<T> extends MLAlgorithm<BaseAlgorithmTrainInput, BaseAlgorithmPredictInput> {
    public ClassifierAlgorithm(@NonNull ElasticsearchSparkService elasticsearchSparkService, @NonNull DatabaseSparkService databaseSparkService,
                               @NonNull Utilities utilities, @NonNull StorageConfig storageConfig, @NonNull ModelUtilService modelUtil,
                               @NonNull FSChiSqSelector chiSqSelector, @NonNull AlgorithmConfig algorithmConfig, @NonNull DataConfig dataConfig,
                               @NonNull SparkSession sparkSession, @NonNull ModelService modelService, @NonNull String algorithmName) {
        super(elasticsearchSparkService, databaseSparkService, utilities, storageConfig, modelUtil, chiSqSelector, algorithmConfig, dataConfig, sparkSession, algorithmName, modelService);
    }

    protected static JavaRDD<String> predictLabelAndVector(Dataset<Row> trainingResults, String[] indicesLabelsMapping, String csvDelimiter) {
        return trainingResults.select("prediction", "index", "features").toJavaRDD().map(new Function<>() {    // #PC0026
            private static final long serialVersionUID = -6554874834801818033L;

            public String call(Row row) {
                StringBuilder lineBuilder = new StringBuilder();
                int index = Double.valueOf(row.getDouble(0)).intValue();
                String str = indicesLabelsMapping[index];
                lineBuilder.append('"').append(str).append('"');
                lineBuilder.append(csvDelimiter);
                lineBuilder.append('"').append(indicesLabelsMapping[Double.valueOf(row.getDouble(1)).intValue()]).append('"');
                lineBuilder.append(csvDelimiter);
                StringBuilder featuresBuilder = new StringBuilder(row.get(2).toString());  // features | vector
                lineBuilder.append(featuresBuilder.deleteCharAt(0).deleteCharAt(featuresBuilder.length() - 1));
                return lineBuilder.toString();
            }
        });
    }

    protected static JavaRDD<LabeledPoint> convertToLabeledPoint(Dataset<Row> indexedData) {
        return indexedData.select("index", "features").toJavaRDD().map(new Function<>() {
            private static final long serialVersionUID = -8008845071144389624L;

            @Override
            public LabeledPoint call(Row row) {
                double label = row.getDouble(0);
                var vector = (org.apache.spark.ml.linalg.Vector) row.get(1);
                return new LabeledPoint(label, Vectors.dense(vector.toArray()));
            }
        });
    }

    private static JavaRDD<String> doPredictClassificationScaledData(Dataset<Row> predictedResultUnion) {
        Dataset<Row> resultDataset = predictedResultUnion.drop("index").drop("features").drop("scaledFeatures").drop("rawPrediction").drop("probability");

        List<String> listColNames_result = List.of(resultDataset.columns());

        return resultDataset.toJavaRDD().map(new Function<>() {
            private static final long serialVersionUID = 7065916945772988691L;

            @Override
            public String call(Row rowData) {
                StringBuilder lineBuilder = new StringBuilder();
                for (int iter = 0; iter < listColNames_result.size(); ++iter) {
                    if (rowData.get(iter) == null) {
                        lineBuilder.append('"').append('"');
                    } else {
                        lineBuilder.append('"').append(rowData.get(iter)).append('"');
                    }
                    lineBuilder.append(",");
                }
                lineBuilder.deleteCharAt(lineBuilder.length() - 1);  // delete last delimiter (redundant)
                return lineBuilder.toString();
            }
        });
    }

    public static Seq<String> convertListToSeq(List<String> inputList) {
        return JavaConverters.asScalaIteratorConverter(inputList.iterator()).asScala().toSeq();
    }

    @Override
    public ClassificationResponse train(BaseAlgorithmTrainInput config) throws Exception {
        System.out.println("Starting train model ..");

        // Get all settings sent through REST-client
        double fraction = config.getFraction();
        long lSeed = config.getLSeed();

        if (config.isFeaturesSelectionEnableFlg()) {
            config.setFeatureCols(Arrays.asList(this.chiSqSelector.selectFeaturesDataframeApi(config)));
        }

        Dataset<Row> originalData = this.databaseSparkService.getLabeledDatasetFromDatabase(config);
        StringIndexerModel labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("index").fit(originalData);
        Dataset<Row> indexedData = labelIndexer.transform(originalData);
        String[] indicesLabelsMapping = labelIndexer.labels();

        // Split the data into train and test
        var splitData = this.splitTrainTest(indexedData, lSeed, fraction);
        var train = splitData[0];
        var test = splitData[1];

        var model = trainModel(config, train, indicesLabelsMapping.length);

        System.out.println("Saving model ..");
        var modelFullPathName = this.saveTrainedModel(config, model);
        labelIndexer.save(modelFullPathName);

        var response = createModelResponse(model, test, config);

        var trainingResults = getTrainingResults(model, test);
        JavaPairRDD<Object, Object> predictionAndLabelRdd = zipPredictResult(trainingResults);

        response.setLabels(indicesLabelsMapping);

        JavaRDD<String> predictedLabelAndVector = predictLabelAndVector(trainingResults, indicesLabelsMapping, this.storageConfig.getCsvDelimiter());

        this.populateResponseFromMetrics(response, new MulticlassMetrics(predictionAndLabelRdd.rdd()), config, predictedLabelAndVector, config.getFeatureCols().toArray(new String[0]));

        return response;
    }

    @Override
    public final ClassificationResponse predict(BaseAlgorithmPredictInput input) throws IOException {
        System.out.println("Start predicting unlabeled data ..");

        var modelName = input.getModelName();
        var data = this.getUnlabeledData(input);

        // 2. load model
        T model = this.loadModel(modelName);
        StringIndexerModel labelIndexer = StringIndexerModel.load(this.getModelIndexerPath(modelName));
        String[] indicesLabelsMapping = labelIndexer.labels();

        var response = predictData(data, model, input, indicesLabelsMapping);
        response.setListFeatures(input.getListFieldsForPredict().toArray(new String[0]));
        response.setClassCol(input.getClassCol());

        System.out.println("predicted unlabeled data successfully.");

        response.setStatus(ResponseStatus.SUCCESS);

        return response;
    }

    public final ClassificationResponse trainSVC(BaseAlgorithmTrainInput config) throws Exception {
        System.out.println("Start SVC Model training with labeled data ...");

        // 0. get settings
        int maxIterations = config.getMaxIter();
        double fraction = config.getFraction();
        long lSeed = config.getLSeed();

        // 1. load Data
        Dataset<Row> originalData = this.databaseSparkService.getLabeledDatasetFromDatabase(config);
        StringIndexerModel labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("index").fit(originalData);
        Dataset<Row> indexedData = labelIndexer.transform(originalData);
        String[] indicesLabelsMapping = labelIndexer.labels();

        // 2. Split the data into train and test
        var splitData = this.splitTrainTest(indexedData, lSeed, fraction);
        var train = splitData[0];
        var test = splitData[1];

        // 3. make model
        LinearSVC ls = new LinearSVC()
                .setMaxIter(maxIterations)
                .setFeaturesCol("features")
                .setLabelCol("index");

        // 4. train
        LinearSVCModel lsModel = ls.fit(train);

        // 5. Make predictions(validation).
        Dataset<Row> predictions = lsModel.transform(test);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ predictions @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        predictions.show(false);

        // 6. Save model
        this.saveModel(config, lsModel);
        this.saveModelIndexer(config, labelIndexer);

        // 7. response
        var response = new SVMClassificationResponse(ResponseType.OBJECT_DATA);

        JavaPairRDD<Object, Object> predictionAndLabelRdd = zipPredictResult(predictions);

        JavaRDD<String> predictedLabelAndVector = predictLabelAndVector(predictions, indicesLabelsMapping, this.storageConfig.getCsvDelimiter());

        response.setLabels(indicesLabelsMapping);

        // 8. model evaluation
        this.populateResponseFromMetrics(response,
                new MulticlassMetrics(predictionAndLabelRdd.rdd()), config, predictedLabelAndVector,
                config.getFeatureCols().toArray(new String[0]));

        return response;
    }

    public final ClassificationResponse trainMLP(BaseAlgorithmTrainInput config) throws Exception {
        System.out.println("Start MLP Model training with labeled data ...");

        // 0. get settings
        int maxIterations = config.getMaxIter();
        int blockSize = config.getBlockSize();
        long seed = config.getSeed();
        int[] layers = new int[config.getLayers()];
        double fraction = config.getFraction();
        long lSeed = config.getLSeed();

        // 1. load Data
        Dataset<Row> originalData = this.databaseSparkService.getLabeledDatasetFromDatabase(config);
        StringIndexerModel labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("index").fit(originalData);
        Dataset<Row> indexedData = labelIndexer.transform(originalData);
        String[] indicesLabelsMapping = labelIndexer.labels();

        // 2. Split the data into train and test
        var splitData = this.splitTrainTest(indexedData, lSeed, fraction);
        var train = splitData[0];
        var test = splitData[1];

        // 3. make model
        String[] listSelectedFeatures = config.getFeatureCols().toArray(new String[0]);

        int numOfClasses = indicesLabelsMapping.length;
        layers[0] = listSelectedFeatures.length;
        layers[1] = numOfClasses;

        MultilayerPerceptronClassifier mlp = new MultilayerPerceptronClassifier()
                .setBlockSize(blockSize)
                .setLayers(layers)
                .setSeed(seed)
                .setMaxIter(maxIterations)
                .setLabelCol("index")
                .setFeaturesCol("features");

        // 4. train
        MultilayerPerceptronClassificationModel mlpModel = mlp.fit(train);

        // 5. Make predictions(validation).
        Dataset<Row> predictions = mlpModel.transform(test);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ predictions @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        predictions.show(false);

        // 6. Save model
        this.saveModel(config, mlpModel);
        this.saveModelIndexer(config, labelIndexer);

        // 7. response
        var response = new ClassificationResponse(ResponseType.OBJECT_DATA);

        JavaPairRDD<Object, Object> predictionAndLabelRdd = zipPredictResult(predictions);

        JavaRDD<String> predictedLabelAndVector = predictLabelAndVector(predictions, indicesLabelsMapping, this.storageConfig.getCsvDelimiter());

        response.setLabels(indicesLabelsMapping);

        // 8. model evaluation
        this.populateResponseFromMetrics(response,
                new MulticlassMetrics(predictionAndLabelRdd.rdd()), config, predictedLabelAndVector,
                config.getFeatureCols().toArray(new String[0]));

        return response;
    }

    public final ClassificationResponse trainLR(BaseAlgorithmTrainInput config) throws Exception {
        System.out.println("Start LR Model training with labeled data ...");

        // 0. get settings
        int maxIterations = config.getMaxIter();
        double regParam = config.getRegParam();
        double elasticNetParam = config.getElasticNetMixing();
        boolean fitIntercept = config.isIntercept();
        double fraction = config.getFraction();
        long lSeed = config.getLSeed();

        // 1. load Data
        Dataset<Row> originalData = this.databaseSparkService.getLabeledDatasetFromDatabase(config);
        StringIndexerModel labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("index").fit(originalData);
        Dataset<Row> indexedData = labelIndexer.transform(originalData);
        String[] indicesLabelsMapping = labelIndexer.labels();

        // 2. Split the data into train and test
        var splitData = this.splitTrainTest(indexedData, lSeed, fraction);
        var train = splitData[0];
        var test = splitData[1];

        // 3. make model
        var lr = new LogisticRegression()
                .setRegParam(regParam)
                .setElasticNetParam(elasticNetParam)
                .setMaxIter(maxIterations)
                .setFitIntercept(fitIntercept)
                .setFeaturesCol("features")
                .setLabelCol("index");

        // 4. train
        LogisticRegressionModel lrModel = lr.fit(train);

        // 5. Make predictions(validation).
        Dataset<Row> predictions = lrModel.transform(test);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ predictions @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        predictions.show(false);

        // 6. Save model
        this.saveModel(config, lrModel);
        this.saveModelIndexer(config, labelIndexer);

        // 7. response
        var response = new ClassificationResponse(ResponseType.OBJECT_DATA);

        JavaPairRDD<Object, Object> predictionAndLabelRdd = zipPredictResult(predictions);

        JavaRDD<String> predictedLabelAndVector = predictLabelAndVector(predictions, indicesLabelsMapping, this.storageConfig.getCsvDelimiter());

        response.setLabels(indicesLabelsMapping);

        // 8. model evaluation
        this.populateResponseFromMetrics(response,
                new MulticlassMetrics(predictionAndLabelRdd.rdd()), config, predictedLabelAndVector,
                config.getFeatureCols().toArray(new String[0]));

        return response;
    }

    public final ClassificationResponse trainRF(BaseAlgorithmTrainInput config) throws Exception {
        System.out.println("Start RF Model training with labeled data ...");

        // 0. get settings
        int numTrees = config.getNumTrees();
        String featureSubsetStrategy = config.getFeatureSubsetStrategy();
        String impurity = config.getImpurity();
        int maxDepths = config.getMaxDepths();
        int maxBins = config.getMaxBins();
        double fraction = config.getFraction();
        long lSeed = config.getLSeed();

        // 1. load Data
        Dataset<Row> originalData = this.databaseSparkService.getLabeledDatasetFromDatabase(config);
        StringIndexerModel labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("index").fit(originalData);
        Dataset<Row> indexedData = labelIndexer.transform(originalData);
        String[] indicesLabelsMapping = labelIndexer.labels();

        // 2. Split the data into train and test
        var splitData = this.splitTrainTest(indexedData, lSeed, fraction);
        var train = splitData[0];
        var test = splitData[1];

        // 3. make model
        var rf = new RandomForestClassifier()
                .setFeatureSubsetStrategy(featureSubsetStrategy)
                .setImpurity(impurity)
                .setMaxBins(maxBins)
                .setMaxDepth(maxDepths)
                .setNumTrees(numTrees)
                .setFeaturesCol("features")
                .setLabelCol("index");

        // 4. train
        RandomForestClassificationModel rfModel = rf.fit(train);

        // 5. Make predictions(validation).
        Dataset<Row> predictions = rfModel.transform(test);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ predictions @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        predictions.show(false);

        // 6. Save model
        this.saveModel(config, rfModel);
        this.saveModelIndexer(config, labelIndexer);

        // 7. response
        var response = new RandomForestClassificationResponse(ResponseType.OBJECT_DATA);

        JavaPairRDD<Object, Object> predictionAndLabelRdd = zipPredictResult(predictions);

        JavaRDD<String> predictedLabelAndVector = predictLabelAndVector(predictions, indicesLabelsMapping, this.storageConfig.getCsvDelimiter());

        response.setLabels(indicesLabelsMapping);

        // 8. model evaluation
        this.populateResponseFromMetrics(response,
                new MulticlassMetrics(predictionAndLabelRdd.rdd()), config, predictedLabelAndVector,
                config.getFeatureCols().toArray(new String[0]));

        return response;
    }

    public final ClassificationResponse predictSVC(BaseAlgorithmPredictInput input) throws IOException {
        System.out.println("Start SVC Model predicting with unlabeled data ...");

        // 0. setting
        var modelName = input.getModelName();
        var unlabeledData = this.getUnlabeledData(input);
        var selectedUnlabeledData = unlabeledData.selectExpr(convertListToSeq(input.getListFieldsForPredict()));

        // 1. load model and labelIndexer
        var model = LinearSVCModel.load(this.getModelFullPath(modelName));
        StringIndexerModel labelIndexer = StringIndexerModel.load(this.getModelIndexerPath(modelName));
        String[] indicesLabelsMapping = labelIndexer.labels();

        // 2. Vector Assembling
        for (String c : selectedUnlabeledData.columns()) {
            selectedUnlabeledData = selectedUnlabeledData.withColumn(c, selectedUnlabeledData.col(c).cast("double"));
        }

        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(Arrays.stream(selectedUnlabeledData.columns()).filter(col -> !col.equals("index")).collect(Collectors.toList()).toArray(new String[]{}))
                .setOutputCol("features");

        Dataset<Row> assembledUnlabeledData = assembler.setHandleInvalid("keep").transform(selectedUnlabeledData);

        // 3. predict
        model.setFeaturesCol("features");
        Dataset<Row> predictedResult = model.transform(assembledUnlabeledData);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ predictedResult @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        predictedResult.show(false);

        // 4. response
        var response = new SVMClassificationResponse(ResponseType.OBJECT_DATA);

        // dataset's each rows convert to line data for response
        var lineData = doPredictClassificationScaledData(predictedResult);

        response.setListFeatures(input.getListFieldsForPredict().toArray(new String[0]));
        response.setPredictionInfo(lineData.collect()); // #PC0002
        response.setPredictedFeatureLine(response.getPredictionInfo());
        response.setClassCol(input.getClassCol());

        response.setStatus(ResponseStatus.SUCCESS);

        return response;
    }

    public final ClassificationResponse predictMLP(BaseAlgorithmPredictInput input) throws IOException {
        System.out.println("Start MLP Model predicting with unlabeled data ...");

        // 0. setting
        var modelName = input.getModelName();
        var unlabeledData = this.getUnlabeledData(input);
        var selectedUnlabeledData = unlabeledData.selectExpr(convertListToSeq(input.getListFieldsForPredict()));

        // 1. load model and labelIndexer
        var model = MultilayerPerceptronClassificationModel.load(this.getModelFullPath(modelName));
        StringIndexerModel labelIndexer = StringIndexerModel.load(this.getModelIndexerPath(modelName));
        String[] indicesLabelsMapping = labelIndexer.labels();

        // 2. Vector Assembling
        for (String c : selectedUnlabeledData.columns()) {
            selectedUnlabeledData = selectedUnlabeledData.withColumn(c, selectedUnlabeledData.col(c).cast("double"));
        }

        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(Arrays.stream(selectedUnlabeledData.columns()).filter(col -> !col.equals("index")).collect(Collectors.toList()).toArray(new String[]{}))
                .setOutputCol("features");

        Dataset<Row> assembledUnlabeledData = assembler.setHandleInvalid("keep").transform(selectedUnlabeledData);

        // 3. predict
        model.setFeaturesCol("features");
        Dataset<Row> predictedResult = model.transform(assembledUnlabeledData);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ predictedResult @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        predictedResult.show(false);

        // 4. response
        var response = new ClassificationResponse(ResponseType.OBJECT_DATA);

        // dataset's each rows convert to line data for response
        var lineData = doPredictClassificationScaledData(predictedResult);

        response.setListFeatures(input.getListFieldsForPredict().toArray(new String[0]));
        response.setPredictionInfo(lineData.collect()); // #PC0002
        response.setPredictedFeatureLine(response.getPredictionInfo());
        response.setClassCol(input.getClassCol());

        response.setStatus(ResponseStatus.SUCCESS);

        return response;
    }

    public final ClassificationResponse predictLR(BaseAlgorithmPredictInput input) throws IOException {
        System.out.println("Start LR Model predicting with unlabeled data ...");

        // 0. setting
        var modelName = input.getModelName();
        var unlabeledData = this.getUnlabeledData(input);
        var selectedUnlabeledData = unlabeledData.selectExpr(convertListToSeq(input.getListFieldsForPredict()));

        // 1. load model and labelIndexer
        var model = LogisticRegressionModel.load(this.getModelFullPath(modelName));
        StringIndexerModel labelIndexer = StringIndexerModel.load(this.getModelIndexerPath(modelName));
        String[] indicesLabelsMapping = labelIndexer.labels();

        // 2. Vector Assembling
        for (String c : selectedUnlabeledData.columns()) {
            selectedUnlabeledData = selectedUnlabeledData.withColumn(c, selectedUnlabeledData.col(c).cast("double"));
        }

        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(Arrays.stream(selectedUnlabeledData.columns()).filter(col -> !col.equals("index")).collect(Collectors.toList()).toArray(new String[]{}))
                .setOutputCol("features");

        Dataset<Row> assembledUnlabeledData = assembler.setHandleInvalid("keep").transform(selectedUnlabeledData);

        // 3. predict
        model.setFeaturesCol("features");
        Dataset<Row> predictedResult = model.transform(assembledUnlabeledData);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ predictedResult @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        predictedResult.show(false);

        // 4. response
        var response = new ClassificationResponse(ResponseType.OBJECT_DATA);

        // dataset's each rows convert to line data for response
        var lineData = doPredictClassificationScaledData(predictedResult);

        response.setListFeatures(input.getListFieldsForPredict().toArray(new String[0]));
        response.setPredictionInfo(lineData.collect()); // #PC0002
        response.setPredictedFeatureLine(response.getPredictionInfo());
        response.setClassCol(input.getClassCol());

        response.setStatus(ResponseStatus.SUCCESS);

        return response;
    }

    public final ClassificationResponse predictRF(BaseAlgorithmPredictInput input) throws IOException {
        System.out.println("Start RF Model predicting with unlabeled data ...");

        // 0. setting
        var modelName = input.getModelName();
        var unlabeledData = this.getUnlabeledData(input);
        var selectedUnlabeledData = unlabeledData.selectExpr(convertListToSeq(input.getListFieldsForPredict()));

        // 1. load model and labelIndexer
        var model = RandomForestClassificationModel.load(this.getModelFullPath(modelName));
        StringIndexerModel labelIndexer = StringIndexerModel.load(this.getModelIndexerPath(modelName));
        String[] indicesLabelsMapping = labelIndexer.labels();

        // 2. Vector Assembling
        for (String c : selectedUnlabeledData.columns()) {
            selectedUnlabeledData = selectedUnlabeledData.withColumn(c, selectedUnlabeledData.col(c).cast("double"));
        }

        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(Arrays.stream(selectedUnlabeledData.columns()).filter(col -> !col.equals("index")).collect(Collectors.toList()).toArray(new String[]{}))
                .setOutputCol("features");

        Dataset<Row> assembledUnlabeledData = assembler.setHandleInvalid("keep").transform(selectedUnlabeledData);

        // 3. predict
        model.setFeaturesCol("features");
        Dataset<Row> predictedResult = model.transform(assembledUnlabeledData);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ predictedResult @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        predictedResult.show(false);

        // 4. response
        var response = new ClassificationResponse(ResponseType.OBJECT_DATA);

        // dataset's each rows convert to line data for response
        var lineData = doPredictClassificationScaledData(predictedResult);

        response.setListFeatures(input.getListFieldsForPredict().toArray(new String[0]));
        response.setPredictionInfo(lineData.collect()); // #PC0002
        response.setPredictedFeatureLine(response.getPredictionInfo());
        response.setClassCol(input.getClassCol());

        response.setStatus(ResponseStatus.SUCCESS);

        return response;
    }

    protected abstract T loadModel(String modelName) throws IOException;

    protected abstract ClassificationResponse predictData(Dataset<Row> data, T model, BaseAlgorithmPredictInput input, String[] indicesLabelsMapping);

    protected abstract String saveTrainedModel(BaseAlgorithmTrainInput config, T model) throws Exception;

    protected abstract T trainModel(BaseAlgorithmTrainInput input, Dataset<Row> train, Integer numberOfClass);

    protected abstract Dataset<Row> getTrainingResults(T model, Dataset<Row> test);

    protected ClassificationResponse createModelResponse(T model, Dataset<Row> test, BaseAlgorithmTrainInput config) throws IOException {
        return new ClassificationResponse();
    }

    public void populateResponseFromMetrics(
            ClassificationResponse response,
            MulticlassMetrics metrics,
            BaseAlgorithmTrainInput config,
            JavaRDD<String> predictActualFeature,
            String[] listSelectedFeatures) throws IOException {
        response.setConfusionMatrix(metrics.confusionMatrix().toArray());
        // Overall statistics
        response.setAccuracy(this.utilities.roundDouble(metrics.accuracy(), 2));
        // Weighted metrics
        response.setWeightedPrecision(this.utilities.roundDouble(metrics.weightedPrecision(), 2));
        response.setWeightedRecall(this.utilities.roundDouble(metrics.weightedRecall(), 2));
        response.setWeightedFMeasure(this.utilities.roundDouble(metrics.weightedFMeasure(), 2));
        response.setWeightedFalsePositiveRate(
                this.utilities.roundDouble(metrics.weightedFalsePositiveRate(), 2));
        response.setWeightedTruePositiveRate(
                this.utilities.roundDouble(metrics.weightedTruePositiveRate(), 2));

        int maxResults = this.algorithmConfig.getMaxResult();
        response.setPredictedActualFeatureLine(predictActualFeature.take((int) predictActualFeature.count()));
        response.setListFeatures(listSelectedFeatures);
        response.setClassCol(config.getClassCol());
        response.setStatus(ResponseStatus.SUCCESS);
        this.modelService.insertNewMlResponse(response, this.algorithmName, config.getModelName(), config.getPartType());
    }
}
