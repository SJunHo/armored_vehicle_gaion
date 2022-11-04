package kr.gaion.armoredVehicle.spark;

import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.database.DatabaseConfiguration;
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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class DatabaseSparkService {
    @NonNull
    protected final SparkSession spark;
    @NonNull
    protected final Utilities utilities;
    @NonNull
    private final DatabaseConfiguration databaseConfiguration;

    private static Dataset<LabeledData> processData(
            Dataset<Row> jvRddData,
            List<String> filterOutFields,
            List<String> featureCols,
            String classCol) {
        List<String> filteredOutFields = new ArrayList<>();
        for (String field : featureCols) {
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

    private static Dataset<NumericLabeledData> processNumericLabeledDataset(
            Dataset<Row> jvRddData, String classCol, List<String> featureCols) {

        System.out.println("********** classCol = " + classCol);
        System.out.println("********** featureCols = " + featureCols);

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

    public String JDBCQuery(String query) {
        String DB_URL = databaseConfiguration.getUrl();
        String USER = databaseConfiguration.getUser();
        String PASS = databaseConfiguration.getPassword();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
        ) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public Dataset<Row> getLabeledDatasetFromDatabase(BaseAlgorithmTrainInput input) {
        var jvRddData = this.getDataRDDFromDb(input.getPartType(), input.getFileName());

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ jvRddData @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        jvRddData.show(false);

        var esData = processData(jvRddData, input.getFilterOutFields(), input.getFeatureCols(), input.getClassCol());
        return spark.createDataFrame(esData.rdd(), LabeledData.class);
    }

    public Dataset<Row> getDataRDDFromDb(String partType, String fileName) {
        String query = null;
        switch (partType) {
            case "BLB":
                // Bearing Left Ball
                query = String.format(" SELECT b.AI_LBSF, b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BSF, b.L_B_V_32924BSF, b.L_B_V_32922BSF, b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM BERTRNNG b, ENGTRNNG e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.FILENM = '%s' ", fileName);
                break;

            case "BLI":
                // Bearing Left Inside
                query = String.format(" SELECT b.AI_LBPFI, b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BPFI, b.L_B_V_32924BPFI, b.L_B_V_32922BPFI, b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM BERTRNNG b, ENGTRNNG e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.FILENM = '%s' ", fileName);
                break;

            case "BLO":
                // Bearing Left Outside
                query = String.format(" SELECT b.AI_LBPFO, b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BPFO, b.L_B_V_32924BPFO, b.L_B_V_32922BPFO, b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM BERTRNNG b, ENGTRNNG e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.FILENM = '%s' ", fileName);
                break;

            case "BLR":
                // Bearing Left Retainer
                query = String.format(" SELECT b.AI_LFTF, b.W_RPM, b.L_B_V_1X, b.L_B_V_6912FTF, b.L_B_V_32924FTF, b.L_B_V_32922FTF, b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM BERTRNNG b, ENGTRNNG e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.FILENM = '%s' ", fileName);
                break;

            case "BRB":
                // Bearing Right Ball
                query = String.format(" SELECT AI_RBSF, b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BSF, b.R_B_V_32924BSF, b.R_B_V_32922BSF, b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM BERTRNNG b, ENGTRNNG e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.FILENM = '%s' ", fileName);
                break;

            case "BRI":
                // Bearing Right Inside
                query = String.format(" SELECT b.AI_RBPFI, b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BPFI, b.R_B_V_32924BPFI, b.R_B_V_32922BPFI, b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM BERTRNNG b, ENGTRNNG e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.FILENM = '%s' ", fileName);
                break;

            case "BRO":
                // Bearing Right Outside
                query = String.format(" SELECT b.AI_RBPFO, b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BPFO, b.R_B_V_32924BPFO, b.R_B_V_32922BPFO, b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM BERTRNNG b, ENGTRNNG e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.FILENM = '%s' ", fileName);
                break;

            case "BRR":
                // Bearing Right Retainer
                query = String.format(" SELECT b.AI_RFTF, b.W_RPM, b.R_B_V_1X, b.R_B_V_6912FTF, b.R_B_V_32924FTF, b.R_B_V_32922FTF, b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM BERTRNNG b, ENGTRNNG e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.FILENM = '%s' ", fileName);
                break;

            case "WL":
                // Wheel Left
                query = String.format(" SELECT w.AI_LW, w.W_RPM, w.L_W_V_2X, w.L_W_V_3X, w.L_W_S_Fault3, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM WHLTRNNG w, ENGTRNNG e " +
                        " WHERE w.`DATE` = e.`DATE` AND w.FILENM = '%s' ", fileName);
                break;

            case "WR":
                // Wheel Right
                query = String.format(" SELECT w.AI_LW, w.W_RPM, w.R_W_V_2X, w.R_W_V_3X, w.R_W_S_Fault3, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM WHLTRNNG w, ENGTRNNG e " +
                        " WHERE w.`DATE` = e.`DATE` w.FILENM = '%s' ", fileName);
                break;
//
//            case "G":
//                // Gearbox
//                query = String.format();
//                break;
//
//            case "E":
//                // Engine
//                query = String.format();
//                break;
        }
        try {
            System.out.println("partType : " + partType + " / " + "fileName : " + fileName);
            Dataset<Row> jdbcDF = spark.read()
                    .format("jdbc")
                    .option("url", "jdbc:mysql://192.168.0.52:3306/AMVHC")
                    .option("user", "AMVHC_U")
                    .option("password", "!Tltmxpa0517")
                    .option("query", query)
                    .load();
            return jdbcDF;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Dataset<NumericLabeledData> getNumericLabeledDatasetFromDb(BaseAlgorithmTrainInput input) {
        var featureCols = input.getFeatureCols();
        var classCol = input.getClassCol();

        var jvRddData = this.getDataRDDFromDb("TEMPLIFE", "FILENM");

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ jvRddData @@@@@@@@@@@@@@@@@@@@@@@@@ ");
        jvRddData.show();

        return processNumericLabeledDataset(jvRddData, classCol, featureCols);
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

    //import Db unlabeled data for predict
    public Dataset<Row> getUnlabeledDataFromDb(BaseAlgorithmPredictInput baseAlgorithmPredictInput) {
        String tname = null;
        switch (baseAlgorithmPredictInput.getDataType()) {
            case "B": {
                tname = "BERDATA";
                break;
            }
            case "W": {
                tname = "WHLDATA";
                break;
            }
            case "G": {
                tname = "GRBDATA";
                break;
            }
            case "E": {
                tname = "ENGDATA";
                break;
            }
            case "T": {
                tname = "TEMPLIFEDATA";
                break;
            }
        }
        System.out.println("Select * from " + tname + " where AI_Predict is Null) as subtest");
        try {
            System.out.println(tname);
            Dataset<Row> jdbcDF = spark.read()
                    .format("jdbc")
                    .option("url", "jdbc:mysql://192.168.0.52:3306/AMVHC")
                    .option("dbtable", "(Select * from " + tname + " where AI_Predict is Null) as subtest")
                    .option("user", "AMVHC_U")
                    .option("password", "!Tltmxpa0517")
                    .load();
            return jdbcDF;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
