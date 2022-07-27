package kr.gaion.armoredVehicle.algorithm.classifier;


import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.ml.classification.LinearSVC;
import org.apache.spark.ml.classification.OneVsRest;
import org.apache.spark.ml.classification.OneVsRestModel;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DecimalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * Add LinearSVC with integrated "One vs. Rest" algorithm, For multiple
 * classification
 */
public class SvcClassifier {

//
//  private static final long serialVersionUID = 6742100313543539122L;
//  final static Logger logger = LoggerFactory.getLogger(SvcClassifier.class);
//
////	@Autowired
////	private EsConnector esConn;
//
//  /**
//   * to train labeled data
//   *
//   * @param config
//   * @return
//   * @throws Exception
//   */
//  public IResponseObject train(IConfigurable config) throws Exception {
//
//    // get settings
//    logger.info("get settings");
//    long seed = config.getSetting(SvcClassifierSettings.SEED);
//    int numIterations = config.getSetting(SvcClassifierSettings.NUMBER_ITERATIONS);
//    double threshold = config.getSetting(SvcClassifierSettings.THRESHOLD);
//    double regParam = config.getSetting(SvcClassifierSettings.REG_PARAM);
//    boolean intercept = config.getSetting(SvcClassifierSettings.INTERCEPT);
//
//
//    boolean featuresSelectionEnabelFlg = config.getSetting(SvcClassifierSettings.FEATURE_SELECTION_ENABEL_FLG);
//    String[] listSelectedFeatures = null;
//    if (featuresSelectionEnabelFlg) {
//      listSelectedFeatures = FSChiSqSelector.selectFeaturesDataframeApi(config);
//      config.set(SvcClassifierSettings.LIST_FEATURES_COL, listSelectedFeatures);
//    } else {
//      // listSelectedFeatures = config.getSetting(SvcClassifierSettings.LIST_FEATURES_COL);
//    }
//
//
//    // get data from Elasticsearch
//    logger.info("get data from Elasticsearch");
//    // Dataset<Row> dataFrame = SparkEsConnector.getDatasetFromESWithDenseFormat(config);
//    Dataset<Row> originalData = DAOSparkDataFrame.getCategoricalLabeledDatasetFromElasticsearch(config);
//    // Using StringIndexer
//    StringIndexerModel labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("index")
//            .setHandleInvalid("skip").fit(originalData);
//    Dataset<Row> indexedData = labelIndexer.transform(originalData);
//    String[] indicesLabelsMapping = labelIndexer.labels();
//
//    // Split the data into train and test
//    logger.info("Split the data into train and test");
//    double fraction = config.getSetting(SvcClassifierSettings.FRACTION);
//    Dataset<Row> train = null, test = null;
//    if (fraction < 100.0) {
//      // random split data
//      double ratio = fraction * 0.01;
//      Dataset<Row>[] splits = indexedData.randomSplit(new double[]{ratio, 1 - ratio}, seed);
//      train = splits[0];
//      test = splits[1];
//    } else {
//      train = indexedData;
//    }
//
//    LinearSVC classifier = new LinearSVC().setMaxIter(numIterations).setThreshold(threshold).setRegParam(regParam)
//            .setFitIntercept(intercept).setLabelCol("index");
//
//    // instantiate the One Vs Rest Classifier.
//    logger.info("instantiate the One Vs Rest Classifier.");
//    OneVsRest ovr = new OneVsRest().setClassifier(classifier).setLabelCol("index");
//
//    // train the multiclass model.
//    logger.info("train the multiclass model.");
//    OneVsRestModel model = ovr.fit(train);
//
//    // Save model
//    logger.info("Saving model ..");
//    String modelFullPathName = "";
//    String modelName = config.getSetting(
//            SvcClassifierSettings.MODEL_NAME) == null ? AlgorithmSettings.DEFAULT_MODEL_NAME : config.getSetting(
//            SvcClassifierSettings.MODEL_NAME);
//    modelFullPathName = Utilities.getPathInWorkingFolder(Constants.DATA_DIR, SvcClassifierSettings.ALGORITHM_NAME,
//            Constants.MODEL_DIR, modelName);
//    ModelUtil.deleteModelIfExisted(modelFullPathName);
//    model.save(modelFullPathName);
//    // save StringIndexerModel
//    modelFullPathName = Utilities.getPathInWorkingFolder(Constants.DATA_DIR, SvcClassifierSettings.ALGORITHM_NAME,
//            Constants.MODEL_INDEXER_DIR, modelName);
//    ModelUtil.deleteModelIfExisted(modelFullPathName);
//    labelIndexer.save(modelFullPathName);
//
//    // compute accuracy on the test set
//    Dataset<Row> trainingResults = model.transform(test);
//
//    // Set of prediction, labels
//    JavaPairRDD<Object, Object> predictionAndLabelRdd = trainingResults.select("prediction", "index", "features")
//            .toJavaRDD().mapToPair(new PairFunction<Row, Object, Object>() {
//              private static final long serialVersionUID = -3406001693748780854L;
//
//              @Override
//              public Tuple2<Object, Object> call(Row row) throws Exception {
//                return new Tuple2<>(row.get(0), row.get(1));
//              }
//            });
//
//    MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabelRdd.rdd());
//
//    // Set of predictions, labels, features
//
//    SvcClassifierResponse response = new SvcClassifierResponse(ResponseType.OBJECT_DATA);
//    JavaRDD<String> predictedLabelAndVector = trainingResults.select("prediction", "label", "features").toJavaRDD()
//            .map(new Function<Row, String>() {
//              private static final long serialVersionUID = 1069330955080634279L;
//
//              @Override
//              public String call(Row row) {
//                StringBuilder lineBuilder = new StringBuilder();
//                int index = new Double(row.getDouble(0)).intValue();
//                String str = indicesLabelsMapping[index];                                    // convert index to label (prediction)
//                lineBuilder.append('"').append(str).append('"');                            // label
//                lineBuilder.append(Constants.CSV_SEPARATOR);                                // delimiter
//                lineBuilder.append('"').append(row.get(1)).append('"');                        // label (actual)
//                lineBuilder.append(Constants.CSV_SEPARATOR);                                // delimiter
//                StringBuilder featuresBuilder = new StringBuilder(
//                        row.get(2).toString());    // features | vector
//                lineBuilder.append(
//                        featuresBuilder.deleteCharAt(0).deleteCharAt(featuresBuilder.length() - 1).toString());
//                return lineBuilder.toString();
//              }
//            });
//
//    int maxRow = Integer.parseInt(MainEntry.restAppConfig.getSetting(Constants.CONF_MAX_RESULTS));
//    response.set(SvcClassifierResponse.PREDICTED_ACTUAL_FEATURE_INFO, predictedLabelAndVector.take(maxRow),
//            List.class);
//    listSelectedFeatures = config.getSetting(SvcClassifierSettings.LIST_FEATURES_COL);
//    response.set(SvcClassifierResponse.LIST_FEATURES, listSelectedFeatures, String[].class);
//    // labels
//    response.set(SvcClassifierResponse.LABELS, indicesLabelsMapping, String[].class);
//    // confusion matrix
//    response.set(SvcClassifierResponse.CONFUSION_MATRIX, metrics.confusionMatrix().toArray(), double[].class);
//    // Overall statistics
//    response.set(SvcClassifierResponse.ACCURACY, Utilities.roundDouble(metrics.accuracy(), 2), double.class);
//    // Weighted metrics
//    response.set(SvcClassifierResponse.WEIGHTED_PRECISION, Utilities.roundDouble(metrics.weightedPrecision(), 2),
//            double.class);
//    response.set(SvcClassifierResponse.WEIGHTED_RECALL, Utilities.roundDouble(metrics.weightedRecall(), 2),
//            double.class);
//    response.set(SvcClassifierResponse.WEIGHTED_F_MEASURE, Utilities.roundDouble(metrics.weightedFMeasure(), 2),
//            double.class);
//    response.set(SvcClassifierResponse.WEIGHTED_FALSE_POSITIVE,
//            Utilities.roundDouble(metrics.weightedFalsePositiveRate(), 2), double.class);
//    response.set(SvcClassifierResponse.WEIGHTED_TRUE_POSISTIVE,
//            Utilities.roundDouble(metrics.weightedTruePositiveRate(), 2), double.class);
//
//    String classCol = config.getSetting(AlgorithmSettings.CLASS_COL);
//    response.set(SvcClassifierResponse.CLASS_COL, classCol, String.class);
//
//    logger.info("evaluated successfully!");
//    response.set(ResponseBase.STATUS, ResponseStatus.SUCCESS, ResponseStatus.class);
//
//    return response;
//  }
//
//
//  /**
//   * to predict unlabeled data
//   *
//   * @param config
//   * @return
//   * @throws Exception
//   */
//  public IResponseObject predict(IConfigurable config) throws Exception {
//
//    // 0. Get settings
//    String dataInputOption = config.getSetting(SvcClassifierSettings.DATA_INPUT_OPTION);
//    String classCol = config.getSetting(SvcClassifierSettings.CLASS_COL);
//
//    // 1. get data
//    Dataset<Row> originalData = null;
//    // Dataset<Row> filtered = null;
//    switch (dataInputOption) {
//      case SvcClassifierSettings.INPUT_FROM_FILE: {
//        // get test data from uploaded file
//        originalData = DAOSparkDataFrame.getDfVectorFromCsvFormatedFile(config);
//        if (Arrays.asList(originalData.columns()).contains(classCol))
//          originalData = originalData.drop(classCol);
//        originalData = originalData.na().drop();
//        break;
//      }
//      case SvcClassifierSettings.INPUT_FROM_ES: {
//        // get test data from ElasticSearch
//        // data = SparkEsConnector.getMlVectorFromESWithDenseFormat(config); // TODO
//        break;
//      }
//      default: {
//        // abnormal case:
//        ResponseBase err = new ResponseBase(ResponseType.MESSAGE);
//        err.setMessage("Input method is not acceptable: " + dataInputOption);
//        return err;
//      }
//    }
//
//    // get setting
//    String[] listCols = originalData.columns();
//    List<String> listColNames = Arrays.asList(listCols);
//    String[] fieldsForPredict = config.getSetting(AlgorithmSettings.LIST_FIELD_FOR_PREDICT);
//    int[] indices = new int[fieldsForPredict.length];
//    int index = 0;
//    for (String field : fieldsForPredict) {
//      indices[index++] = listColNames.indexOf(field);
//    }
//
//    // 2.1 load model
//    String modelName = config.getSetting(SvcClassifierSettings.MODEL_NAME);
//    String modelDir = Utilities.getPathInWorkingFolder(Constants.DATA_DIR, SvcClassifierSettings.ALGORITHM_NAME,
//            Constants.MODEL_DIR, modelName);
//    OneVsRestModel model = OneVsRestModel.load(modelDir);
//    // 2.1 load StringIndexer model
//    modelDir = Utilities.getPathInWorkingFolder(Constants.DATA_DIR, SvcClassifierSettings.ALGORITHM_NAME,
//            Constants.MODEL_INDEXER_DIR, modelName);
//    StringIndexerModel labelIndexer = StringIndexerModel.load(modelDir);
//    String[] indicesLabelsMapping = labelIndexer.labels();
//
//    // Convert/cast StringType to DecimalType (VectorAssembler does not support
//    // StringType)
//    String[] fieldsForAssembling = new String[fieldsForPredict.length];
//    index = 0;
//    for (String field : fieldsForPredict) {
//      fieldsForAssembling[index++] = field + "_str";
//      originalData = originalData.withColumn(field + "_str",
//              originalData.col(field).cast(new DecimalType(38, 8)));
//    }
//    // invalid field will be null, replace with 0 instead
//    originalData = originalData.na().fill(0.0);
//
//    // Create `features` column using VectorAssembler
//    VectorAssembler assembler = new VectorAssembler().setInputCols(fieldsForAssembling).setOutputCol("features")
//            .setHandleInvalid("skip");
//    Dataset<Row> data = assembler.transform(originalData);
//
//    // 3. predict
//
//    Dataset<Row> result = model.transform(data);
//    Dataset<Row> predictionFeatures = result.select("prediction", listCols);
//
//    IResponseObject response = new SvcClassifierResponse(ResponseType.OBJECT_DATA);
//    JavaRDD<String> lineData = predictionFeatures.toJavaRDD().map(new Function<Row, String>() {
//      private static final long serialVersionUID = -4227208761238478293L;
//
//      @Override
//      public String call(Row rowData) throws Exception {
//        StringBuilder lineBuilder = new StringBuilder();
//        int index = new Double(
//                rowData.getDouble(0)).intValue();                                    // index of label
//        lineBuilder.append('"').append(indicesLabelsMapping[index])
//                .append('"');                    // convert to categorical label
//        lineBuilder.append(
//                Constants.CSV_SEPARATOR);                                                                // delimiter
//        for (int iter = 1; iter <= listCols.length; ++iter) {
//          if (rowData.get(iter) == null) {
//            lineBuilder.append('"').append('"');
//          } else {
//            lineBuilder.append('"').append(rowData.get(iter)).append('"');
//          }
//          lineBuilder.append(Constants.CSV_SEPARATOR);
//        }
//        lineBuilder.deleteCharAt(
//                lineBuilder.length() - 1);                                            // delete last delimiter (redundant)
//        return lineBuilder.toString();
//      }
//    });
//
//    response.set(SvcClassifierResponse.PREDICTED_FEATURE_INFO, lineData.collect(), List.class);
//    response.set(SvcClassifierResponse.LIST_FEATURES, listCols, String[].class);
//    response.set(RandomForestClassifierResponse.CLASS_COL, classCol, String.class);
//
//    logger.info("predicted unlabeled data successfully.");
//    response.set(ResponseBase.STATUS, ResponseStatus.SUCCESS, ResponseStatus.class);
//
//    return response;
//  }
}
