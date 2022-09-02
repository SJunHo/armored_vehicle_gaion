package kr.gaion.armoredVehicle.spark.controller;

import au.com.bytecode.opencsv.CSVReader;
import kr.gaion.armoredVehicle.algorithm.classifier.LogisticRegressionClassifier;
//import kr.gaion.armoredVehicle.algorithm.classifier.MLPClassifier;
//import kr.gaion.armoredVehicle.algorithm.classifier.RandomForestClassifier;
//import kr.gaion.armoredVehicle.algorithm.classifier.SVM;
//import kr.gaion.armoredVehicle.algorithm.clustering.IsolationForestOutlierDetection;
//import kr.gaion.armoredVehicle.algorithm.clustering.KmeansClustering;
import kr.gaion.armoredVehicle.algorithm.classifier.MLPClassifier;
import kr.gaion.armoredVehicle.algorithm.classifier.RandomForestClassifier;
import kr.gaion.armoredVehicle.algorithm.classifier.SVM;
import kr.gaion.armoredVehicle.algorithm.clustering.IsolationForestOutlierDetection;
import kr.gaion.armoredVehicle.algorithm.clustering.KmeansClustering;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.ClusterTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.*;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.algorithm.featureSelector.PcaDimensionalityReduction;
import kr.gaion.armoredVehicle.algorithm.regressor.LinearRegressor;
import kr.gaion.armoredVehicle.common.DataConfig;
import kr.gaion.armoredVehicle.database.model.TempLifeData;
import kr.gaion.armoredVehicle.database.model.TrainingBearing;
import kr.gaion.armoredVehicle.database.repository.FileInfoRepository;
import kr.gaion.armoredVehicle.dataset.service.DatasetDatabaseService;
import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.ml.dto.ModelResponse;
import kr.gaion.armoredVehicle.ml.dto.input.UpdateModelInput;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import kr.gaion.armoredVehicle.spark.DatabaseSparkService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.SparkSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

//import static kr.gaion.armoredVehicle.spark.controller.TestSpark.ReadCSV;

