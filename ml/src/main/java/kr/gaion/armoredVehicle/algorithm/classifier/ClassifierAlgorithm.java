package kr.gaion.armoredVehicle.algorithm.classifier;

import kr.gaion.armoredVehicle.algorithm.AlgorithmConfig;
import kr.gaion.armoredVehicle.algorithm.MLAlgorithm;
import kr.gaion.armoredVehicle.algorithm.ModelUtilService;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseStatus;
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
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.util.Arrays;

@Log4j
public abstract class ClassifierAlgorithm<T> extends MLAlgorithm<BaseAlgorithmTrainInput, BaseAlgorithmPredictInput> {
  public ClassifierAlgorithm(@NonNull ElasticsearchSparkService elasticsearchSparkService, @NonNull Utilities utilities, @NonNull StorageConfig storageConfig, @NonNull ModelUtilService modelUtil, @NonNull EsConnector esConnector, @NonNull FSChiSqSelector chiSqSelector, @NonNull AlgorithmConfig algorithmConfig, @NonNull DataConfig dataConfig, @NonNull SparkSession sparkSession, @NonNull ModelService modelService, @NonNull String algorithmName) {
    super(elasticsearchSparkService, utilities, storageConfig, modelUtil, esConnector, chiSqSelector, algorithmConfig, dataConfig, sparkSession, algorithmName, modelService);
  }

  @Override
	public ClassificationResponse train(BaseAlgorithmTrainInput config) throws Exception {
		log.info("Starting train model ..");

		// Get all settings sent through REST-client
		double fraction = config.getFraction();
		long lSeed = config.getLSeed();

		if(config.isFeaturesSelectionEnableFlg()) {
			config.setFeatureCols(Arrays.asList(this.chiSqSelector.selectFeaturesDataframeApi(config)));
		}

		Dataset<Row> originalData = this.elasticsearchSparkService.getLabeledDatasetFromElasticsearch(config); 												// #PC0023
		StringIndexerModel labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("index").fit(originalData);						// #PC0026
		Dataset<Row> indexedData = labelIndexer.transform(originalData);
		String[] indicesLabelsMapping = labelIndexer.labels();

		// Split the data into train and test
		var splitData = this.splitTrainTest(indexedData, lSeed, fraction);
		var train = splitData[0];
		var test = splitData[1];

    var model = trainModel(config, train, indicesLabelsMapping.length);

		log.info("Saving model ..");
		var modelFullPathName = this.saveTrainedModel(config, model);
		labelIndexer.save(modelFullPathName);

		// model evaluation

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
		log.info("Start predicting unlabeled data ..");

		var modelName = input.getModelName();
		var data = this.getUnlabeledData(input);

		// 2. load model
		T model = this.loadModel(modelName);
		StringIndexerModel labelIndexer = StringIndexerModel.load(this.getModelIndexerPath(modelName));
		String[] indicesLabelsMapping = labelIndexer.labels();

    var response = predictData(data, model, input, indicesLabelsMapping);
    response.setListFeatures(input.getListFieldsForPredict().toArray(new String[0]));
		response.setClassCol(input.getClassCol());

		log.info("predicted unlabeled data successfully.");
		response.setStatus( ResponseStatus.SUCCESS);

		return response;
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
		response.setWeightedPrecision( this.utilities.roundDouble(metrics.weightedPrecision(), 2));
		response.setWeightedRecall( this.utilities.roundDouble(metrics.weightedRecall(), 2));
		response.setWeightedFMeasure( this.utilities.roundDouble(metrics.weightedFMeasure(), 2));
		response.setWeightedFalsePositiveRate(
				this.utilities.roundDouble(metrics.weightedFalsePositiveRate(), 2));
		response.setWeightedTruePositiveRate(
				this.utilities.roundDouble(metrics.weightedTruePositiveRate(), 2));

		int maxResults = this.algorithmConfig.getMaxResult();
		response.setPredictedActualFeatureLine(predictActualFeature.take(maxResults));
		response.setListFeatures(listSelectedFeatures);
		response.setClassCol(config.getClassCol());
		response.setStatus(ResponseStatus.SUCCESS);
		this.modelService.insertNewMlResponse(response, this.algorithmName, config.getModelName());
  }
}
