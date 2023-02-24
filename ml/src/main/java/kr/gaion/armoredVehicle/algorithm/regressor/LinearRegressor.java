package kr.gaion.armoredVehicle.algorithm.regressor;

import kr.gaion.armoredVehicle.algorithm.AlgorithmConfig;
import kr.gaion.armoredVehicle.algorithm.MLAlgorithm;
import kr.gaion.armoredVehicle.algorithm.ModelUtilService;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseStatus;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.LinearRegressionTrainResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.RegressionResponse;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.DatabaseSparkService;
import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
import kr.gaion.armoredVehicle.spark.dto.NumericLabeledData;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.ml.linalg.DenseVector;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.ml.regression.LinearRegressionTrainingSummary;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class LinearRegressor extends MLAlgorithm<BaseAlgorithmTrainInput, BaseAlgorithmPredictInput> {
    public LinearRegressor(ElasticsearchSparkService elasticsearchSparkService, DatabaseSparkService databaseSparkService, Utilities utilities, StorageConfig storageConfig, ModelUtilService modelUtil, FSChiSqSelector chiSqSelector, AlgorithmConfig algorithmConfig, DataConfig dataConfig, SparkSession sparkSession, ModelService modelService) {
        super(elasticsearchSparkService, databaseSparkService, utilities, storageConfig, modelUtil, chiSqSelector, algorithmConfig, dataConfig, sparkSession, "LinearRegression", modelService);
    }

    private static JavaRDD<String> doPredictRegressionData(Dataset<Row> data, LinearRegressionModel model, List<String> fieldsForPredict) {
        List<String> listColNames = List.of(data.columns());
        int[] indices = new int[fieldsForPredict.size()];
        int index = 0;
        for (String field : fieldsForPredict) {
            indices[index++] = listColNames.indexOf(field);
        }
        return data.toJavaRDD().map(new Function<>() {
            private static final long serialVersionUID = 7065916945772988691L;

            @Override
            public String call(Row rowData) {
                // create suitable vector
                double[] denseData = new double[fieldsForPredict.size()];
                int _subIter = -1;
                for (int iter : indices) {
                    ++_subIter;
                    try {
                        denseData[_subIter] = rowData.getDouble(iter);
                    } catch (Exception e) {
                        denseData[_subIter] = 0;
                    }
                }
                Vector vector = Vectors.dense(denseData);

                // predict
                StringBuilder lineBuilder = new StringBuilder();
                var index = Double.valueOf(model.predict(vector));
                lineBuilder.append('"').append(index).append('"');
                lineBuilder.append(",");
                for (int iter = 0; iter < listColNames.size(); ++iter) {
                    if (rowData.get(iter) == null) {
                        lineBuilder.append('"').append('"');
                    } else {
                        lineBuilder.append('"').append(rowData.get(iter)).append('"');
                    }
                    lineBuilder.append(",");
                }
                lineBuilder.deleteCharAt(lineBuilder.length() - 1);
                return lineBuilder.toString();
            }
        });
    }

    private static Dataset<String> evaluateTest(Dataset<Row> test, LinearRegressionModel lrModel) {
        return test.map(new MapFunction<>() {
            private static final long serialVersionUID = 7065916945772988691L;

            @Override
            public String call(Row row) {
                StringBuilder strBlder = new StringBuilder();
                // in each line of returned data:
                // the first element is predicted value,
                // the second is actual
                // and the rest is feature
                DenseVector vector = (DenseVector) (row.getAs("features"));
                double predictedVal = lrModel.predict(vector);
                strBlder.append(predictedVal).append(',');
                double actualVal = (double) row.getAs("label");
                strBlder.append(actualVal).append(',');

                String originalFeatures = vector.toString();
                strBlder.append(originalFeatures, 1, originalFeatures.length() - 1);

                return strBlder.toString();
            }
        }, Encoders.STRING());
    }

    @Override
    public RegressionResponse train(BaseAlgorithmTrainInput config) throws Exception {
        System.out.println("============================ Start training to linear regression model ============================");

        // get settings
        int maxIterations = config.getMaxIter();
        double regParam = config.getRegParam();

        Dataset<NumericLabeledData> originalData = this.databaseSparkService.getNumericLabeledDatasetFromDb(config);
        Dataset<Row> rowOriginalData = sparkSession.createDataFrame(originalData.rdd(), NumericLabeledData.class);

        var splittedData = this.splitTrainTest(rowOriginalData, config.getSeed(), config.getFraction());
        var train = splittedData[0];
        var test = splittedData[1];

        LinearRegression lr = new LinearRegression()
                .setMaxIter(maxIterations)
                .setRegParam(regParam)
                .setElasticNetParam(0.0) // L2 regularization(Ridge)
                .setFeaturesCol("features")
                .setLabelCol("label");

        // Fit the model.
        LinearRegressionModel lrModel = lr.fit(train);

        // Save model
        var modelFullPathName = this.saveModel(config, lrModel);
        System.out.println(">>> Finish to save model in " + modelFullPathName);

        var response = new LinearRegressionTrainResponse(ResponseType.OBJECT_DATA);

        if (config.getFraction() < 100.0) {
            var jvRddPredictionInfo = evaluateTest(test, lrModel);
            response.setPredictionInfo(jvRddPredictionInfo.takeAsList((int) jvRddPredictionInfo.count()));
        }

        double[] arrCoe = new double[lrModel.coefficients().toArray().length + 1];
        int index = 0;
        for (double coe : lrModel.coefficients().toArray()) {
            arrCoe[index] = coe;
            index++;
        }
        arrCoe[arrCoe.length - 1] = lrModel.intercept();
        response.setCoefficients(arrCoe);

        LinearRegressionTrainingSummary trainingSummary = lrModel.summary();

        // RMSE
        response.setRootMeanSquaredError(trainingSummary.rootMeanSquaredError());

        // R2
        response.setR2(trainingSummary.r2());

        response.setListFeatures(config.getFeatureCols().toArray(new String[0]));
        response.setClassCol(config.getClassCol());

        response.setStatus(ResponseStatus.SUCCESS);

        this.modelService.insertNewMlResponse(response, this.algorithmName, config);

        System.out.println(">>> Complete linear regression training.");

        return response;
    }

    public RegressionResponse predict(BaseAlgorithmPredictInput input) throws Exception {
        System.out.println("============================ Start predict using linear regression model ============================");

        // 0. Get settings
        var dataInputOption = input.getDataInputOption();
        String modelName = input.getModelName();

        // 1. get data
        Dataset<Row> data = this.getUnlabeledData(input);

        // 2. load model
        var model = LinearRegressionModel.load(this.getModelFullPath(modelName));

        // 3. predict
        var response = new RegressionResponse(ResponseType.OBJECT_DATA);

        // get setting
        String[] listCols = data.columns();
        List<String> listColNames = List.of(data.columns());
        List<String> fieldsForPredict = input.getListFieldsForPredict();
        int[] indices = new int[fieldsForPredict.size()];
        int index = 0;
        for (String field : fieldsForPredict) {
            indices[index++] = listColNames.indexOf(field);
        }

        var lineData = doPredictRegressionData(data, model, input.getListFieldsForPredict());

        response.setPredictionInfo(lineData.collect());
        response.setListFeatures(listCols);

        response.setPredictedFeatureLine(response.getPredictionInfo());
        response.setClassCol(input.getClassCol());

        response.setStatus(ResponseStatus.SUCCESS);

        System.out.println(">>> Complete linear regression Predicting.");

        return response;
    }
}