@RestController("api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MLController {
  // Controller: 사용자의 요청(request)을 어떻게 처리할지 결정하는 파트입니다. 즉, Controller에 정의 된 기준대로 요청을 처리합니다.
  // @Controller: Controller의 역할을 수행한다고 명시해주는 어노테이션, Controller의 내용을 기준 삼아 요청을 처리.
  // @RequestMapping(vale=" ", method= ): Controller에 들어온 요청을 처리하는 기준점입니다. 서버의 URL + value로 매핑되며 method의 RequestMethod.GET or POST. 주소창에 URL + value를 입력하면 method가 수행됩니다.
  @NonNull private final RandomForestClassifier rfc;
  @NonNull private final SVM svm;
  @NonNull private final MLPClassifier mlp;
  @NonNull private final LogisticRegressionClassifier lr;
  @NonNull private final KmeansClustering kmeansClustering;
  @NonNull private final IsolationForestOutlierDetection isolationForestOutlierDetection;
  @NonNull private final EsConnector esConnector;
  @NonNull private final DataConfig dataConfig;
  @NonNull private final ModelService modelService;
  @NonNull private final SparkSession sparkSession;
  @NonNull private final FSChiSqSelector chiSqSelector;
  @NonNull private final PcaDimensionalityReduction pcaDimensionalityReduction;
  @NonNull private final LinearRegressor linearRegressor;
  @NonNull private final DatabaseSparkService databaseSparkService;
  @NonNull private final DatasetDatabaseService datasetDatabaseService;
  @NonNull private final FileInfoRepository fileInfoRepository;

  @PostMapping(path = "/api/train/rfc")
  public RandomForestClassificationResponse trainRfc(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
    return (RandomForestClassificationResponse) rfc.train(input);
  }

  @PostMapping(path = "/api/train/svm")
  public SVMClassificationResponse trainSVM(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
    return (SVMClassificationResponse) svm.train(input);
  }

  @PostMapping(path = "/api/train/lr")
  public ClassificationResponse trainLr(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
    return lr.train(input);
  }

  @PostMapping(path = "/api/train/mlp")
  public ClassificationResponse trainMLP(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
    return mlp.train(input);
  }

  @PostMapping(path = "/api/train/kmean")
  public ClusterResponse trainKmean(@RequestBody ClusterTrainInput input) throws Exception {
    return kmeansClustering.train(input);
  }

  @PostMapping(path = "/api/train/if")
  public ClusterResponse trainIsolationForest(@RequestBody ClusterTrainInput input) throws Exception {
    return isolationForestOutlierDetection.train(input);
  }

  @PostMapping(path = "/api/train/linear")
  public LinearRegressionTrainResponse trainLinearRegression(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
    return linearRegressor.train(input);
  }

//  @GetMapping(path = "/api/test")
//  public Dataset<Row> test() {
//    var result = databaseSparkService.getLabeledDatasetFromDatabase();
//    return result;
//  }

  @PostMapping(path = "/api/predict/{algorithmName}")
  public ClassificationResponse predict(@RequestBody BaseAlgorithmPredictInput input, @PathVariable String algorithmName) throws Exception {
    switch (algorithmName) {
      case "rfc": {
        return this.rfc.predict(input);
      }
      case "svm": {
        return this.svm.predict(input);
      }
      case "mlp": {
        return this.mlp.predict(input);
      }
      case "lr": {
        return this.lr.predict(input);
      }
      case "linear": {
        return this.linearRegressor.predict(input);
      }
      default: {
        throw new Error("Unsupported algorithm");
      }
    }
  }

  @PostMapping(path = "/api/cluster-predict/{algorithmName}")
  public ClusterResponse predictCluster(@RequestBody BaseAlgorithmPredictInput input, @PathVariable String algorithmName) throws Exception {
    switch (algorithmName) {
      case "kmean": {
        return this.kmeansClustering.predict(input);
      }
      case "if": {
        return this.isolationForestOutlierDetection.predict(input);
      }
      default: {
        throw new Error("Unsupported algorithm");
      }
    }
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

  @GetMapping(path = "/api/get-trainingData")
  public String[] getTrainingDataList(){
    return fileInfoRepository.findTrainingDataNameList();
  }

  @GetMapping(path = "/api/get-trainingData/{index}")
  public String[] getTrainingDataColumnList(@PathVariable String index) throws IOException {
    String type = "";
    if(index.contains("bearing")){
      type = "bearing";
    }else if(index.contains("server")){
      type = "server";
    }
    System.out.println(type);
    switch (type) {
      case "bearing": {
        Field[] fields = TrainingBearing.class.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();

        for (Field field : fields){
          fieldNames.add(field.getName());
        }
        System.out.println(fieldNames);

        return fieldNames.toArray(new String[0]);
      }
      case "server": {
        Field[] fields = TempLifeData.class.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();

        for (Field field : fields)
          fieldNames.add(field.getName());

        return fieldNames.toArray(new String[0]);
      }

      default: {
        throw new Error("Unsupported algorithm");
      }
    }


//    String path = "D:\\Sources\\armored-vehicle\\test-data\\" + index + ".csv";
//    CSVReader reader = new CSVReader(new FileReader(path));
//    String[] header = reader.readNext();
//    return header;
  }


//  @GetMapping(path = "/api/es-mappings")
//  public String[] getEsDatasetIndicesList() {
//    return dataConfig.getEsDatasetIndices();
//  }
//
//  @GetMapping(path = "/api/es-mapping/{index}")
//  public String[] getIndexMappings(@PathVariable String index) throws IOException {
//    var res = esConnector.getEsIndexMappings(index);
//    return ((Map<String, Object>) res.sourceAsMap().get("properties")).keySet().toArray(new String[]{});
//  }

  @PostMapping(path = "/api/fs/chi-sq")
  public FSResponse chiSquareFS(@RequestBody BaseAlgorithmTrainInput input) {
    return this.chiSqSelector.trainWithDataFrameApi(input);
  }

  @PostMapping(path = "/api/fs/pca")
  public FSResponse pcaDimensionalityReduction(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
    return this.pcaDimensionalityReduction.train(input);
  }
}
