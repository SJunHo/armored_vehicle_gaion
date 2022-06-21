package kr.gaion.armoredVehicle.algorithm;

import kr.gaion.armoredVehicle.algorithm.dto.ResponseStatus;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClassificationResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.LinearRegressionTrainResponse;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
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
public class LinearRegression extends MLAlgorithm<BaseAlgorithmTrainInput , BaseAlgorithmPredictInput> {
  public LinearRegression(ElasticsearchSparkService elasticsearchSparkService, DatabaseSparkService databaseSparkService, Utilities utilities, StorageConfig storageConfig, ModelUtilService modelUtil, EsConnector esConnector, FSChiSqSelector chiSqSelector, AlgorithmConfig algorithmConfig, DataConfig dataConfig, SparkSession sparkSession, ModelService modelService) {
    super(elasticsearchSparkService,databaseSparkService, utilities, storageConfig, modelUtil, esConnector, chiSqSelector, algorithmConfig, dataConfig, sparkSession, "LinearRegression", modelService);
  }

  @Override
  public ClassificationResponse predict(BaseAlgorithmPredictInput input) throws Exception {
  	log.info("Start predicting unlabeled data ..");

		// 0. Get settings
		var dataInputOption = input.getDataInputOption();
		String modelName = input.getModelName();

		// 1. get data
		// JavaRDD<Vector> data = null;
		Dataset<Row> data = this.getUnlabeledData(input);

		// 2. load model
		var model = LinearRegressionModel.load(this.getModelFullPath(modelName));

		// 3. predict
		// #PC0002 - Start
		var response = new LinearRegressionTrainResponse(ResponseType.OBJECT_DATA);

		// get setting
		String[] listCols = data.columns();
		List<String> listColNames = Arrays.asList(listCols);
		String[] fieldsForPredict = input.getListFieldsForPredict().toArray(new String[0]);
		int[] indices = new int[fieldsForPredict.length];
		int index = 0;
		for(String field : fieldsForPredict) {
			indices[index++] = listColNames.indexOf(field);
		}

		JavaRDD<String> lineData = data.toJavaRDD().map(new Function<>() {

			private static final long serialVersionUID = -4035135440483467579L;

			@Override
			public String call(Row rowData) {
				// create suitable vector
				double[] denseData = new double[fieldsForPredict.length];
				int _subIter = -1;
				for (int iter : indices) {
					++_subIter;
					try {
						denseData[_subIter] = Double.parseDouble(rowData.getString(iter));
					} catch (Exception e) {
						denseData[_subIter] = 0;
					}
				}
				Vector vector = org.apache.spark.ml.linalg.Vectors.dense(denseData);

				StringBuilder lineBuilder = new StringBuilder();
				//DenseVector dVector = (DenseVector)(rowData.get(0));
				double predictedVal = model.predict(vector);
				lineBuilder.append(predictedVal).append(',');
				//double actualVal = rowData.getDouble(1);
				//lineBuilder.append(actualVal).append(',');

//				String originalFeaturesT = vector.toString();
//				lineBuilder.append(originalFeaturesT.substring(1, originalFeaturesT.length() - 1));

				double[] data = new double[rowData.length()];
				for (int i = 0; i < rowData.length(); i++) {
					try {
						data[i] = Double.valueOf(rowData.getString(i));
					} catch (Exception e) {
						data[i] = 0;
					}
				}
				Vector vectort = org.apache.spark.ml.linalg.Vectors.dense(data);
				String originalFeatures = vectort.toString();
				lineBuilder.append(originalFeatures, 1, originalFeatures.length() - 1);

				return lineBuilder.toString();
			}
		});

		response.setPredictionInfo(lineData.collect()); // #PC0002
		response.setListFeatures(listCols); // #PC0002
		// #PC0002 - End

		response.setPredictedFeatureLine(response.getPredictionInfo());
		response.setClassCol(input.getClassCol());

		log.info("predicted unlabeled data successfully.");
		response.setStatus(ResponseStatus.SUCCESS);

		return response;
  }

