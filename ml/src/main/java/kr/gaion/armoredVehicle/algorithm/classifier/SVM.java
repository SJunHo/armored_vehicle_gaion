package kr.gaion.armoredVehicle.algorithm.classifier;

import kr.gaion.armoredVehicle.algorithm.AlgorithmConfig;
import kr.gaion.armoredVehicle.algorithm.ModelUtilService;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseStatus;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClassificationResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.SVMClassificationResponse;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.DatabaseSparkService;
import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
import kr.gaion.armoredVehicle.spark.dto.PredictedLabeledData;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j
public class SVM extends ClassifierAlgorithm<LogisticRegressionModel> {
	public SVM(@NonNull ElasticsearchSparkService elasticsearchSparkService, @NonNull DatabaseSparkService databaseSparkService, @NonNull Utilities utilities, @NonNull StorageConfig storageConfig, @NonNull ModelUtilService modelUtil, @NonNull FSChiSqSelector chiSqSelector, @NonNull AlgorithmConfig algorithmConfig, @NonNull DataConfig dataConfig, @NonNull ModelService modelService, @NonNull SparkSession sparkSession) {
		super(elasticsearchSparkService, databaseSparkService, utilities, storageConfig, modelUtil,chiSqSelector, algorithmConfig, dataConfig, sparkSession, modelService, "SvmClassifier");
	}

	private static Tuple2<BinaryClassificationMetrics, JavaRDD<Double>> evaluate(LogisticRegressionModel model, JavaRDD<LabeledPoint> testData, SVMClassificationResponse response) {
		// Clear the default threshold to compute raw scores
		model.clearThreshold();

		// Compute raw scores on the test set.
		JavaRDD<Tuple2<Object, Object>> predictionAndLabels = testData
				.map(new Function<>() {
					private static final long serialVersionUID = 6386275588685876924L;

					@Override
					public Tuple2<Object, Object> call(LabeledPoint p) {
						Double prediction = model.predict(p.features());
						return new Tuple2<>(prediction, p.label());
					}
				});

		// Get evaluation metrics.
		BinaryClassificationMetrics metrics = new BinaryClassificationMetrics(predictionAndLabels.rdd());
		// Precision by threshold
		JavaRDD<Tuple2<Object, Object>> precision = metrics.precisionByThreshold().toJavaRDD();
		response.setPrecisionByThreshold(precision.collect());

		// Thresholds
		JavaRDD<Double> thresholds = precision.map(new Function<>() {
			private static final long serialVersionUID = -3252224500161927762L;

			@Override
			public Double call(Tuple2<Object, Object> t) {
				return Double.valueOf(t._1().toString());
			}
		});

		return new Tuple2<>(metrics, thresholds);
	}

	@Override
	protected LogisticRegressionModel loadModel(String modelName) throws IOException {
		return LogisticRegressionModel.load(this.sparkSession.sparkContext(), this.getModelFullPath(modelName));
	}

	@Override
	protected ClassificationResponse predictData(Dataset<Row> data, LogisticRegressionModel model, BaseAlgorithmPredictInput input, String[] indicesLabelsMapping) {
		var response = new SVMClassificationResponse(ResponseType.OBJECT_DATA);
		// get setting
		String delimiter = this.storageConfig.getCsvDelimiter();

		var lineData = doPredictData(data, model, input.getListFieldsForPredict(), indicesLabelsMapping, delimiter);

		response.setPredictionInfo(lineData.collect());
		return response;
	}

	private static JavaRDD<String> doPredictData(Dataset<Row> data, LogisticRegressionModel model, List<String> fieldsForPredict, String[] indicesLabelsMapping, String delimiter) {
		String[] listCols = data.columns();
		List<String> listColNames = Arrays.asList(listCols);
		int[] indices = new int[fieldsForPredict.size()];
		int index = 0;
		for(String field : fieldsForPredict) {
			indices[index++] = listColNames.indexOf(field);
		}
		return data.toJavaRDD().map(row -> {
			// create suitable vector
			double[] denseData = new double[fieldsForPredict.size()];
			int _subIter = -1;
			for (int iter : indices) {
				++_subIter;
				try {
					denseData[_subIter] = Double.parseDouble(row.getString(iter));
				} catch (Exception e) {
					denseData[_subIter] = 0;
				}
			}
			Vector vector = Vectors.dense(denseData);

			// predict
			StringBuilder lineBuilder = new StringBuilder();
			int lblIndex = Double.valueOf(model.predict(vector)).intValue();								// index of label								// #PC0026
			lineBuilder.append('"').append(indicesLabelsMapping[lblIndex]).append('"');				// convert to categorical label					// #PC0026
			lineBuilder.append(delimiter);
			for (int iter = 0; iter < listColNames.size(); ++iter) {
				if (row.get(iter) == null) {
					lineBuilder.append('"').append('"');
				} else {
					lineBuilder.append('"').append(row.get(iter)).append('"');
				}
				lineBuilder.append(delimiter);
			}
			lineBuilder.deleteCharAt(lineBuilder.length() - 1);	// delete last delimiter (redundant)
			return lineBuilder.toString();
		});
	}

