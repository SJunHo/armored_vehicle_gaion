package kr.gaion.armoredVehicle.elasticsearch;

import lombok.extern.log4j.Log4j;
import org.apache.http.HttpHost;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.GetMappingsRequest;
//import org.elasticsearch.cluster.metadata.MappingMetadata;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.reindex.DeleteByQueryRequest;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortBuilder;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

@Log4j
public class EsConnector {
//	private final RestHighLevelClient client;
//	private final ObjectMapper objectMapper = new ObjectMapper()
//			.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//	/**
//	 * default mapping type for saving model information to Elasticsearch (Item: #PC0022)<br>
//	 */
//	public static final String DEFAULT_MODEL_MAPPING_TYPE = "model";
//
//	/**
//	 * Constructor, to initialize the connection to ElasticeSearch
//	 */
//	@SuppressWarnings("resource")
//	EsConnector(String host, int port) throws UnknownHostException {
//		client = new RestHighLevelClient(
//				RestClient.builder(
//						new HttpHost(host, port)
//				)
//		);
//	}
//
//	/**
//	 * to terminate current connection
//	 */
//	public void closeTransaction() throws IOException {
//		if (this.client != null) {
//			client.close();
//		}
//	}
//
//	/**
//	 * to get data from ElasticSearch by specified queries
//	 */
//	public SearchResponse select(String index, Integer count, Integer from, QueryBuilder qb, List<SortBuilder<?>> sorts) throws IOException {
//		var searchRequest = new SearchRequest(index);
//		var srb = new SearchSourceBuilder();
//		srb.size(count);
//		srb.from(from);
//
//		if (qb != null) {
//			srb.query(qb);
//		}
//		searchRequest.source(srb);
//		return client.search(searchRequest, RequestOptions.DEFAULT);
//	}
//
//	/**
//	 * to get data from ElasticSearch by specified queries
//	 */
//	public SearchResponse select(String index, Integer count, Integer from, QueryBuilder qb) throws IOException {
//		return this.select(index, count, from, qb, null);
//	}
//
//	public SearchResponse select(String index, Integer count, Integer from) throws IOException {
//		return this.select(index, count, from, null);
//	}
//
//	/**
//	 * To delete data from ElasticSearch by specified queries
//	 */
//	public void delete(String index, QueryBuilder qb) throws IOException {
//		var response = this.client.deleteByQuery(new DeleteByQueryRequest(index.toLowerCase()).setQuery(
//				qb
//		), RequestOptions.DEFAULT);
//
//		long deleted = response.getDeleted();
//		System.out.println(String.format("Deleted _index: %s, _type: %s", index, deleted));
//	}
//
//	/**
//	 * to index JSON data to Elasticsearch
//	 *
//	 * @param jsonData
//	 * @param _index
//	 * @return
//	 */
//	public String insert(String jsonData, String _index) throws IOException {
//		var request = new IndexRequest(_index);
//		request.source(jsonData, XContentType.JSON);
//		IndexResponse response = this.client.index(request, RequestOptions.DEFAULT);
//		return response.toString();
//	}
//
//	public MappingMetadata getEsIndexMappings(String index) throws IOException {
//	  var request = new GetMappingsRequest();
//	  request.indices(index);
//	  var response = this.client.indices().getMapping(request, RequestOptions.DEFAULT);
//	  return response.mappings().get(index);
//	}
//
//	public RestHighLevelClient getClient() {
//		return this.client;
//	}
}