  @Override
  public LinearRegressionTrainResponse train(BaseAlgorithmTrainInput config) throws Exception {
		// get settings
		int maxIterations = config.getMaxIter();
		double regParam = config.getRegParam();
		double elasticNetParam = config.getElasticNetMixing();

		// get data from Elasticsearch
		log.info("get data from Elasticsearch");
		Dataset<NumericLabeledData> originalData = this.elasticsearchSparkService.getNumericLabeledDatasetFromElasticsearch(config);

		// Split the data into train and test
		log.info("Split the data into train and test");
		var splittedData = this.splitTrainTest(originalData, config.getSeed(), config.getFraction());
		var train = splittedData[0];
		var test = splittedData[1];

		org.apache.spark.ml.regression.LinearRegression lr = new org.apache.spark.ml.regression.LinearRegression()
				.setMaxIter(maxIterations).setRegParam(regParam).setElasticNetParam(elasticNetParam);

		// Fit the model.
		LinearRegressionModel lrModel = lr.fit(train);

		// Save model
		log.info("Saving model ..");
		var modelFullPathName = this.saveModel(config, lrModel);
		lrModel.save(modelFullPathName);																		// #PC0026	// #PC0017
		//labelIndexer.save(modelFullPathName);

		// return response
		var response = new LinearRegressionTrainResponse(ResponseType.OBJECT_DATA);
		// if the test data set is not null/empty
		if (config.getFraction() < 100.0) {
			var jvRddPredictionInfo = evaluateTest(test, lrModel);

			// prediction-actual infomation
			response.setPredictionInfo(jvRddPredictionInfo.takeAsList(algorithmConfig.getMaxResult()));
		}

		// Print the coefficients and intercept for linear regression.
		//response.setCoefficients(lrModel.coefficients().toArray());

		// Print the coefficients and intercept for linear regression.
		double[] arrCoe = new double[lrModel.coefficients().toArray().length + 1];
		int index = 0;
		for(double coe : lrModel.coefficients().toArray()) {
			arrCoe[index] = coe;
			index++;
		}
		//arrCoe = lrModel.coefficients().toArray();
		arrCoe[arrCoe.length - 1] = lrModel.intercept();
		response.setCoefficients(arrCoe);

		// Summarize the model over the training set and print out some metrics.
		LinearRegressionTrainingSummary trainingSummary = lrModel.summary();

		// residuals
		response.setResiduals(trainingSummary.residuals().collectAsList());
		// RMSE
		response.setRootMeanSquaredError(trainingSummary.rootMeanSquaredError());
		// R2
		response.setR2(trainingSummary.r2());

		response.setListFeatures(config.getFeatureCols().toArray(new String[0]));
		response.setClassCol(config.getClassCol());

		response.setStatus(ResponseStatus.SUCCESS);

		this.modelService.insertNewMlResponse(response, this.algorithmName, config.getModelName());
		return response;
  }

  private static Dataset<String> evaluateTest(Dataset<NumericLabeledData> test, LinearRegressionModel lrModel) {
  	return test.map(new MapFunction<>() {
				private static final long serialVersionUID = 7065916945772988691L;

				@Override
				public String call(NumericLabeledData row) {
					StringBuilder strBlder = new StringBuilder();
					// in each line of returned data:
					// the first element is predicted value,
					// the second is actual
					// and the rest is feature
					DenseVector vector = (DenseVector) (row.getFeatures());
					double predictedVal = lrModel.predict(vector);
					strBlder.append(predictedVal).append(',');
					double actualVal = row.getLabel();
					strBlder.append(actualVal).append(',');

					String originalFeatures = vector.toString();
					strBlder.append(originalFeatures, 1, originalFeatures.length() - 1);

					return strBlder.toString();
				}
			}, Encoders.STRING());
	}
}
