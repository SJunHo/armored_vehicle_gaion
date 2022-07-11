package kr.gaion.armoredVehicle.ml.service;

import com.google.gson.Gson;
import kr.gaion.armoredVehicle.algorithm.dto.response.AlgorithmResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClassificationResponse;
import kr.gaion.armoredVehicle.algorithm.dto.response.LinearRegressionTrainResponse;
import kr.gaion.armoredVehicle.common.HdfsHelperService;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.database.model.AlgorithmResponseDB;
import kr.gaion.armoredVehicle.database.model.DbModelResponse;
import kr.gaion.armoredVehicle.database.repository.AlgorithmResponseDBRepository;
import kr.gaion.armoredVehicle.database.repository.DBModelResponseRepository;
import kr.gaion.armoredVehicle.database.repository.FileInfoRepository;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
//import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.ml.dto.ModelResponse;
import kr.gaion.armoredVehicle.ml.dto.input.UpdateModelInput;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
@RequiredArgsConstructor
@Log4j
public class ModelService {
    // Service의 역할은 Dao가 DB에서 받아온 데이터를 전달받아 가공하는 것. 사용자의 요청(request)에 대해 어떤 처리를 할지 결정하는 파트입니다.
    // 즉, Controller가 받은 요청에 대해 알맞는 정보를 가공해서 다시 Controller에게 데이터를 넘기는 것을 의미합니다.
    // 주의할 점은 실제 비즈니스 로직은 Service에서 수행하지 않습니다. API에서 발생하는 트랙잭션과 Domain의 순서 보장의 역할을 갖고 있으며, 실제 로직은 Domain 패키지에서 수행됩니다.
    // /@Service: Service의 역할을 수행한다고 명시해주는 어노테이션.
  @NonNull private final EsConnector esConnector;
  @NonNull private final Utilities utilities;
  @NonNull private final StorageConfig storageConfig;
  @NonNull private final HdfsHelperService hdfsHelperService;
  @NonNull private final DBModelResponseRepository dbModelResponseRepository;

  private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//  public ModelResponse updateModel(String algorithmName, String esId, UpdateModelInput input) throws IOException {
//    var updateRequest = new UpdateRequest(this.getAlgorithmESIndex(algorithmName), esId);
//    var updateDoc = new HashMap<String, Object>();
//    updateDoc.put("description", input.getDescription());
//    updateDoc.put("checked", input.getChecked());
//    updateRequest.doc(updateDoc);
//    updateRequest.fetchSource(true);
//    var res = this.esConnector.getClient().update(updateRequest, RequestOptions.DEFAULT);
//    var source = res.getGetResult().getSource();
//    source.put("response", this.objectMapper.convertValue(source.get("response"), ClassificationResponse.class));
//    var ret = objectMapper.convertValue(res.getGetResult().getSource(), ModelResponse.class);
//    ret.setEsId(res.getGetResult().getId());
//    return ret;
//  }

//  public DbModelResponse updateModel(String algorithmName, String esId, UpdateModelInput input) throws IOException {
//
//  }

//  public boolean deleteModel(String algorithmName, String esId) throws Exception {
//    try {
//      var res = esConnector.select(this.getAlgorithmESIndex(algorithmName), 1, 0, QueryBuilders.idsQuery().addIds(esId));
//      String rootDir = this.utilities.getPathInWorkingFolder(this.storageConfig.getDataDir(), algorithmName, this.storageConfig.getModelDir());
//      String pathname = rootDir + File.separator + res.getHits().getHits()[0].getSourceAsMap().get("modelName");
//      this.hdfsHelperService.deleteIfExist(pathname);
//      esConnector.delete(this.getAlgorithmESIndex(algorithmName), QueryBuilders.idsQuery().addIds(esId));
//      return true;
//    } catch (IOException e) {
//      e.printStackTrace();
//			log.warn("Delete failed. Cause: " + e);
//			log.warn(String.format("The index %s not found.", algorithmName));
//      return false;
//    }
//  }
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

//	public List<ModelResponse> getModelResponse(String algorithm) {
//		var searchRequest = new SearchRequest(this.getAlgorithmESIndex(algorithm));
//		var srb = new SearchSourceBuilder();
//		srb.size(1000);
//		srb.from(0);
//        srb.query(QueryBuilders.matchAllQuery());
//        srb.fetchSource(new String[]{"modelName", "description", "checked", "response"}, new String[]{});
//		searchRequest.source(srb);
//    try {
//      var res = this.esConnector.getClient().search(searchRequest, RequestOptions.DEFAULT);
//      return Arrays.stream(res.getHits().getHits()).map(hit -> {
//        var m = new ModelResponse();
//        m.setModelName((String) hit.getSourceAsMap().get("modelName"));
//        m.setResponse(objectMapper.convertValue(hit.getSourceAsMap().get("response"), ClassificationResponse.class));
//        m.setDescription((String) hit.getSourceAsMap().get("description"));
//        m.setChecked((Boolean) hit.getSourceAsMap().get("checked"));
//        m.setEsId(hit.getId());
//          System.out.println(m);
//        return m;
//      }).collect(Collectors.toList());
//    } catch (IOException e) {
//      e.printStackTrace();
//      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//    }
//	}

