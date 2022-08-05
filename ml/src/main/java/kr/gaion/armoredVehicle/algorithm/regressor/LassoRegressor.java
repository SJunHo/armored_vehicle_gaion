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

import java.util.Arrays;
import java.util.List;

@Service
@Log4j
public class LassoRegressor extends MLAlgorithm<BaseAlgorithmTrainInput, BaseAlgorithmPredictInput>  {
    public LassoRegressor(ElasticsearchSparkService elasticsearchSparkService, DatabaseSparkService databaseSparkService, Utilities utilities, StorageConfig storageConfig, ModelUtilService modelUtil, FSChiSqSelector chiSqSelector, AlgorithmConfig algorithmConfig, DataConfig dataConfig, SparkSession sparkSession, ModelService modelService) {
        super(elasticsearchSparkService, databaseSparkService, utilities, storageConfig, modelUtil, chiSqSelector, algorithmConfig, dataConfig, sparkSession, "LassoRegression", modelService);
    }

    @Override
    public RegressionResponse train(BaseAlgorithmTrainInput config) throws Exception {
        // BaseAlgorithmTrainInput config: 웹으로 통해 들어오는 사용자가 선택한 알고리즘의 '학습'을 위한 정보들(Request)

        log.info("============================ START Lasso Regression ============================");

        // get settings
        int maxIterations = config.getMaxIter();
        double regParam = config.getRegParam();

        Dataset<NumericLabeledData> originalData = this.databaseSparkService.getNumericLabeledDatasetFromDb(config);
        Dataset<Row> rowOriginalData = sparkSession.createDataFrame(originalData.rdd(), NumericLabeledData.class);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ originalData @@@@@@@@@@@@@@@@@@@@@@@@@ ");
        originalData.show();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ rowOriginalData @@@@@@@@@@@@@@@@@@@@@@@@@ ");
        rowOriginalData.show();

        // Split the data into train and test
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@ Split the data into train and test @@@@@@@@@@@@@@@@@@@@@@@@@");
        var splittedData = this.splitTrainTest(rowOriginalData, config.getSeed(), config.getFraction()); // MLAlgorithm 클래스를 상속받았으니 이 안에 있는 splitTrainTest 메소드를 this로 호출
        var train = splittedData[0];
        var test = splittedData[1];
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ Train set Count: " + train.count());  // Train set Count: 24974 (8:2)
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ Test set Count: " +test.count()); // Test set Count: 6298 (8:2)

        // 모델 생성
        LinearRegression lr = new LinearRegression()
                .setMaxIter(maxIterations)
                .setRegParam(regParam)
                .setElasticNetParam(1.0)    // L1 regularization(Lasso)
                .setFeaturesCol("features")
                .setLabelCol("label");

        // Fit the model.
        LinearRegressionModel lrModel = lr.fit(train);

        // Save model
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@ Saving model ... @@@@@@@@@@@@@@@@@@@@@@@@@");
        var modelFullPathName = this.saveModel(config, lrModel); // MLAlgorithm 클래스를 상속받았으니 이 안에 있는 saveModel 메소드를 this로 호출
        lrModel.save(modelFullPathName);    // #PC0026	// #PC0017

        // return response
        var response = new LinearRegressionTrainResponse(ResponseType.OBJECT_DATA); // Linear Regression 모델이 다시 웹으로 돌려주어야하는 정보가 있는 LinearRegressionTrainResponse에 response를 담음

        // if the test data set is not null/empty
        if (config.getFraction() < 100.0) {
            var jvRddPredictionInfo = evaluateTest(test, lrModel);  // test 데이터셋(실제 값, feature)과 학습된 모델 결과(test 데이터셋에 대한 예측값)를 묶음
            // prediction-actual information
//            response.setPredictionInfo(jvRddPredictionInfo.takeAsList(algorithmConfig.getMaxResult()));
            response.setPredictionInfo(jvRddPredictionInfo.takeAsList((int) jvRddPredictionInfo.count()));
            // -> test set의 모든 예측값, 실제값, features 가져와서 response
        }

        // Print the coefficients and intercept for linear regression. ~> coefficients 계산하고 순서에 맞게 인덱스를 달아 리스트로 묶음
        double[] arrCoe = new double[lrModel.coefficients().toArray().length + 1];
        int index = 0;
        for(double coe : lrModel.coefficients().toArray()) {
            arrCoe[index] = coe;
            index++;
        }
        //arrCoe = lrModel.coefficients().toArray();
        arrCoe[arrCoe.length - 1] = lrModel.intercept();
        response.setCoefficients(arrCoe); // coefficients를 웹으로 돌려주어야 하니까 response에 set

        // Summarize the model over the training set and print out some metrics. ~> 모델 summary
        LinearRegressionTrainingSummary trainingSummary = lrModel.summary();

        // residuals ~> 모델 summary에서 residuals 찾고 웹으로 돌려주어야 하니까 response에 set
        // spark dataset의 각 row를 DOuble 타입으로 바꾸고 리스트로 변환. (dataset's row: [[0.1111], [0.2222], ...] -> [0.1111, 0.2222, ...]
//        List<Double> residualsValues = trainingSummary.residuals().map((MapFunction<Row, Double>) row -> row.<Double>getAs(0), Encoders.DOUBLE()).collectAsList();
//        response.setResiduals(residualsValues);
//        response.setResiduals(trainingSummary.residuals().collectAsList());

        // RMSE ~> 모델 summary에서 RMSE 찾고 웹으로 돌려주어야 하니까 response에 set
        response.setRootMeanSquaredError(trainingSummary.rootMeanSquaredError());

        // R2 ~> 모델 summary에서 R2 찾고 웹으로 돌려주어야 하니까 response에 set
        response.setR2(trainingSummary.r2());

        response.setListFeatures(config.getFeatureCols().toArray(new String[0])); // 사용된 feature들도 웹으로 돌려주어야 하니까 response에 set
        response.setClassCol(config.getClassCol()); // 사용된 컬럼들도 웹으로 돌려주어야 하니까 response에 set

        response.setStatus(ResponseStatus.SUCCESS); // SUCCESS 메시지도 웹으로 돌려주어야 하니까 response에 set

        // Service의 역할은 Dao가 DB에서 받아온 데이터를 전달받아 가공하는 것. 즉, Controller가 받은 요청에 대해 알맞는 정보를 가공해서 다시 Controller에게 데이터를 넘기는 것을 의미합니다.
        // 그래서 웹에서 컨트롤러로 들어온 요청에 대한 대답을 서비스가 가공해서 다시 컨트롤러로 주기위해 정보들을 담아주는 것. 그럼 이 정보를 컨트롤러가 웹으로 보내준다.
        this.modelService.insertNewMlResponse(response, this.algorithmName, config.getModelName(), config.getPartType());

        return response;
    }

