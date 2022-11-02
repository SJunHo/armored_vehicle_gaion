package kr.gaion.armoredVehicle.spark.controller;

import au.com.bytecode.opencsv.CSVReader;
import kr.gaion.armoredVehicle.algorithm.classifier.LogisticRegressionClassifier;
import kr.gaion.armoredVehicle.algorithm.classifier.MLPClassifier;
import kr.gaion.armoredVehicle.algorithm.classifier.RandomForestClassifier;
import kr.gaion.armoredVehicle.algorithm.classifier.SupportVectorClassifier;
import kr.gaion.armoredVehicle.algorithm.clustering.IsolationForestOutlierDetection;
import kr.gaion.armoredVehicle.algorithm.clustering.KmeansClustering;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.ClusterTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.*;
import kr.gaion.armoredVehicle.algorithm.featureSelector.FSChiSqSelector;
import kr.gaion.armoredVehicle.algorithm.featureSelector.PcaDimensionalityReduction;
import kr.gaion.armoredVehicle.algorithm.regressor.LassoRegressor;
import kr.gaion.armoredVehicle.algorithm.regressor.LinearRegressor;
import kr.gaion.armoredVehicle.database.model.DbModelResponse;
import kr.gaion.armoredVehicle.database.repository.FileInfoRepository;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.ml.dto.input.UpdateModelInput;
import kr.gaion.armoredVehicle.ml.service.ModelService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

//import static kr.gaion.armoredVehicle.spark.controller.TestSpark.ReadCSV;

@RestController("api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MLController {
    @NonNull
    private final RandomForestClassifier rfc;
    @NonNull
    private final SupportVectorClassifier svc;
    @NonNull
    private final MLPClassifier mlp;
    @NonNull
    private final LogisticRegressionClassifier lr;
    @NonNull
    private final LinearRegressor linearRegressor;
    @NonNull
    private final LassoRegressor lassoRegressor;
    @NonNull
    private final KmeansClustering kmeansClustering;
    @NonNull
    private final IsolationForestOutlierDetection isolationForestOutlierDetection;
    @NonNull
    private final ModelService modelService;
    @NonNull
    private final FSChiSqSelector chiSqSelector;
    @NonNull
    private final PcaDimensionalityReduction pcaDimensionalityReduction;
    @NonNull
    private final FileInfoRepository fileInfoRepository;
    @NonNull
    private final StorageConfig storageConfig;


    @PostMapping(path = "/api/train/rfc")
    public RandomForestClassificationResponse trainRfc(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
        return (RandomForestClassificationResponse) rfc.trainRF(input);
    }

    @PostMapping(path = "/api/train/svc")
    public ClassificationResponse trainSVC(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
        return svc.trainSVC(input);
    }

    @PostMapping(path = "/api/train/lr")
    public ClassificationResponse trainLr(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
        return lr.trainLR(input);
    }

    @PostMapping(path = "/api/train/mlp")
    public ClassificationResponse trainMLP(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
        return mlp.trainMLP(input);
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
    public RegressionResponse trainLinearRegression(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
        return linearRegressor.train(input);
    }

    @PostMapping(path = "/api/train/lasso")
    public RegressionResponse trainLassoRegression(@RequestBody BaseAlgorithmTrainInput input) throws Exception {
        return lassoRegressor.train(input);
    }

    @PostMapping(path = "/api/classification-predict/{algorithmName}")
    public ClassificationResponse classification_predict(@RequestBody BaseAlgorithmPredictInput input, @PathVariable String algorithmName) throws Exception {
        switch (algorithmName) {
            case "rfc": {
                return this.rfc.predictRF(input);
            }
            case "svc": {
                return this.svc.predictSVC(input);
            }
            case "mlp": {
                return this.mlp.predictMLP(input);
            }
            case "lr": {
                return this.lr.predictLR(input);
            }
            default: {
                throw new Error("Unsupported algorithm");
            }
        }
    }

    @PostMapping(path = "/api/regression-predict/{algorithmName}")
    public RegressionResponse regression_predict(@RequestBody BaseAlgorithmPredictInput input, @PathVariable String algorithmName) throws Exception {
        switch (algorithmName) {
            case "linear": {
                return this.linearRegressor.predict(input);
            }
            case "lasso": {
                return this.lassoRegressor.predict(input);
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
    public List<DbModelResponse> getModels(@PathVariable String algorithm) {
        return modelService.getModelResponse(algorithm);
    }

    @PostMapping(path = "/api/ml/{algorithm}/model/{algorithmResponseId}")
    public DbModelResponse updateModel(@PathVariable String algorithm, @PathVariable Long algorithmResponseId, @RequestBody UpdateModelInput update) {
        try {
            return modelService.updateModel(algorithm, algorithmResponseId, update);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/api/ml/{algorithm}/model/{algorithmResponseId}")
    public Boolean deleteModel(@PathVariable String algorithm, @PathVariable Long algorithmResponseId) {
        try {
            return modelService.deleteModel(algorithm, algorithmResponseId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/api/get-trainingData/{partType}")
    public String[] getTrainingDataList(@PathVariable String partType) {
        return fileInfoRepository.findTrainingDataNameList(partType.charAt(0));
    }

    @GetMapping(path = "/api/get-trainingDataColumnList/{index}")
    public String[] getTrainingDataColumnList(@PathVariable String index) throws IOException {

        String path = storageConfig.getHomeDir() + index + ".csv";

        CSVReader reader = new CSVReader(new FileReader(path));
        return reader.readNext();
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