    public List<DbModelResponse> getModelResponse(String algorithm){
        return dbModelResponseRepository.getModelResponseListByalgorithm(algorithm);
    }



	public String insertNewMlResponse(AlgorithmResponse response, String algorithmName, String modelName) throws IOException {
		// Delete old data
		deleteOldMlResponse(algorithmName, modelName);

		// Write new data
		log.info(String.format("Write new data: Algorithm name: %s, Model name: %s.", algorithmName, modelName));
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<>();
		map.put("response", response);
		map.put("modelName", modelName);
        // modelResponseSaveToDatabase
        switch (algorithmName) {
            case "RandomForestClassifier":
            {
                var model= (ClassificationResponse)response;
                DbModelResponse dbModelResponse = new DbModelResponse();
                dbModelResponse.setModelName(modelName);
                dbModelResponse.setType(algorithmName);
                dbModelResponse.setWeightedFalsePositiveRate(model.getWeightedFalsePositiveRate());
                dbModelResponse.setWeightedFMeasure(model.getWeightedFMeasure());
                dbModelResponse.setAccuracy(model.getAccuracy());
                dbModelResponse.setWeightedPrecision(model.getWeightedPrecision());
                dbModelResponse.setWeightedRecall(model.getWeightedRecall());
                dbModelResponse.setWeightedTruePositiveRate(model.getWeightedTruePositiveRate());
                dbModelResponseRepository.save(dbModelResponse);
            }
            case "LinearRegression":
            {
  //                model = (ClassificationResponse)response;
              var model= (LinearRegressionTrainResponse) response;

              DbModelResponse dbModelResponse = new DbModelResponse();

              dbModelResponse.setModelName(modelName);
              dbModelResponse.setType(algorithmName);
              dbModelResponse.setCoefficients(model.getCoefficients());
              dbModelResponse.setResiduals(model.getResiduals());
              dbModelResponse.setRootMeanSquaredError(model.getRootMeanSquaredError());
              dbModelResponse.setR2(model.getR2());

              dbModelResponseRepository.save(dbModelResponse);
            }
        }
//		String insertInfo = this.esConnector.insert(gson.toJson(map), this.getAlgorithmESIndex(algorithmName));
        String insertInfo = "true";
		log.info(insertInfo);

		return insertInfo;
	}

	public void deleteOldMlResponse(String algorithmName, String modelName) {
		log.info(String.format("Delete old data: Algorithm name: %s, Model name: %s.", algorithmName, modelName));
		try {
			var searchRequest = new DeleteByQueryRequest(this.getAlgorithmESIndex(algorithmName));
			var query = QueryBuilders.boolQuery()
					.filter(QueryBuilders.boolQuery().must(termQuery("modelName", modelName)));
			searchRequest.setQuery(query);
			var response = this.esConnector.getClient().deleteByQuery(searchRequest, RequestOptions.DEFAULT);
			long deleted = response.getDeleted();
			log.info(String.format("Deleted _index: %s, modelName: %s, affected: %d ",
					this.getAlgorithmESIndex(algorithmName),
					modelName,
					deleted));
		} catch (IndexNotFoundException | IOException e) {
			log.warn("Delete failed. Cause: " + e.getMessage());
			log.warn(String.format("The index %s not found.", this.getAlgorithmESIndex(algorithmName)));
		}
	}
}
