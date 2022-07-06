package kr.gaion.armoredVehicle.spark;

import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.FileInput;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.elasticsearch.ESIndexConfig;
import kr.gaion.armoredVehicle.spark.dto.LabeledData;
import kr.gaion.armoredVehicle.spark.dto.NumericLabeledData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.*;
import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j
public class ElasticsearchSparkService implements Serializable {
	@NonNull private final SparkSession spark;
	@NonNull private final ESIndexConfig esIndexConfig;
	@NonNull private final Utilities utilities;
	public void indexDataFromFileToElasticsearch(String filePath, String delimiter, String url, String ... _idColumn) {
		// load data from file to DataFrame
		Dataset<Row> originalData = this.spark.read().format("com.databricks.spark.csv").option("header", "true")
				.option("nullValue", "0").option("sep", delimiter).load(filePath);

		// index to Elasticsearch
		if (_idColumn != null && _idColumn.length > 0 && _idColumn[0].length() > 0) {
			Map<String, String> indexSettings = new HashMap<>();
			indexSettings.put("es.mapping.id", _idColumn[0]);
			JavaEsSparkSQL.saveToEs(originalData, url, indexSettings);
		} else {
			JavaEsSparkSQL.saveToEs(originalData, url);
		}
	}

	public Dataset<Row> getDatasetFromElasticsearch() {
		String _index = this.esIndexConfig.getIndex();
		String _type = this.esIndexConfig.getReadingType();
		String url = this.utilities.getURL(_index, _type);
		// return value
		var retVal = JavaEsSparkSQL.esDF(this.spark.sqlContext(), url);
		retVal.cache();

		return retVal;
	}

	public Dataset<Row> getUnlabeledDataFromEs() {
		var index = this.esIndexConfig.getIndex();
		return this.getDataRDDFromEs(index, null);
	}

	public Dataset<Row> getUnlabeledDataFromEs(List<String> ids) {
		var index = this.esIndexConfig.getIndex();
		return this.getDataRDDFromEs(index, ids);
	}

	public Dataset<Row> getDataRDDFromEs(String esIndex) {
		return this.getDataRDDFromEs(esIndex, null);
	}

	public Dataset<Row> getDataRDDFromEs(String esIndex, List<String> ids) {
//		var jvSc = JavaSparkContext.fromSparkContext(this.spark.sparkContext());

		var option = new HashMap<String, String>();
		option.put("es.nodes", this.esIndexConfig.getHost());
		option.put("es.port", Integer.toString(this.esIndexConfig.getTransportPort()));
		option.put("es.read.field.as.array.include", "tags");
		option.put("es.read.metadata", "true");
		option.put("es.mapping.date.rich", "false");
		var esReadingType = this.esIndexConfig.getReadingType();
		var url = this.utilities.getURL(esIndex, esReadingType);
		var retVal = this.spark.sqlContext()
				.read()
				.format("es")
				.options(option)
				.load(url);
		if (ids != null) {
			retVal = retVal.where(retVal.col("_metadata._id").isin(ids.stream().toArray(String[]::new)));
		}
		retVal.cache();
		return retVal;
	}

	public Dataset<Row> getLabeledDatasetFromElasticsearch(BaseAlgorithmTrainInput input) {
		log.info("Getting data from ElasticSearch for: " + this.esIndexConfig.getIndex() + "/" + this.esIndexConfig.getReadingType() + "/");

		var jvRddData = this.getDataRDDFromEs(input.getTrainingESIndex());

		var esData = processData(jvRddData, input.getFilterOutFields(), input.getFeatureCols(), input.getClassCol());
		return spark.createDataFrame(esData.rdd(), LabeledData.class);
	}

