package kr.gaion.armoredVehicle.algorithm;

import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.AlgorithmResponse;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.DatabaseSparkService;
import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.ml.util.MLWritable;
import org.apache.spark.mllib.util.Saveable;
import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataTypes;
import scala.Tuple2;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Log4j
@Getter
@RequiredArgsConstructor
public abstract class MLAlgorithm<T extends BaseAlgorithmTrainInput, T2 extends BaseAlgorithmPredictInput> implements IMLAlgorithm<T, T2> {
    @NonNull
    protected final ElasticsearchSparkService elasticsearchSparkService;
    @NonNull
    protected final DatabaseSparkService databaseSparkService;
    @NonNull
    protected final Utilities utilities;
    @NonNull
    protected final StorageConfig storageConfig;
    @NonNull
    protected final ModelUtilService modelUtil;
    @NonNull
    protected final FSChiSqSelector chiSqSelector;
    @NonNull
    protected final AlgorithmConfig algorithmConfig;
    @NonNull
    protected final DataConfig dataConfig;
    @NonNull
    protected final SparkSession sparkSession;
    @NonNull
    protected final String algorithmName;
    @NonNull
    protected final ModelService modelService;

    protected String name = "genericAlgorithm";

    protected static JavaPairRDD<Object, Object> zipPredictResult(Dataset<Row> trainingResults) {
        return trainingResults.select("prediction", "index", "features").toJavaRDD()
                .mapToPair(new PairFunction<>() {
                    private static final long serialVersionUID = 2113199201851594743L;

                    @Override
                    public Tuple2<Object, Object> call(Row row) {
                        return new Tuple2<>(row.get(0), row.get(1));
                    }
                });
    }

    protected static UserDefinedFunction convertStringToDoubleUDF() {
        return functions.udf((UDF1<String, Double>) s -> {
            try {
                return Double.parseDouble(s);
            } catch (Exception e) {
                return 0.0;
            }
        }, DataTypes.DoubleType);
    }

    @Override
    public abstract AlgorithmResponse train(T input) throws Exception;

    private void mlSaveModel(Saveable model, String modelFullPathName) {
        model.save(this.sparkSession.sparkContext(), modelFullPathName);
    }

    protected String getModelFullPath(String modelName) throws IOException {
        return this.utilities.getPathInWorkingFolder(
                this.storageConfig.getModelDir(),
                this.algorithmName,
                modelName);
    }

    protected String getModelIndexerPath(String modelName) throws IOException {
        return this.utilities.getPathInWorkingFolder(
                this.storageConfig.getModelIndexerDir(),
                this.algorithmName,
                modelName);
    }

    public String saveModel(BaseAlgorithmTrainInput config, MLWritable model) throws Exception {
        String modelName = config.getModelName();
        var modelFullPathName = this.getModelFullPath(modelName);

        modelUtil.deleteModelIfExisted(modelFullPathName);

        model.save(modelFullPathName);

        return modelFullPathName;
    }

    public String saveModelIndexer(BaseAlgorithmTrainInput config, MLWritable indexer) throws Exception {
        String modelName = config.getModelName();
        var modelIndexerPath = this.getModelIndexerPath(modelName);

        modelUtil.deleteModelIfExisted(modelIndexerPath);

        indexer.save(modelIndexerPath);

        return modelIndexerPath;
    }

    protected <TRow> Dataset<TRow>[] splitTrainTest(Dataset<TRow> data, long seed, double fraction) {
        // Split the data into training and test sets
        Dataset<TRow> trainingData, testData = null;
        if (fraction <= 100.0) {
            var splits = data.randomSplit(new double[]{fraction * 0.01, 1 - fraction * 0.01}, seed);
            trainingData = splits[0];
            testData = splits[1];
        } else {
            trainingData = data;
        }
        trainingData.cache();
        return new Dataset[]{trainingData, testData};
    }

    protected void saveTransformedData(String modelName, String action, Dataset<Row> df) {
        String hdfsFileNameFullPath = Paths
                .get(this.storageConfig.getHomeDir(), this.storageConfig.getModelDir(), this.algorithmName, modelName, action).toString();
        df.coalesce(1).write().format("com.databricks.spark.csv").option("header", "true").mode(SaveMode.Overwrite)
                .save(hdfsFileNameFullPath);
    }

    protected Dataset<Row> getUnlabeledData(BaseAlgorithmPredictInput config) {
        var dataInputOption = config.getDataInputOption();

        // unlabeled data
        Dataset<Row> originalData;

        switch (dataInputOption) {
            case INPUT_FROM_FILE: {
                originalData = this.elasticsearchSparkService.getDfVectorFromCsvFormattedFile(config.getFileInput());
                break;
            }
            case INPUT_FROM_ES: {
                // get test data from ElasticSearch
//			 data = SparkEsConnector.getUnlabeledDataFromES(config);
                List<String> docIds = config.getDbDocIds();
                if (docIds != null) {
                    originalData = this.elasticsearchSparkService.getUnlabeledDataFromEs(docIds);
                } else {
                    originalData = this.elasticsearchSparkService.getUnlabeledDataFromEs();
                }
                break;
            }
            case INPUT_FROM_DB: {
                originalData = this.databaseSparkService.getUnlabeledDataFromDb(config);
                break;
            }
            default: {
                // abnormal case:
                throw new Error("Input method is not acceptable: " + dataInputOption);
            }
        }
        return originalData;
    }
}
