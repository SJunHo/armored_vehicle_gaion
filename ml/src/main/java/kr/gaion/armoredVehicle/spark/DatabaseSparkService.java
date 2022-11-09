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
                query = String.format(" SELECT b.AI_RBSF, b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BSF, b.R_B_V_32924BSF, b.R_B_V_32922BSF, b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
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

            case "G":
                // Gearbox
                query = String.format(" SELECT g.AI_GEAR, g.W_RPM, g.G_V_OverallRMS, g.G_V_Wheel1X, g.G_V_Wheel2X, g.G_V_Pinion1X, g.G_V_Pinion2X, g.G_V_GMF1X, g.G_V_GMF2X, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM GRBTRNNG g, ENGTRNNG e " +
                        " WHERE g.`DATE` = e.`DATE` AND g.FILENM = '%s' ", fileName);
                break;

            case "E":
                // Engine
                query = String.format(" SELECT e.AI_ENGINE, e.W_RPM, e.E_V_OverallRMS, e.E_V_1_2X, e.E_V_1X, e.E_V_Crestfactor, e.AC_h, e.AC_v, e.AC_a " +
                        " FROM ENGTRNNG e " +
                        " WHERE e.FILENM = '%s' ", fileName);
                break;
        }
        try {
            System.out.println("Get Training Data -> " + " partType : " + partType + " / " + "fileName : " + fileName);
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
        String partType = baseAlgorithmPredictInput.getDataType();
        String query = null;
        switch (partType) {
            case "BLB":
                // Bearing Left Ball
                query = " SELECT b.AI_LBSF, b.AI_LBSF_ALGO, b.AI_LBSF_MODEL, b.AI_LBSF_DATE, " +
                        " b.USER_LBSF, b.USER_LBSF_ID, b.USER_LBSF_DATE, " +
                        " b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BSF, b.L_B_V_32924BSF, b.L_B_V_32922BSF, " +
                        " b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, " +
                        " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
                        " FROM BERDATA b, ENGDATA e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.AI_LBSF is Null ";
                break;

            case "BLI":
                // Bearing Left Inside
                query = " SELECT b.AI_LBPFI, b.AI_LBPFI_ALGO, b.AI_LBPFI_MODEL, b.AI_LBPFI_DATE, " +
                        " b.USER_LBPFI, b.USER_LBPFI_ID, b.USER_LBPFI_DATE, " +
                        " b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BPFI, b.L_B_V_32924BPFI, b.L_B_V_32922BPFI, " +
                        " b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, " +
                        " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
                        " FROM BERDATA b, ENGDATA e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.AI_LBPFI is Null ";
                break;

            case "BLO":
                // Bearing Left Outside
                query = " SELECT b.AI_LBPFO, b.AI_LBPFO_ALGO, b.AI_LBPFO_MODEL, b.AI_LBPFO_DATE, " +
                        " b.USER_LBPFO, b.USER_LBPFO_ID, b.USER_LBPFO_DATE, " +
                        " b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BPFO, b.L_B_V_32924BPFO, b.L_B_V_32922BPFO, " +
                        " b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, " +
                        " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
                        " FROM BERDATA b, ENGDATA e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.AI_LBPFO is Null ";
                break;

            case "BLR":
                // Bearing Left Retainer
                query = " SELECT b.AI_LFTF, b.AI_LFTF_ALGO, b.AI_LFTF_MODEL, b.AI_LFTF_DATE, " +
                        " b.USER_LFTF, b.USER_LFTF_ID, b.USER_LFTF_DATE, " +
                        " b.W_RPM, b.L_B_V_1X, b.L_B_V_6912FTF, b.L_B_V_32924FTF, b.L_B_V_32922FTF, " +
                        " b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, " +
                        " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
                        " FROM BERDATA b, ENGDATA e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.AI_LFTF is Null ";
                break;

            case "BRB":
                // Bearing Right Ball
                query = " SELECT b.AI_RBSF, b.AI_RBSF_ALGO, b.AI_RBSF_MODEL, b.AI_RBSF_DATE, " +
                        " b.USER_RBSF, b.USER_RBPFO_ID, b.USER_RBSF_DATE, " +
                        " b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BSF, b.R_B_V_32924BSF, b.R_B_V_32922BPFI, " +
                        " b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, " +
                        " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
                        " FROM BERDATA b, ENGDATA e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.AI_RBSF is Null ";
                break;

            case "BRI":
                // Bearing Right Inside
                query = " SELECT b.AI_RBPFI, b.AI_RBPFI_ALGO, b.AI_RBPFI_MODEL, b.AI_RBPFI_DATE, " +
                        " b.USER_RBPFI, b.USER_RBPFI_ID, b.USER_RBPFI_DATE, " +
                        " b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BPFI, b.R_B_V_32924BPFI, b.R_B_V_32922BPFI, " +
                        " b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, " +
                        " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
                        " FROM BERDATA b, ENGDATA e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.AI_RBPFI is Null ";
                break;

            case "BRO":
                // Bearing Right Outside
                query = " SELECT b.AI_RBPFO, b.AI_RBPFO_ALGO, b.AI_RBPFO_MODEL, b.AI_RBPFO_DATE, " +
                        " b.USER_RBPFO, b.USER_RBPFO_ID, b.USER_RBPFO_DATE, " +
                        " b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BPFO, b.R_B_V_32924BPFO, b.R_B_V_32922BPFO, " +
                        " b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, " +
                        " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
                        " FROM BERDATA b, ENGDATA e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.AI_RBPFO is Null ";
                break;

            case "BRR":
                // Bearing Right Retainer
                query = " SELECT b.AI_RFTF, b.AI_RFTF_ALGO, b.AI_RFTF_MODEL, b.AI_RFTF_DATE, " +
                        " b.USER_RFTF, b.USER_RFTF_ID, b.USER_RFTF_DATE, " +
                        " b.W_RPM, b.R_B_V_1X, b.R_B_V_6912FTF, b.R_B_V_32924FTF, b.R_B_V_32922FTF, " +
                        " b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, " +
                        " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
                        " FROM BERDATA b, ENGDATA e " +
                        " WHERE b.`DATE` = e.`DATE` AND b.AI_RFTF is Null ";
                break;

            case "WL":
                // Wheel Left
                query = " SELECT w.AI_LW, w.AI_LW_ALGO, w.AI_LW_MODEL, w.AI_LW_DATE, " +
                        " w.USER_LW, w.USER_LW_ID, w.USER_LW_DATE, " +
                        " w.W_RPM, w.L_W_V_2X, w.L_W_V_3X, w.L_W_S_Fault3, " +
                        " e.AC_h, e.AC_v, e.AC_a, w.`DATE` " +
                        " FROM WHLDATA w, ENGDATA e " +
                        " WHERE w.`DATE` = e.`DATE` AND w.AI_LW is Null ";
                break;

            case "WR":
                // Wheel Right
                query = " SELECT w.AI_RW, w.AI_RW_ALGO, w.AI_RW_MODEL, w.AI_RW_DATE, " +
                        " w.USER_RW, w.USER_RW_ID, w.USER_RW_DATE, " +
                        " w.W_RPM, w.R_W_V_2X, w.R_W_V_3X, w.R_W_S_Fault3, " +
                        " e.AC_h, e.AC_v, e.AC_a, w.`DATE` " +
                        " FROM WHLDATA w, ENGDATA e " +
                        " WHERE w.`DATE` = e.`DATE` AND w.AI_RW is Null ";
                break;

            case "G":
                // Gearbox
                query = " SELECT g.AI_GEAR, g.AI_GEAR_ALGO, g.AI_GEAR_MODEL, g.AI_GEAR_DATE, " +
                        " g.USER_GEAR, g.USER_GEAR_ID, g.USER_GEAR_DATE, " +
                        " g.W_RPM, g.G_V_OverallRMS, g.G_V_Wheel1X, g.G_V_Wheel2X, g.G_V_Pinion1X, g.G_V_Pinion2X, g.G_V_GMF1X, g.G_V_GMF2X, " +
                        " e.AC_h, e.AC_v, e.AC_a, g.`DATE` " +
                        " FROM GRBDATA g, ENGDATA e " +
                        " WHERE g.`DATE` = e.`DATE` AND g.AI_GEAR is Null ";
                break;

            case "E":
                // Engine
                query = " SELECT e.AI_ENGINE, e.AI_ENGINE_ALGO, e.AI_ENGINE_MODEL, e.AI_ENGINE_DATE, " +
                        " e.USER_ENGINE, e.USER_ENGINE_ID, e.USER_ENGINE_DATE, " +
                        " e.W_RPM, e.E_V_OverallRMS, e.E_V_1_2X, e.E_V_1X, e.E_V_Crestfactor, e.AC_h, e.AC_v, e.AC_a, e.`DATE` " +
                        " FROM ENGDATA e " +
                        " WHERE e.AI_ENGINE is Null ";
                break;
        }
        System.out.println("Get unlabeled Data -> " + " partType : " + partType);
        try {
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


}
