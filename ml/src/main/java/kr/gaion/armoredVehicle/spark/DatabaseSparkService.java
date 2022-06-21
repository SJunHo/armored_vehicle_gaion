package kr.gaion.armoredVehicle.spark;

import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.FileInput;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.database.DBIndexConfig;
import kr.gaion.armoredVehicle.elasticsearch.ESIndexConfig;
import kr.gaion.armoredVehicle.spark.dto.LabeledData;
import kr.gaion.armoredVehicle.spark.dto.NumericLabeledData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class DatabaseSparkService {
    @NonNull protected final SparkSession spark;
    @NonNull protected final Utilities utilities;
    @NonNull private final DBIndexConfig dbIndexConfig;

    public Dataset<Row> getUnlabeledDataFromDb() {
        var index = this.dbIndexConfig.getIndex();
        return this.getDataRDDFromDb();
    }

    public Dataset<Row> getUnlabeledDataFromDb(List<String> ids) {
        var index = this.dbIndexConfig.getIndex();
        return this.getDataRDDFromDb();
    }

//    public Dataset<Row> getDataRDDFromDb(String dbIndex) {
//        return this.getDataRDDFromDb();
//    }

    public Dataset<Row> getDataRDDFromDb() {
//		var jvSc = JavaSparkContext.fromSparkContext(this.spark.sparkContext());

        Dataset<Row> jdbcDF = spark.read()
                .format("jdbc")
                .option("url", "jdbc:mysql:192.168.2.81:3000/AMVHC")
                .option("dbtable", "schema.FILEINFO")
                .option("user", "AMVHC_U")
                .option("password", "!Tltmxpa0517")
                .load();
//        var dbReadingType = this.dbIndexConfig.getReadingType();
//        var url = this.utilities.getURL(dbIndex, dbReadingType);
//        var retVal = this.spark.sqlContext()
//                .read()
//                .format("db")
//                .options(option)
//                .load(url);
//        if (ids != null) {
//            retVal = retVal.where(retVal.col("_metadata._id").isin(ids.stream().toArray(String[]::new)));
//        }
//        retVal.cache();
        System.out.println(jdbcDF);
        return jdbcDF;
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

    public Dataset<Row> getLabeledDatasetFromElasticsearch(BaseAlgorithmTrainInput input) {
        log.info("Getting data from ElasticSearch for: " + this.dbIndexConfig.getIndex() + "/" + this.dbIndexConfig.getReadingType() + "/");

        var jvRddData = this.getDataRDDFromDb();

        var esData = processData(jvRddData, input.getFilterOutFields(), input.getFeatureCols(), input.getClassCol());
        return spark.createDataFrame(esData.rdd(), LabeledData.class);
    }

    public Dataset<NumericLabeledData> getNumericLabeledDatasetFromElasticsearch(BaseAlgorithmTrainInput input) {
        var classCol = input.getClassCol();
        var featureCols = input.getFeatureCols();

        log.info("Getting data from ElasticSearch for: " + this.dbIndexConfig.getIndex() + "/" + this.dbIndexConfig.getReadingType() + "/");

        var jvRddData = this.getDataRDDFromDb();

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