    @Override
    public RegressionResponse predict(BaseAlgorithmPredictInput input) throws Exception {
        // BaseAlgorithmPredictInput input: 웹으로 통해 들어오는 사용자가 선택한 알고리즘의 '예측'을 위한 정보들(Request)
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@ Start predicting unlabeled data... @@@@@@@@@@@@@@@@@@@@@@@@@");

        // 0. Get settings
        var dataInputOption = input.getDataInputOption();
        String modelName = input.getModelName();

        // 1. get data
        // JavaRDD<Vector> data = null;
        Dataset<Row> data = this.getUnlabeledData(input); // MLAlgorithm 클래스를 상속받았으니 이 안에 있는 메소드를 this로 호출
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ UnlabeledData @@@@@@@@@@@@@@@@@@@@@@@@@");
        data.show();

        // 2. load model
        var model = LinearRegressionModel.load(this.getModelFullPath(modelName));

        // 3. predict
        // #PC0002 - Start
        var response = new RegressionResponse(ResponseType.OBJECT_DATA);

        // get setting
        String[] listCols = data.columns();
        List<String> listColNames = Arrays.asList(listCols);
//        String[] fieldsForPredict = input.getListFieldsForPredict().toArray(new String[0]);
        List<String> fieldsForPredict = input.getListFieldsForPredict();
        int[] indices = new int[fieldsForPredict.size()];
        int index = 0;
        for(String field : fieldsForPredict) {
            indices[index++] = listColNames.indexOf(field);
        }

        var lineData = doPredictRegressionData(data, model, input.getListFieldsForPredict());

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@ PredictionInfo @@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info(lineData.collect());

        response.setPredictionInfo(lineData.collect()); // #PC0002
        response.setListFeatures(listCols); // #PC0002
        // #PC0002 - End

        response.setPredictedFeatureLine(response.getPredictionInfo());
        response.setClassCol(input.getClassCol());

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@ Lasso regression model predict to unlabeled data successfully. @@@@@@@@@@@@@@@@@@@@@@@@@");
        response.setStatus(ResponseStatus.SUCCESS);

        return response;
    }

    private static JavaRDD<String> doPredictRegressionData(Dataset<Row> data, LinearRegressionModel model, List<String> fieldsForPredict) {
        List<String> listColNames = List.of(data.columns());
        int[] indices = new int[fieldsForPredict.size()];
        int index = 0;
        for(String field : fieldsForPredict) {
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
                var index = Double.valueOf(model.predict(vector));                // index of label								// #PC0026
                lineBuilder.append('"').append(index).append('"');        // convert to categorical label					// #PC0026
                lineBuilder.append(",");
                for (int iter = 0; iter < listColNames.size(); ++iter) {
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

    private static Dataset<String> evaluateTest(Dataset<Row> test, LinearRegressionModel lrModel) {
        // 각 feature(각 row)들의 각 예측 결과, 실제 결과를 StringBuilder로 묶음
        // Dense Vector == Numpy Array ~> [예측, 실제, feature]
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
                double actualVal = row.getAs("label");
                strBlder.append(actualVal).append(',');

                String originalFeatures = vector.toString();
                strBlder.append(originalFeatures, 1, originalFeatures.length() - 1);

                return strBlder.toString();
            }
        }, Encoders.STRING());
    }
}
