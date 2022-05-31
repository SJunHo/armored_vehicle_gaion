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
import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier;
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
public class MLPClassifier extends ClassifierAlgorithm<MultilayerPerceptronClassificationModel> {
  public MLPClassifier(@NonNull ElasticsearchSparkService elasticsearchSparkService, @NonNull Utilities utilities, @NonNull StorageConfig storageConfig, @NonNull ModelUtilService modelUtil, @NonNull EsConnector esConnector, @NonNull FSChiSqSelector chiSqSelector, @NonNull AlgorithmConfig algorithmConfig, @NonNull DataConfig dataConfig, @NonNull SparkSession sparkSession, @NonNull ModelService modelService) {
    super(elasticsearchSparkService, utilities, storageConfig, modelUtil, esConnector, chiSqSelector, algorithmConfig, dataConfig, sparkSession, modelService, "MLPClassifier");
  }

	@Override
	protected MultilayerPerceptronClassificationModel loadModel(String modelName) throws IOException {
		return MultilayerPerceptronClassificationModel.load(this.getModelFullPath(modelName));
	}

	@Override
	protected ClassificationResponse predictData(Dataset<Row> data, MultilayerPerceptronClassificationModel model, BaseAlgorithmPredictInput input, String[] indicesLabelsMapping) {
		var response = new ClassificationResponse(ResponseType.OBJECT_DATA);

		// get setting
		String delimiter = this.storageConfig.getCsvDelimiter();

		var lineData = doPredictData(data, model, input.getListFieldsForPredict(), indicesLabelsMapping, delimiter);

		response.setPredictionInfo(lineData.collect());

		return response;
	}

	private static JavaRDD<String> doPredictData(Dataset<Row> data, MultilayerPerceptronClassificationModel model, List<String> fieldsForPredict, String[] indicesLabelsMapping, String delimiter) {
		List<String> listColNames = List.of(data.columns());
		int[] indices = new int[fieldsForPredict.size()];
		int index = 0;
		for(String field : fieldsForPredict) {
			indices[index++] = listColNames.indexOf(field);
		}
		return data.toJavaRDD().map(new Function<>() {
			private static final long serialVersionUID = 966931278414005865L;

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

	protected @Override
	String saveTrainedModel(BaseAlgorithmTrainInput config, MultilayerPerceptronClassificationModel model) throws Exception {
		return this.saveModel(config, model);
	}

	@Override
	protected MultilayerPerceptronClassificationModel trainModel(BaseAlgorithmTrainInput input, Dataset<Row> train, Integer numberOfClass) {
		// get settings from client
		long seed = input.getSeed();
		int[] layers = new int[input.getLayers()];
		int blockSize = input.getBlockSize();
		int maxIter = input.getMaxIter();
		String[] listSelectedFeatures = input.getFeatureCols().toArray(new String[0]);
		if (input.isFeaturesSelectionEnableFlg()) {
			listSelectedFeatures = this.chiSqSelector.selectFeaturesDataframeApi(input);
		}

		System.out.println("Total classes: " + numberOfClass);
		layers[0] = listSelectedFeatures.length;
		layers[1] = numberOfClass;

		// create the trainer and set its parameters
		MultilayerPerceptronClassifier trainer = new MultilayerPerceptronClassifier()
													.setBlockSize(blockSize)
													.setLayers(layers)
													.setSeed(seed)
													.setMaxIter(maxIter)
													.setLabelCol("index");
		return trainer.fit(train);
	}

	@Override
	protected Dataset<Row> getTrainingResults(MultilayerPerceptronClassificationModel model, Dataset<Row> test) {
		return model.transform(test);
	}
}
