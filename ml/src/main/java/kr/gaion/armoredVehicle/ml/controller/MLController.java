package kr.gaion.armoredVehicle.ml.controller;

import kr.gaion.armoredVehicle.algorithm.classifier.LogisticRegressionClassifier;
//import kr.gaion.armoredVehicle.algorithm.classifier.MLPClassifier;
//import kr.gaion.armoredVehicle.algorithm.classifier.RandomForestClassifier;
//import kr.gaion.armoredVehicle.algorithm.classifier.SVM;
//import kr.gaion.armoredVehicle.algorithm.clustering.IsolationForestOutlierDetection;
//import kr.gaion.armoredVehicle.algorithm.clustering.KmeansClustering;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.ClusterTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.*;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.algorithm.featureSelector.PcaDimensionalityReduction;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.ml.dto.ModelResponse;
import kr.gaion.armoredVehicle.ml.dto.input.UpdateModelInput;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.DatabaseSparkService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController("api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MLController {
//  @NonNull private final RandomForestClassifier rfc;
//  @NonNull private final SVM svm;
//  @NonNull private final MLPClassifier mlp;
  @NonNull private final LogisticRegressionClassifier lr;
//  @NonNull private final KmeansClustering kmeansClustering;
//  @NonNull private final IsolationForestOutlierDetection isolationForestOutlierDetection;
  @NonNull private final EsConnector esConnector;
  @NonNull private final DataConfig dataConfig;
  @NonNull private final ModelService modelService;
  @NonNull private final SparkSession sparkSession;
  @NonNull private final FSChiSqSelector chiSqSelector;
  @NonNull private final PcaDimensionalityReduction pcaDimensionalityReduction;
  @NonNull private final DatabaseSparkService databaseSparkService;

//  @PostMapping(path = "/api/train/rfc")
//  public RandomForestClassificationResponse trainRfc(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
//    return (RandomForestClassificationResponse) rfc.train(input);
//  }
//
//  @PostMapping(path = "/api/train/svm")
//  public SVMClassificationResponse trainSVM(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
//    return (SVMClassificationResponse) svm.train(input);
//  }

  @PostMapping(path = "/api/train/lr")
  public ClassificationResponse trainLr(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
    return lr.train(input);
  }

//  @PostMapping(path = "/api/train/mlp")
//  public ClassificationResponse trainMLP(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
//    return mlp.train(input);
//  }

//  @PostMapping(path = "/api/train/kmean")
//  public ClusterResponse trainKmean(@RequestBody ClusterTrainInput input) throws Exception {
//    return kmeansClustering.train(input);
//  }
//
//  @PostMapping(path = "/api/train/if")
//  public ClusterResponse trainIsolationForest(@RequestBody ClusterTrainInput input) throws Exception {
//    return isolationForestOutlierDetection.train(input);
//  }

  @GetMapping(path = "/api/test")
  public Dataset<Row> test() {
    var result = databaseSparkService.getDataRDDFromDb();
    return result;
  }

  @PostMapping(path = "/api/predict/{algorithmName}")
  public ClassificationResponse predict(@RequestBody BaseAlgorithmPredictInput input, @PathVariable String algorithmName) throws Exception {
    switch (algorithmName) {
//      case "rfc": {
//        return this.rfc.predict(input);
//      }
//      case "svm": {
//        return this.svm.predict(input);
//      }
//      case "mlp": {
//        return this.mlp.predict(input);
//      }
      case "lr": {
        return this.lr.predict(input);
      }
      default: {
        throw new Error("Unsupported algorithm");
      }
    }
  }

  @PostMapping(path = "/api/cluster-predict/{algorithmName}")
  public ClusterResponse predictCluster(@RequestBody BaseAlgorithmPredictInput input, @PathVariable String algorithmName) throws Exception {
    switch (algorithmName) {
//      case "kmean": {
//        return this.kmeansClustering.predict(input);
//      }
//      case "if": {
//        return this.isolationForestOutlierDetection.predict(input);
//      }
      default: {
        throw new Error("Unsupported algorithm");
      }
    }
  }

  @GetMapping(path = "/api/es-mapping/{index}")
  public String[] getIndexMappings(@PathVariable String index) throws IOException {
    var res = esConnector.getEsIndexMappings(index);
    return ((Map<String, Object>) res.sourceAsMap().get("properties")).keySet().toArray(new String[]{});
  }

  @GetMapping(path = "/api/ml/{algorithm}/models")
  public List<ModelResponse> getModels(@PathVariable String algorithm) {
    return modelService.getModelResponse(algorithm);
  }

  @PostMapping(path = "/api/ml/{algorithm}/model/{esId}")
  public ModelResponse updateModel(@PathVariable String algorithm, @PathVariable String esId, @RequestBody UpdateModelInput update) {
    try {
      return modelService.updateModel(algorithm, esId, update);
    } catch (IOException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping(path = "/api/ml/{algorithm}/model/{esId}")
  public Boolean deleteModel(@PathVariable String algorithm, @PathVariable String esId) {
    try {
      return modelService.deleteModel(algorithm, esId);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(path = "/api/es-mappings")
  public String[] getEsDatasetIndicesList() {
    return dataConfig.getEsDatasetIndices();
  }

  @PostMapping(path = "/api/fs/chi-sq")
  public FSResponse chiSquareFS(@RequestBody BaseAlgorithmTrainInput input) {
    return this.chiSqSelector.trainWithDataFrameApi(input);
  }

  @PostMapping(path = "/api/fs/pca")
  public FSResponse pcaDimensionalityReduction(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
    return this.pcaDimensionalityReduction.train(input);
  }
}
