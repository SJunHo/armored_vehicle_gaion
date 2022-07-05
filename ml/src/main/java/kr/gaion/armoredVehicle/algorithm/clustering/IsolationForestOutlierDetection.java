package kr.gaion.armoredVehicle.algorithm.clustering;

import com.linkedin.relevance.isolationforest.IsolationForest;
import com.linkedin.relevance.isolationforest.IsolationForestModel;
import kr.gaion.armoredVehicle.algorithm.AlgorithmConfig;
import kr.gaion.armoredVehicle.algorithm.ModelUtilService;
import kr.gaion.armoredVehicle.algorithm.dto.input.ClusterTrainInput;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.algorithm.featureSelector.PcaDimensionalityReduction;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.DatabaseSparkService;
import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
import lombok.NonNull;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.hibernate.dialect.Database;
import org.springframework.stereotype.Service;

@Service
public class IsolationForestOutlierDetection extends ClusterMlAlgorithm<IsolationForestModel> {
  public IsolationForestOutlierDetection(@NonNull ElasticsearchSparkService elasticsearchSparkService, @NonNull DatabaseSparkService databaseSparkService, @NonNull Utilities utilities, @NonNull StorageConfig storageConfig, @NonNull ModelUtilService modelUtil, @NonNull FSChiSqSelector chiSqSelector, @NonNull AlgorithmConfig algorithmConfig, @NonNull DataConfig dataConfig, @NonNull SparkSession sparkSession, @NonNull PcaDimensionalityReduction dimensionalityReduction, @NonNull ModelService modelService) {
    super(elasticsearchSparkService, databaseSparkService, utilities, storageConfig, modelUtil, chiSqSelector, algorithmConfig, dataConfig, sparkSession, "isolationForestOutlierDetection", modelService, dimensionalityReduction);
  }

  @Override
  protected Dataset<Row> predictUnlabeledData(Dataset<Row> data, boolean isTagAvailable, boolean dimensionalityReductionEnableFlg, String modelDir) {
    var isolationForestModel = IsolationForestModel.load(modelDir);
    var result = isolationForestModel.transform(data);
    return result
        .withColumn("prediction", functions.when(result.col("prediction").equalTo(functions.lit(0)), 20).otherwise(30));
  }

  @Override
  protected IsolationForestModel trainModel(ClusterTrainInput input, Dataset<Row> trainData) {
    var outlierCount = trainData.filter(trainData.col("label").gt(20)).count();
    var totalCount = trainData.count();
    var isolationForest = new IsolationForest();
    isolationForest
        .setFeaturesCol("features")
        //      .setPredictionCol(PredictionCol)
        //      .setScoreCol(ScoreCol)
        .setNumEstimators(input.getNumClusters())
        .setBootstrap(input.isBootstrap())
        .setMaxSamples(input.getFraction())
        .setMaxFeatures(input.getMaxFeatures())
        .setContamination((outlierCount*1.0)/(totalCount*1.0))
        .setPredictionCol("prediction")
        //      .setContaminationError(0.01 * config.contamination)
        .setContaminationError(0.0);
    return isolationForest.fit(trainData);
  }

  @Override
  protected Dataset<Row> predictData(Dataset<Row> input, IsolationForestModel isolationForestModel) {
    var result = isolationForestModel.transform(input);
    return result
        .withColumn("prediction", functions.when(result.col("prediction").equalTo(functions.lit(0)), 20).otherwise(30));
  }

  @Override
  protected void doSaveModel(IsolationForestModel isolationForestModel, ClusterTrainInput input) throws Exception {
    this.saveModel(input, isolationForestModel);
  }
}
