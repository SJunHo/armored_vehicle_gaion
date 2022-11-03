package kr.gaion.armoredVehicle.ml.service;

import com.google.gson.Gson;
import kr.gaion.armoredVehicle.algorithm.dto.response.AlgorithmResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClassificationResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClusterResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.RegressionResponse;
import kr.gaion.armoredVehicle.common.HdfsHelperService;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.database.model.DbModelResponse;
import kr.gaion.armoredVehicle.database.repository.DBModelResponseRepository;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.ml.dto.input.UpdateModelInput;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
@RequiredArgsConstructor
@Log4j
public class ModelService {
    @NonNull
    private final EsConnector esConnector;
    @NonNull
    private final Utilities utilities;
    @NonNull
    private final StorageConfig storageConfig;
    @NonNull
    private final HdfsHelperService hdfsHelperService;
    @NonNull
    private final DBModelResponseRepository dbModelResponseRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public DbModelResponse updateModel(String algorithmName, Long algorithmResponseId, UpdateModelInput input) throws IOException {
        DbModelResponse dbModelResponse = dbModelResponseRepository.findById(algorithmResponseId).get();
        dbModelResponse.setDescription(input.getDescription());
        dbModelResponse.setChecked(input.getChecked());
        dbModelResponseRepository.save(dbModelResponse);
        return dbModelResponse;
    }

    public boolean deleteModel(String algorithmName, Long algorithmResponseId) throws Exception {
        try {
            var res = dbModelResponseRepository.findById(algorithmResponseId);
            dbModelResponseRepository.deleteById(algorithmResponseId);
            String rootDir = this.utilities.getPathInWorkingFolder(this.storageConfig.getDataDir(), algorithmName, this.storageConfig.getModelDir());
            String pathname = rootDir + File.separator + res.get().getModelName();
            this.hdfsHelperService.deleteIfExist(pathname);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("Delete failed. Cause: " + e);
            log.warn(String.format("The index %s not found.", algorithmName));
            return false;
        }
    }

    private String getAlgorithmESIndex(String algorithmName) {
        return algorithmName.toLowerCase() + "_2";
    }

    public List<DbModelResponse> getModelResponse(String algorithm) {
        return dbModelResponseRepository.getModelResponseListByAlgorithm(algorithm);
    }

    public String insertNewMlResponse(AlgorithmResponse response, String algorithmName, String modelName, String partType) throws IOException {
        // Write new data
        System.out.printf("Write new data: Algorithm name: %s, Model name: %s.%n", algorithmName, modelName);
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("response", response);
        map.put("modelName", modelName);
        // modelResponseSaveToDatabase
        DbModelResponse dbModelResponse = new DbModelResponse();
        dbModelResponse.setPartType(partType);
        switch (algorithmName) {
            case "RandomForestClassifier":
            case "LogisticRegression":
            case "SVCClassifier":
            case "MLPClassifier": {
                var model = (ClassificationResponse) response;
                dbModelResponse.setModelName(modelName);
                dbModelResponse.setAlgorithmType(algorithmName);
                dbModelResponse.setWeightedFalsePositiveRate(model.getWeightedFalsePositiveRate());
                dbModelResponse.setWeightedFMeasure(model.getWeightedFMeasure());
                dbModelResponse.setAccuracy(model.getAccuracy());
                dbModelResponse.setWeightedPrecision(model.getWeightedPrecision());
                dbModelResponse.setWeightedRecall(model.getWeightedRecall());
                dbModelResponse.setWeightedTruePositiveRate(model.getWeightedTruePositiveRate());
                dbModelResponse.setListFeatures(model.getListFeatures());
                break;
            }
            case "LinearRegression":
            case "LassoRegression": {
                var model = (RegressionResponse) response;
                dbModelResponse.setModelName(modelName);
                dbModelResponse.setAlgorithmType(algorithmName);
                dbModelResponse.setCoefficients(model.getCoefficients());
//                  dbModelResponse.setResiduals(model.getResiduals());
                dbModelResponse.setRootMeanSquaredError(model.getRootMeanSquaredError());
                dbModelResponse.setR2(model.getR2());
                dbModelResponse.setListFeatures(model.getListFeatures());
                break;
            }
            case "isolationForestOutlierDetection": {
                var model = (ClusterResponse) response;
                dbModelResponse.setModelName(modelName);
                dbModelResponse.setAlgorithmType(algorithmName);
                dbModelResponse.setListFeatures(model.getListFeatures());

                break;
            }

        }
        dbModelResponseRepository.save(dbModelResponse);

        String insertInfo = "DB INSERT DONE.";
        System.out.println(insertInfo);

        return insertInfo;
    }

    public void deleteOldMlResponse(String algorithmName, String modelName) {
        System.out.println(String.format("Delete old data: Algorithm name: %s, Model name: %s.", algorithmName, modelName));
        try {
            var searchRequest = new DeleteByQueryRequest(this.getAlgorithmESIndex(algorithmName));

            var query = QueryBuilders.boolQuery()
                    .filter(QueryBuilders.boolQuery().must(termQuery("modelName", modelName)));

            searchRequest.setQuery(query);

            // TODO: esConnector 삭제
            var response = this.esConnector.getClient().deleteByQuery(searchRequest, RequestOptions.DEFAULT);

            long deleted = response.getDeleted();

            System.out.println(String.format("Deleted _index: %s, modelName: %s, affected: %d ",
                    this.getAlgorithmESIndex(algorithmName),
                    modelName,
                    deleted));

        } catch (IndexNotFoundException | IOException e) {
            log.warn("Delete failed. Cause: " + e.getMessage());
            log.warn(String.format("The index %s not found.", this.getAlgorithmESIndex(algorithmName)));
        }
    }
}
