package kr.gaion.armoredVehicle.spark;

import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.FileInput;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.database.DatabaseConfiguration;
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
import org.elasticsearch.spark.sql.api.java.JavaEsSparkSQL;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Log4j
public class DatabaseSparkService {
    @NonNull protected final SparkSession spark;
    @NonNull protected final Utilities utilities;
    @NonNull private final DatabaseConfiguration databaseConfiguration;

    public String JDBCQuery(String query) {
        String DB_URL = databaseConfiguration.getUrl();
        String USER = databaseConfiguration.getUser();
        String PASS = databaseConfiguration.getPassword();

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ){
            System.out.println(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "success";
    }

    public Dataset<Row> getLabeledDatasetFromDatabase(BaseAlgorithmTrainInput input) {
//        log.info("Getting data from ElasticSearch for: " + this.dbIndexConfig.getIndex() + "/" + this.dbIndexConfig.getReadingType() + "/");

        var jvRddData = this.getDataRDDFromDb("BERTRNING");

        var esData = processData(jvRddData, input.getFilterOutFields(), input.getFeatureCols(), input.getClassCol());
        return spark.createDataFrame(esData.rdd(), LabeledData.class);
    }

    public Dataset<Row> getDataRDDFromDb(String tableName){
        try{
            String tname = tableName;
            System.out.println(tname);
            Dataset<Row> jdbcDF = spark.read()
                    .format("jdbc")
                    .option("url", "jdbc:mysql://192.168.0.52:3306/AMVHC")
                    .option("dbtable", tname.toUpperCase())
                    .option("user", "AMVHC_U")
                    .option("password", "!Tltmxpa0517")
                    .load();
            return jdbcDF;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        var featureCols = input.getFeatureCols();
        var classCol = input.getClassCol();

        var jvRddData = this.getDataRDDFromDb("TEMPLIFE");
        System.out.println("jvRddData");

        jvRddData.show();

        return processNumericLabeledDataset(jvRddData, classCol, featureCols);
    }

    private static Dataset<NumericLabeledData> processNumericLabeledDataset(
            Dataset<Row> jvRddData, String classCol, List<String> featureCols) {

        System.out.println(classCol);
        System.out.println(featureCols);

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

    public Dataset<Row> getDatasetFromDatabase() {
        Dataset<Row> jdbcDF = spark.read()
                .format("jdbc")
                .option("url", "jdbc:mysql://localhost:3306/AMVHC")
                .option("dbtable", "BERTRNING")
                .option("user", "root")
                .option("password", "gaion")
                .load();
        return jdbcDF;
    }


}