	public Dataset<Row> getSelectedFeaturesFromElasticsearch(BaseAlgorithmTrainInput config) {
		String _index = config.getTrainingESIndex();
		final String idCol = config.getClassCol();
		String url = this.utilities.getURL(_index, "_doc");

		String[] listColStr = config.getFeatureCols().toArray(new String[0]);
//		String[] listColStr = config.getSetting(AlgorithmSettings.LIST_FEATURES_COL);

		Column[] listCol = (idCol != null || !idCol.isEmpty()) ? new Column[listColStr.length + 1] : new Column[listColStr.length];
//		Column[] listCol = new Column[listColStr.length];
		if (idCol.isEmpty()) {
			for (int i = 0; i < listColStr.length; i++) {
				listCol[i] = new Column(listColStr[i]);
			}
		} else {
			listCol[0] = new Column(idCol);
			for (int i = 0; i < listColStr.length; i++) {
				listCol[i+1] = new Column(listColStr[i]);
			}
		}
		// return value
		Dataset<Row> retVal = JavaEsSparkSQL.esDF(spark.sqlContext(), url)
				.select(listCol);

		retVal.cache();

		return retVal;
	}

	private static Dataset<LabeledData> processData(
			Dataset<Row> jvRddData,
			List<String> filterOutFields,
			List<String> featureCols,
			String classCol) {
		List<String> filteredOutFields = new ArrayList<>();
		for (String field: featureCols) {
			if (filterOutFields == null || !filterOutFields.contains(field)) {
				filteredOutFields.add(field);
			}
		}
		// get data from ElasticSearch

		return jvRddData.map(new MapFunction<>() {
			private static final long serialVersionUID = 3091059931575058849L;

			public LabeledData call(Row mapData) {
				String label;
				try {
					label = mapData.getAs(classCol).toString();                                    // #PC0026
				} catch (Exception e) {
					label = "0";
				}
				double[] vector = new double[filteredOutFields.size()];
				int index = -1;
				String strVal;
				for (String feature : filteredOutFields) {
					++index;
					try {
						strVal = mapData.getAs(feature).toString();
						vector[index] = Double.parseDouble(strVal);
					} catch (Exception e) {
						vector[index] = 0.0;
					}
				}

				LabeledData dataReturn = new LabeledData();
				dataReturn.setLabel(label);
				dataReturn.setFeatures(Vectors.dense(vector));

				return dataReturn;
			}
		}, Encoders.javaSerialization(LabeledData.class));
	}

	public Dataset<NumericLabeledData> getNumericLabeledDatasetFromDb(BaseAlgorithmTrainInput input) {
		var classCol = input.getClassCol();
		var featureCols = input.getFeatureCols();

		log.info("Getting data from ElasticSearch for: " + this.esIndexConfig.getIndex() + "/" + this.esIndexConfig.getReadingType() + "/");

		var jvRddData = this.getDataRDDFromEs(input.getTrainingESIndex());

		// get data from ElasticSearch
		return processNumericLabeledDataset(jvRddData, classCol, featureCols);
	}

	private static Dataset<NumericLabeledData> processNumericLabeledDataset(
			Dataset<Row> jvRddData, String classCol, List<String> featureCols) {
		return jvRddData.map(new MapFunction<>() {
			private static final long serialVersionUID = -1318784596736889400L;

			public NumericLabeledData call(Row mapData) {
				double label;
				try {
					label = Double.parseDouble(mapData.getAs(classCol).toString());                                    // #PC0026
				} catch (Exception e) {
					label = 0.0;
				}
				double[] vector = new double[featureCols.size()];
				int index = -1;
				String strVal;
				for (String feature : featureCols) {
					++index;
					try {
						strVal = mapData.getAs(feature).toString();
						vector[index] = Double.parseDouble(strVal);
					} catch (Exception e) {
						vector[index] = 0.0;
					}
					// #PC0025 - End
				}

				NumericLabeledData dataReturn = new NumericLabeledData();
				dataReturn.setLabel(label);
				dataReturn.setFeatures(Vectors.dense(vector));

				return dataReturn;
			}
		}, Encoders.javaSerialization(NumericLabeledData.class));
	}


	public Dataset<Row> getDfVectorFromCsvFormattedFile(FileInput config) {
		String delimiter = config.getSeparator();

		// Load data from CSV file

		return this.spark.read()
			.format("com.databricks.spark.csv")
			.option("header", "true")
			// .option("nullValue", "0.0")
			// .option("sep", delimiter)
			.option("delimiter", delimiter)		// for format("com.databricks.spark.csv")
			.option("quote", "\"")
			.option("encoding", "UTF-8")
			.option("mode", "DROPMALFORMED")	// ignores the whole corrupted records (*1)
			.load(config.getFileName());
	}
}