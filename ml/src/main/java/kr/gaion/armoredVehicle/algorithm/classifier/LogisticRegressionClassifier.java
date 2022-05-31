package kr.gaion.armoredVehicle.algorithm.classifier;

import kr.gaion.armoredVehicle.algorithm.AlgorithmConfig;
import kr.gaion.armoredVehicle.algorithm.ModelUtilService;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClassificationResponse;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Log4j
public class LogisticRegressionClassifier extends ClassifierAlgorithm<LogisticRegressionModel> {
	public LogisticRegressionClassifier(@NonNull ElasticsearchSparkService elasticsearchSparkService, @NonNull Utilities utilities, @NonNull StorageConfig storageConfig, @NonNull ModelUtilService modelUtil, @NonNull EsConnector esConnector, @NonNull FSChiSqSelector chiSqSelector, @NonNull AlgorithmConfig algorithmConfig, @NonNull DataConfig dataConfig, @NonNull SparkSession sparkSession, @NonNull ModelService modelService) {
		super(elasticsearchSparkService, utilities, storageConfig, modelUtil, esConnector, chiSqSelector, algorithmConfig, dataConfig, sparkSession, modelService, "LogisticRegression");
	}

  @Override
	protected String saveTrainedModel(BaseAlgorithmTrainInput config, LogisticRegressionModel model) throws Exception {
		return this.saveModel(config, model);
	}

	@Override
	protected LogisticRegressionModel trainModel(BaseAlgorithmTrainInput config, Dataset<Row> train, Integer numberOfClass) {
		// Training model
		var rfTrainer = new LogisticRegression()
				.setLabelCol("index")																												// #PC002
				.setRegParam(config.getRegParam())
				.setElasticNetParam(config.getElasticNetMixing())
				.setMaxIter(config.getMaxIter())
				.setFitIntercept(config.isIntercept());


		return rfTrainer.fit(train);
	}

	@Override
	protected final Dataset<Row> getTrainingResults(LogisticRegressionModel model, Dataset<Row> test) {
		return model.transform(test);
	}

	@Override
	protected LogisticRegressionModel loadModel(String modelName) throws IOException {
		return LogisticRegressionModel.load(this.getModelFullPath(modelName));
	}

	@Override
	protected ClassificationResponse predictData(Dataset<Row> data, LogisticRegressionModel model, BaseAlgorithmPredictInput input, String[] indicesLabelsMapping) {
		// 3. predict
		// #PC0002 - Start
		var response = new ClassificationResponse(ResponseType.OBJECT_DATA);

		// get setting
		String delimiter = this.storageConfig.getCsvDelimiter();

		var lineData = doPredictData(data, model, input.getListFieldsForPredict(), indicesLabelsMapping, delimiter);

		response.setPredictionInfo(lineData.collect());

		return response;
	}

	private static JavaRDD<String> doPredictData(Dataset<Row> data, LogisticRegressionModel model, List<String> fieldsForPredict, String[] indicesLabelsMapping, String delimiter) {
		List<String> listColNames = List.of(data.columns());
		int[] indices = new int[fieldsForPredict.size()];
		int index = 0;
		for(String field : fieldsForPredict) {
			indices[index++] = listColNames.indexOf(field);
		}
		return data.toJavaRDD().map(new Function<>() {
			private static final long serialVersionUID = -4035135440483467579L;

			@Override
			public String call(Row rowData) {
				// create suitable vector
				double[] denseData = new double[fieldsForPredict.size()];
				int _subIter = -1;
				for (int iter : indices) {
					++_subIter;
					try {
						denseData[_subIter] = Double.parseDouble(rowData.getString(iter));
					} catch (Exception e) {
						denseData[_subIter] = 0;
					}
				}
				Vector vector = Vectors.dense(denseData);

				// predict
				StringBuilder lineBuilder = new StringBuilder();
				int index = Double.valueOf(model.predict(vector)).intValue();                // index of label								// #PC0026
				lineBuilder.append('"').append(indicesLabelsMapping[index]).append('"');        // convert to categorical label					// #PC0026
				lineBuilder.append(delimiter);
				for (int iter = 0; iter < listColNames.size(); ++iter) {
					if (rowData.get(iter) == null) {
						lineBuilder.append('"').append('"');
					} else {
						lineBuilder.append('"').append(rowData.get(iter)).append('"');
					}
					lineBuilder.append(delimiter);
				}
				lineBuilder.deleteCharAt(lineBuilder.length() - 1);  // delete last delimiter (redundant)
				return lineBuilder.toString();
			}
		});
	}
}
