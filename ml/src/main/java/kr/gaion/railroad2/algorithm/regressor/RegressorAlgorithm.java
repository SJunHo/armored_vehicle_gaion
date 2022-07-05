package kr.gaion.railroad2.algorithm.regressor;

import kr.gaion.railroad2.algorithm.AlgorithmConfig;
import kr.gaion.railroad2.algorithm.MLAlgorithm;
import kr.gaion.railroad2.algorithm.ModelUtilService;
import kr.gaion.railroad2.algorithm.dto.ResponseStatus;
import kr.gaion.railroad2.algorithm.dto.ResponseType;
import kr.gaion.railroad2.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.railroad2.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.railroad2.algorithm.dto.response.ClassificationResponse;
import kr.gaion.railroad2.algorithm.dto.response.LinearRegressionTrainResponse;
import kr.gaion.railroad2.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.railroad2.common.DataConfig;
import kr.gaion.railroad2.common.Utilities;
import kr.gaion.railroad2.dataset.config.StorageConfig;
import kr.gaion.railroad2.elasticsearch.EsConnector;
import kr.gaion.railroad2.ml.service.ModelService;
import kr.gaion.railroad2.spark.ElasticsearchSparkService;
import kr.gaion.railroad2.spark.dto.NumericLabeledData;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.linalg.DenseVector;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.ml.regression.LinearRegressionTrainingSummary;
import org.apache.spark.ml.util.MLWritable;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;

@Log4j
public class RegressorAlgorithm {

}
