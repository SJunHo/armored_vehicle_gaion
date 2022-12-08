package kr.gaion.armoredVehicle.dataset.service;

import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.dataset.dto.AllTimeESStats;
import kr.gaion.armoredVehicle.dataset.dto.ESDataUpdateInput;
import kr.gaion.armoredVehicle.dataset.dto.ImportESDataFromFileInput;
import kr.gaion.armoredVehicle.elasticsearch.ESIndexConfig;
//import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.elasticsearch.EsConnector;
import kr.gaion.armoredVehicle.ml.dto.RailSensorData;
import kr.gaion.armoredVehicle.spark.ElasticsearchSparkService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.index.query.IdsQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
//import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
//import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
//import org.elasticsearch.search.aggregations.bucket.range.Range;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortBuilder;
//import org.elasticsearch.search.sort.SortBuilders;
//import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j
public class DatasetService {
    @NonNull
    private final StorageService storageService;
    //    @NonNull
//    private final EsConnector esConnector;
    @NonNull
    private final ESIndexConfig esIndexConfig;
    @NonNull
    private final Utilities utilities;
    @NonNull
    private final ElasticsearchSparkService elasticsearchSparkService;
    private final SimpleDateFormat esDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//  public List<String> getTrainsList() throws IOException {
//    var client = this.esConnector.getClient();
//    var searchRequest = new SearchRequest(this.esIndexConfig.getIndex());
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//		var aggregation = AggregationBuilders.terms("byTrain")
//        .field("Train No.keyword");
//		searchSourceBuilder.aggregation(aggregation);
//		searchRequest.source(searchSourceBuilder);
//		var res = client.search(searchRequest, RequestOptions.DEFAULT);
//		Terms terms = res.getAggregations().get("byTrain");
//		return terms.getBuckets().stream().map(bucket -> bucket.getKey().toString()).collect(Collectors.toList());
//  }
//
//  public List<String> getCarsList(String trainNumber) throws IOException {
//    var client = this.esConnector.getClient();
//    var searchRequest = new SearchRequest(this.esIndexConfig.getIndex());
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(trainNumber == null
//				? QueryBuilders.matchAllQuery()
//				: QueryBuilders.matchQuery("Train No.keyword", trainNumber));
//		var aggregation = AggregationBuilders.terms("byCar")
//        .field("Car No.keyword");
//		searchSourceBuilder.aggregation(aggregation);
//		searchRequest.source(searchSourceBuilder);
//		var res = client.search(searchRequest, RequestOptions.DEFAULT);
//		Terms terms = res.getAggregations().get("byCar");
//		return terms.getBuckets().stream().map(bucket -> bucket.getKey().toString()).collect(Collectors.toList());
//  }
//
//	public Page<RailSensorData> getConditionData(
//      String wOrB, String trainNumber, String carNumber, Date fromDate, Date toDate, Integer severity, Pageable pageable, Boolean hasDefectScore, Integer hasDefectUser) throws IOException {
//		var query = QueryBuilders.boolQuery();
//		query.must().add(QueryBuilders.matchQuery("W/B.keyword", wOrB));
//		if (trainNumber != null) {
//			query.must().add(QueryBuilders.matchQuery("Train No.keyword", trainNumber));
//		}
//		if (carNumber != null) {
//			query.must().add(QueryBuilders.matchQuery("Car No.keyword", carNumber));
//		}
//		if (fromDate != null && toDate != null) {
//			query.must().add(QueryBuilders.rangeQuery("Measurement Time")
//          .gte(this.esDateFormat.format(fromDate))
//          .lte(this.esDateFormat.format(toDate)));
//		}
//		if (severity != null) {
//			query.must().add(QueryBuilders.rangeQuery("severity").gte(severity));
//		}
//        if (hasDefectScore) {
//            // defectScore is must above 0.
//            query.mustNot().add(QueryBuilders.matchQuery("defect_score", 0));
//        }
//        if (hasDefectUser == 0) {
//            // state 0 mean select defectUser is must 0 data.
//            query.must().add(QueryBuilders.matchQuery("defect_user", 0));
//        } else if (hasDefectUser == 1) {
//            // state 1 mean select defectUser is must above 0 data.
//            query.mustNot().add(QueryBuilders.matchQuery("defect_user", 0));
//        } else {
//            // state 2 mean select all defectUser data.
//            query.must().add(QueryBuilders.matchAllQuery());
//        }
//        return this.selectByPageable(this.esIndexConfig.getIndex(), pageable, query, "Measurement Time");
//	}
//
//  public RailSensorData[] updateRailSensorData(List<ESDataUpdateInput> inputs) throws IOException {
//    BulkRequest request = new BulkRequest(this.esIndexConfig.getIndex());
//    var updates = inputs.stream().map(input -> {
//      var updateRequest = new UpdateRequest(this.esIndexConfig.getIndex(), input.getEsId());
//      var updateDoc = new HashMap<String, Object>();
//      if (input.getGDefectProb() != null) {
//          updateDoc.put("defect_score", input.getGDefectProb());
//      }
//      if (input.getUDefectProb() != null) {
//          updateDoc.put("defect_user", input.getUDefectProb());
//      }
//      updateRequest.doc(updateDoc);
//      updateRequest.fetchSource(true);
//      return updateRequest;
//    }).toArray(UpdateRequest[]::new);
//    request.add(updates);
//    this.esConnector.getClient().bulk(request, RequestOptions.DEFAULT);
//    var res = this.selectByPageable(
//        this.esIndexConfig.getIndex(),
//        Pageable.ofSize(inputs.size()),
//        new IdsQueryBuilder().addIds(inputs.stream().map(ESDataUpdateInput::getEsId).toArray(String[]::new)),
//        "Measurement Time");
//    return res.getContent().toArray(new RailSensorData[0]);
//  }
//
//	public Page<RailSensorData> getPredictingData(Pageable pageable) throws IOException {
//		return this.getPredictingData(this.esIndexConfig.getIndex(), pageable);
//	}
//
//  public String handleUploadFile(MultipartFile file) {
//    this.storageService.store(file);
//    return file.getOriginalFilename();
//  }
//
//  public void handleImportESIndexFromFile(ImportESDataFromFileInput input) throws IOException {
//      System.out.println("Index to es-search: " + input.getIndexW());
//
//    if (input.getListUploadedFiles().length == 0) {
//      throw new Error("There was not uploaded data, please upload data first.");
//    }
//
//      System.out.println("Index request is handling ..");
//    // Start watch
//    long startTime = System.currentTimeMillis();
//
//    // Get settings
//    String _index = input.getIndexW();
//    String _type = "doc";
//    String delimiter = input.getDelimiter();
//    String url = this.utilities.getURL(_index, _type);
//    String _idColumn = input.getColumnPrimaryKey();
//
//    // Delete old data
//    if (input.isClearOldData()) {
//        System.out.println(String.format("delete old data from _index: %s, type: %s", _index, _type));
//      this.esConnector.delete(_index, QueryBuilders.matchAllQuery());
//    }
//
//    // Start index
//      System.out.println("index to Elasticsearch with data format is CSV");
//    for (String file : input.getListUploadedFiles()) {
//        System.out.println("call Spark connector:");
//      var absoluteFilePath = this.storageService.load(file);
//      this.elasticsearchSparkService.indexDataFromFileToElasticsearch(absoluteFilePath.toString(), delimiter, url, _idColumn);
//    }
//      System.out.println("elapsed time: estimatedTime " + (System.currentTimeMillis() - startTime) + " (ms)");
//  }
//
//  public Page<RailSensorData> selectByPageable(String index, Pageable pageable, QueryBuilder queryBuilder, String sortByField) throws IOException {
//    var sort = new ArrayList<SortBuilder<?>>();
//    if (sortByField != null) {
//      sort.add(SortBuilders.fieldSort(sortByField).order(SortOrder.DESC));
//    }
//		var response = this.esConnector.select(index, pageable.getPageSize(), (int) pageable.getOffset(), queryBuilder, sort);
//
//		return new PageImpl<>(Arrays.stream(response.getHits().getHits()).map(hit -> {
//			try {
//				var res = this.objectMapper.readValue(
//						hit.getSourceAsString(), RailSensorData.class);
//				res.setEsId(hit.getId());
//				return res;
//			} catch (IOException e) {
//				e.printStackTrace();
//				return null;
//			}
//		}).collect(Collectors.toList()), pageable, response.getHits().getTotalHits().value);
//	}
//
//	public Page<RailSensorData> getPredictingData(String index, Pageable pageable) throws IOException {
//		var query = QueryBuilders.boolQuery().filter(QueryBuilders.rangeQuery("defect_prob_0").gt(0));
//		return this.selectByPageable(index, pageable, query, "Measurement Time");
//	}
//
//  public List<Long> countRecordsDailyInLast30Days() throws IOException {
//    var searchRequest = new SearchRequest(this.esIndexConfig.getIndex());
//    var query = QueryBuilders
//        .boolQuery()
//        .filter(QueryBuilders.rangeQuery("Measurement Time")
//            .gte("now-5M/d")
//            .lte("now-4M/d"));
//    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(query);
//		var aggregation = AggregationBuilders.dateHistogram("byDate")
//        .field("Measurement Time").calendarInterval(DateHistogramInterval.DAY);
//		searchSourceBuilder.aggregation(aggregation);
//		searchRequest.source(searchSourceBuilder);
//    var res = this.esConnector.getClient().search(searchRequest, RequestOptions.DEFAULT);
//    var agg = (Histogram) res.getAggregations().get("byDate");
//    return agg.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getDocCount).collect(Collectors.toList());
//  }
//
//  public List<Long> countRecordsMonthlyLast6Months() throws IOException {
//    var searchRequest = new SearchRequest(this.esIndexConfig.getIndex());
//    var query = QueryBuilders
//        .boolQuery()
//        .filter(QueryBuilders.rangeQuery("Measurement Time")
//            .gte("now-11M/M")
//            .lte("now"));
//    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(query);
//		var aggregation = AggregationBuilders.dateHistogram("byMonth")
//        .field("Measurement Time").calendarInterval(DateHistogramInterval.MONTH);
//		searchSourceBuilder.aggregation(aggregation);
//		searchRequest.source(searchSourceBuilder);
//    var res = this.esConnector.getClient().search(searchRequest, RequestOptions.DEFAULT);
//    var agg = (Histogram) res.getAggregations().get("byMonth");
//    return agg.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getDocCount).collect(Collectors.toList());
//  }
//
//  public AllTimeESStats getAllTimeStats() throws IOException {
//    var searchRequest = new SearchRequest(this.esIndexConfig.getIndex());
//    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//		searchSourceBuilder.aggregation(
//        AggregationBuilders.terms("byType").field("type.keyword")
//            .subAggregation(AggregationBuilders.terms("defectScore").field("defect_score"))
//
//        ).aggregations()
//        .addAggregator(AggregationBuilders.terms("byTrainNo").field("Train No.keyword")
//            .subAggregation(AggregationBuilders.terms("defectScore").field("defect_score")))
//        .addAggregator(AggregationBuilders.terms("byCarNo").field("Car No.keyword")
//            .subAggregation(AggregationBuilders.terms("defectScore").field("defect_score")))
//        .addAggregator(AggregationBuilders.range("byWeighting").field("Weighting State equation")
//            .addUnboundedTo("1", 140)
//            .addRange("2", 140, 180)
//            .addRange("3", 180, 200)
//            .addRange("4", 200, 220)
//            .addUnboundedTo("5", 220)
//            .subAggregation(AggregationBuilders.terms("wb").field("W/B.keyword")))
//        .addAggregator(AggregationBuilders.terms("defectScore").field("defect_score")
//            .subAggregation(AggregationBuilders.terms("defectUser").field("defect_user.keyword")))
//        .addAggregator(AggregationBuilders.terms("defectUser").field("defect_user.keyword"));
//		searchRequest.source(searchSourceBuilder);
//      System.out.println(searchRequest.toString());
//    var res = this.esConnector.getClient().search(searchRequest, RequestOptions.DEFAULT);
//    var ret = new AllTimeESStats();
//
//    var byTypeTerms = (Terms) res.getAggregations().get("byType");
//    var byTypeMap = byTypeTerms.getBuckets().stream().collect(Collectors.toMap(bucket -> bucket.getKey().toString(),
//        bucket -> {
//          var dTerms = (Terms) bucket.getAggregations().get("defectScore");
//          return dTerms.getBuckets().stream().collect(
//                  Collectors.toMap(bucket1 -> Integer.valueOf(bucket1.getKey().toString()),
//                  MultiBucketsAggregation.Bucket::getDocCount));
//    }));
//
//    var byTrainNo = (Terms) res.getAggregations().get("byTrainNo");
//    var byTrainNoMap = byTrainNo.getBuckets().stream().collect(Collectors.toMap(bucket -> bucket.getKey().toString(),
//        bucket -> {
//          var dTerms = (Terms) bucket.getAggregations().get("defectScore");
//          return dTerms.getBuckets().stream().collect(
//                  Collectors.toMap(bucket1 -> Integer.valueOf(bucket1.getKey().toString()),
//                  MultiBucketsAggregation.Bucket::getDocCount));
//    }));
//
//    var byCarNo = (Terms) res.getAggregations().get("byCarNo");
//    var byCarNoMap = byCarNo.getBuckets().stream().collect(Collectors.toMap(bucket -> bucket.getKey().toString(),
//        bucket -> {
//          var dTerms = (Terms) bucket.getAggregations().get("defectScore");
//          return dTerms.getBuckets().stream().collect(
//                  Collectors.toMap(bucket1 -> Integer.valueOf(bucket1.getKey().toString()),
//                  MultiBucketsAggregation.Bucket::getDocCount));
//    }));
//
//    var byWeighting = (Range) res.getAggregations().get("byWeighting");
//
//    var byDScore = (Terms) res.getAggregations().get("defectScore");
//    var byDUser = (Terms) res.getAggregations().get("defectUser");
//
//    ret.setByType(byTypeMap);
//    ret.setByTrainNo(byTrainNoMap);
//    ret.setByCarNo(byCarNoMap);
//    ret.setWeightingB(byWeighting.getBuckets().stream().map(bucket -> {
//      var wb = (Terms) bucket.getAggregations().get("wb");
//      if (wb.getBuckets().size() > 0) {
//        return wb.getBuckets().get(wb.getBuckets().get(0).getKey().toString().equals("B") ? 0 : 1).getDocCount();
//      }
//      return 0L;
//    }).collect(Collectors.toList()));
//    ret.setWeightingW(byWeighting.getBuckets().stream().map(bucket -> {
//      var wb = (Terms) bucket.getAggregations().get("wb");
//      if (wb.getBuckets().size() > 0) {
//        return wb.getBuckets().get(wb.getBuckets().get(0).getKey().toString().equals("W") ? 0 : 1).getDocCount();
//      }
//      return 0L;
//    }).collect(Collectors.toList()));
//    ret.setDefectScoreGt0(byDScore.getBuckets()
//        .stream().filter(bucket -> !bucket.getKey().toString().equals("0"))
//        .map(MultiBucketsAggregation.Bucket::getDocCount).mapToLong(Long::longValue).sum()
//    );
//    ret.setDefectUserGt0(byDUser.getBuckets()
//        .stream().filter(bucket -> !bucket.getKey().toString().equals("0"))
//        .map(MultiBucketsAggregation.Bucket::getDocCount).mapToLong(Long::longValue).sum()
//    );
//    ret.setDefectMatrix(byDScore.getBuckets().stream().collect(Collectors.toMap(
//        bucket -> bucket.getKey().toString(),
//        bucket -> ((Terms) bucket.getAggregations().get("defectUser")).getBuckets().stream().collect(Collectors.toMap(
//            subBucket -> subBucket.getKey().toString(),
//            MultiBucketsAggregation.Bucket::getDocCount
//        ))
//    )));
//    return ret;
//  }
}