	@Override
	protected String saveTrainedModel(BaseAlgorithmTrainInput config, LogisticRegressionModel model) throws Exception {
		return this.saveModel(config, model);
	}

	@Override
	protected LogisticRegressionModel trainModel(BaseAlgorithmTrainInput input, Dataset<Row> train, Integer numberOfClass) {
		var trainData = convertToLabeledPoint(train).rdd();
		return new LogisticRegressionWithLBFGS().setNumClasses(numberOfClass).run(trainData);
	}

	@Override
	protected ClassificationResponse createModelResponse(LogisticRegressionModel model, Dataset<Row> test, BaseAlgorithmTrainInput config) throws IOException {
		var response = new SVMClassificationResponse(ResponseType.OBJECT_DATA);
		var testDataset = convertToLabeledPoint(test);
		if (config.getFraction() < 100.0) {
			// evaluate the training model
			log.info("Start evaluating ..");
			var evaluateResult = evaluate(model, testDataset, response);
			var metrics = evaluateResult._1;
			var thresholds = evaluateResult._2;
			// Recall by threshold
			JavaRDD<Tuple2<Object, Object>> recall = metrics.recallByThreshold().toJavaRDD();
			response.setRecallByThreshold(recall.collect());

			// F Score by threshold
			JavaRDD<Tuple2<Object, Object>> f1Score = metrics.fMeasureByThreshold().toJavaRDD();
			response.setF1ScoreByThreshold(f1Score.collect());

			JavaRDD<Tuple2<Object, Object>> f2Score = metrics.fMeasureByThreshold(2.0).toJavaRDD();
			response.setF2ScoreByThreshold(f2Score.collect());

			// Precision-recall curve
			JavaRDD<Tuple2<Object, Object>> prc = metrics.pr().toJavaRDD();
			response.setPrecisionRecallCurve(prc.collect());

			response.setThresholds(thresholds.collect());

			// ROC Curve
			JavaRDD<Tuple2<Object, Object>> roc = metrics.roc().toJavaRDD();
			response.setRocByThresholds(roc.collect());

			// AUPRC
			response.setAreaUnderPrecisionRecallCurve(this.utilities.roundDouble(metrics.areaUnderPR(), 2));

			// AUROC
			response.setAreaUnderRoc(this.utilities.roundDouble(metrics.areaUnderROC(), 2));

			log.info("evaluated successfully.");
			response.setStatus(ResponseStatus.SUCCESS);
		} else {
			response = new SVMClassificationResponse(ResponseType.MESSAGE);
			response.setMessage("trained successfully");
		}
		return response;
	}

	@Override
	protected Dataset<Row> getTrainingResults(LogisticRegressionModel model, Dataset<Row> test) {
		var predictionAndLabels = getTrainingResultsStatic(model, test);
		return this.sparkSession.createDataFrame(predictionAndLabels, PredictedLabeledData.class).toDF();
	}

	private static JavaRDD<PredictedLabeledData> getTrainingResultsStatic(LogisticRegressionModel model, Dataset<Row> test) {
		var data = convertToLabeledPoint(test);
		return data.map(new Function<>() {
			private static final long serialVersionUID = 7096752668508743505L;

			public PredictedLabeledData call(LabeledPoint p) {
				Double prediction = model.predict(p.features());
				var res = new PredictedLabeledData();
				res.setIndex(p.label());
				res.setPrediction(prediction);
				res.setFeatures(org.apache.spark.ml.linalg.Vectors.dense(p.features().toArray()));
				return res;
			}
		});
	}
}