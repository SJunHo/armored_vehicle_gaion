//package kr.gaion.armoredVehicle.algorithm.clustering;
//
//import com.google.gson.Gson;
//import kr.gaion.armoredVehicle.algorithm.AlgorithmConfig;
//import kr.gaion.armoredVehicle.algorithm.ModelUtilService;
//import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
//import kr.gaion.armoredVehicle.algorithm.dto.input.ClusterTrainInput;
//import kr.gaion.armoredVehicle.algorithm.dto.input.FileInput;
//import kr.gaion.armoredVehicle.algorithm.dto.response.ClusterResponse;
//import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
//import kr.gaion.armoredVehicle.algorithm.featureSelector.PcaDimensionalityReduction;
//import kr.gaion.armoredVehicle.common.DataConfig;
//import kr.gaion.armoredVehicle.common.HdfsHelperService;
//import kr.gaion.armoredVehicle.common.Utilities;
//import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
//import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
//import kr.gaion.armoredVehicle.ml.service.ModelService;
//import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
//import lombok.NonNull;
//import lombok.extern.log4j.Log4j;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.ml.clustering.KMeans;
//import org.apache.spark.ml.clustering.KMeansModel;
//import org.apache.spark.ml.linalg.SQLDataTypes;
//import org.apache.spark.ml.linalg.Vector;
//import org.apache.spark.ml.linalg.VectorUDT;
//import org.apache.spark.ml.linalg.Vectors;
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.RowFactory;
//import org.apache.spark.sql.SparkSession;
//import org.apache.spark.sql.api.java.UDF1;
//import org.apache.spark.sql.api.java.UDF2;
//import org.apache.spark.sql.expressions.UserDefinedFunction;
//import org.apache.spark.sql.types.DataTypes;
//import org.apache.spark.sql.types.StructType;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import static org.apache.spark.sql.functions.*;
//
//@Service
//@Log4j
//public class KmeansClustering extends ClusterMlAlgorithm<KMeansModel> {
//	private final HdfsHelperService hdfsHelperService;
//  public KmeansClustering(@NonNull ElasticsearchSparkService elasticsearchSparkService, @NonNull Utilities utilities, @NonNull StorageConfig storageConfig, @NonNull ModelUtilService modelUtil, @NonNull EsConnector esConnector, @NonNull FSChiSqSelector chiSqSelector, @NonNull AlgorithmConfig algorithmConfig, @NonNull DataConfig dataConfig, @NonNull SparkSession sparkSession, @NonNull ModelService modelService, @NonNull PcaDimensionalityReduction dimensionalityReduction, @NonNull HdfsHelperService hdfsHelperService) {
//		super(elasticsearchSparkService, utilities, storageConfig, modelUtil, esConnector, chiSqSelector, algorithmConfig, dataConfig, sparkSession, "KmeansClustering", modelService, dimensionalityReduction);
//		this.hdfsHelperService = hdfsHelperService;
//	}
//
//  @Override
//  protected void doSaveModel(KMeansModel kMeansModel, ClusterTrainInput config) throws Exception {
//    // Saving the Clustering model
//    log.info("Saving model ..");
//    this.saveModel(config, kMeansModel);
//  }
//
//  private Map<Integer, Long> getTotalPointsEachCluster(int numClusters, Dataset<Row> dataForSavingToFile) {
//    var totalPointsEachCluster = new HashMap<Integer, Long>();
//    for (int index = 0; index < numClusters; ++index) {
//      String val = Integer.toString(index);
//      long total = dataForSavingToFile.filter(col("prediction").equalTo(lit(val))).count();
//      totalPointsEachCluster.put(index, total);
//    }
//    return totalPointsEachCluster;
//  }
//
//  @Override
//  protected Dataset<Row> predictUnlabeledData(Dataset<Row> data, boolean isTagAvailable, boolean dimensionalityReductionEnableFlg, String modelDir) {
//    // 2. load model
//    KMeansModel model = KMeansModel.load(modelDir);
//    var res = model.transform(data);
//    return dimensionalityReductionEnableFlg ? res.withColumn("vector", res.col("features")).toDF() : res;
//  }
//
//  @Override
//  protected KMeansModel trainModel(ClusterTrainInput config, Dataset<Row> trainData) {
//    // setting algorithm's parameters
//    return new KMeans().setK(config.getNumClusters())
//        .setMaxIter(config.getMaxIter())
//        .setSeed(config.getLSeed())
//        .setFeaturesCol("features")
//				.fit(trainData);
//  }
//
//  protected Dataset<Row> enrichPredictedData(BaseAlgorithmPredictInput input, Dataset<Row> predictedData) throws Exception {
//    String clusterInfoDir = this.getModelFullPath(input.getModelName()) + "//clusterInfo";
//		log.info("clusterInfoDir = " + clusterInfoDir);
//		String [] clusterInfoFileNameList = this.hdfsHelperService.getListFilesInDir(clusterInfoDir);
//		String clusterInfoFileName = "";
//		for (String fName : clusterInfoFileNameList) {
//			if ( fName.contains(".csv") ) {
//				clusterInfoFileName = fName;
//				break;
//			}
//		}
//		var csvConfig = new FileInput();
//		csvConfig.setFileName(clusterInfoDir + "//" + clusterInfoFileName);
//		csvConfig.setSeparator(",");
//
//		Dataset<Row> clusterInfoFileDF = this.elasticsearchSparkService.getDfVectorFromCsvFormattedFile(csvConfig)
//				.withColumn("centroid", jsonToVector().apply(col("centroid")));
//
//		Dataset<Row> testClusterDf = predictedData
//				.withColumnRenamed("prediction", "clusterId")
//				.withColumnRenamed("features", "vector");
//		var clusterCenterDf = testClusterDf
//				.select("clusterId", "vector")
//        .join(clusterInfoFileDF, "clusterId");
//		var clusterInfoDF = clusterCenterDf
//        .withColumn("dis", sqdist().apply(col("vector"), col("centroid")))
//        .orderBy("dis")
//        .groupBy("clusterId")
//        .agg(first(col("radius")).as("radius"), first(col("dis")).as("dis"), first(col("centroid")).as("centroid"))
//				.select("clusterId", "centroid", "dis", "radius");
//
//		return testClusterDf
//				.join(clusterInfoDF, "clusterId")
//				.withColumn("ratio", col("dis").divide(col("radius")))
//				.withColumn("predictedTag", when(
//						col("ratio").geq(lit(1)).or(col("radius").equalTo(lit(0))),
//						"20").otherwise("10"))
//				.withColumnRenamed("vector", "features")
//				.orderBy("ratio")
//				.select("predictedTag", "clusterId", "ratio", "features");
//  }
//
//  @Override
//  protected Dataset<Row> predictData(Dataset<Row> input, KMeansModel kMeansModel) {
//    return kMeansModel.transform(input);
//  }
//
//  private static Dataset<Row> getClusterInfo(SparkSession sparkSession, Dataset<Row> centerDisDf) {
//    sparkSession.udf().register("vector2String", new UDF1<Vector, String>() {
//			private static final long serialVersionUID = 1L;
//			@Override
//			public String call(Vector vec) {
//				return Arrays.toString(vec.toArray());
//			}
//		}, DataTypes.StringType);
//
//		return centerDisDf.withColumn("centroid", callUDF("vector2String", centerDisDf.col("centroid")) );
//  }
//
//	private UserDefinedFunction sqdist() {
//		return udf((UDF2<Vector, Vector, Double>) Vectors::sqdist, DataTypes.DoubleType);
//	}
//
//	private UserDefinedFunction jsonToVector() {
//		return udf((UDF1<String, Vector>) inputStr -> {
//			var gson = new Gson();
//			double[] parsed = gson.fromJson(inputStr, double[].class);
//			return Vectors.dense(parsed);
//		}, new VectorUDT());
//	}
//
//	private void saveClusterInfo(Dataset<Row> resultDf, KMeansModel model, ClusterTrainInput config) throws Exception {
//		var sc = JavaSparkContext.fromSparkContext(this.sparkSession.sparkContext());
//
//		Vector[] clusterCenter = model.clusterCenters();
//		List<Row> centerPoint = IntStream.range(0, clusterCenter.length).mapToObj(i -> {
//      Vector vec = clusterCenter[i];
//      return RowFactory.create(i, vec);
//    }).collect(Collectors.toList());
//    StructType centerSt = new StructType()
//				.add("clusterId", DataTypes.IntegerType)
//				.add("vector", SQLDataTypes.VectorType());
//		Dataset<Row> centerDf = this.sparkSession.createDataFrame(sc.parallelize(centerPoint), centerSt)
//        .withColumnRenamed("vector", "centroid");
//		Dataset<Row> clusterCenterDf = resultDf
//        .withColumnRenamed("prediction", "clusterId")
//        .withColumnRenamed("features", "vector")
//        .select("clusterId", "vector")
//        .join(centerDf, "clusterId");
//
//    var centerDisDf = clusterCenterDf
//        .withColumn("radius", sqdist().apply(col("vector"), col("centroid")))
//        .orderBy("radius")
//        .groupBy("clusterId")
//        .agg(first(col("radius")).as("radius"), first(col("centroid")).as("centroid"))
//        .select("clusterId", "centroid", "radius");
//
//		/*
//		 * To do
//		 * Save centroid and radius information
//		 */
//		Dataset<Row> clusterInfo = getClusterInfo(sparkSession, centerDisDf);
//
//		clusterInfo.coalesce(1).write().option("header", "true").csv(this.getModelFullPath(config.getModelName()) + "//clusterInfo");
//	}
//
//  @Override
//  protected final void enrichTrainResponse(ClusterResponse response, KMeansModel model, Dataset<Row> resultDf, ClusterTrainInput config) throws Exception {
//    // filter, count total points in each cluster
//    Map<Integer, Long> totalPointsEachCluster = getTotalPointsEachCluster(config.getNumClusters(),  resultDf);
//
//    //total points in each cluster
//    response.setTotalPointsEachCluster(totalPointsEachCluster);
//    // cluster-feature
//
//    // cluster's centers list
//    List<double[]> centers = new ArrayList<>();
//    for (Vector center : model.clusterCenters()) {
//      centers.add(center.toArray());
//    }
//    response.setCenters(centers);
//    // columns list
//
//    saveClusterInfo(resultDf, model, config);
//  }
//}