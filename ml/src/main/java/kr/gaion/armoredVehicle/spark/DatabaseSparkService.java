package kr.gaion.armoredVehicle.spark;

import com.google.gson.Gson;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.database.DatabaseConfiguration;
import kr.gaion.armoredVehicle.dataset.config.StorageConfig;
import kr.gaion.armoredVehicle.spark.dto.LabeledData;
import kr.gaion.armoredVehicle.spark.dto.NumericLabeledData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.stereotype.Service;
import scala.collection.JavaConverters;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    @NonNull
    private final StorageConfig storageConfig;

    public static Column col(String columnName) {
        return new Column(columnName);
    }

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

        System.out.println(">>> classCol = " + classCol);
        System.out.println(">>> featureCols = " + featureCols);

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

    public static Dataset<Row> reorderColumns(Dataset<Row> dataset) {
        // dataset의 컬럼 이름을 가져옵니다.
        String[] columns = dataset.columns();

        // dataset에서 선택할 컬럼을 담을 리스트를 생성합니다.
        List<Column> selectedColumns = new ArrayList<>();

        // dataset의 컬럼 순서를 맞춥니다.
        for (String column : columns) {
            selectedColumns.add(col(column));
        }

        // dataset을 선택한 컬럼으로 재구성합니다.
        dataset = dataset.select(selectedColumns.toArray(new Column[selectedColumns.size()]));

        return dataset;
    }

    public static Dataset<Row> unionDatasets(Dataset<Row> ds1, Dataset<Row> ds2) {
        // 스키마를 비교하여 컬럼이름과 컬럼타입이 같은 컬럼을 찾습니다.
        System.out.println(ds1.columns().toString());
        System.out.println(ds2.columns().toString());
        System.out.println(ds1.schema().toString());
        System.out.println(ds2.schema().toString());


        String[] colNames = ds1.columns();
        StructType schema = ds1.schema();
        StructField[] fields = schema.fields();
        List<Column> cols = new ArrayList<>();
        for (String colName : colNames) {
            for (StructField field : fields) {
                if (colName.equalsIgnoreCase(field.name())) {
                    DataType dataType = field.dataType();
                    cols.add(col(colName).cast(dataType));
                    break;
                }
            }
        }
        // 컬럼이름과 컬럼타입이 같은 컬럼을 선택하여 Dataset을 생성합니다.
        Dataset<Row> selectedDs1 = ds1.select(cols.toArray(new Column[0]));
        Dataset<Row> selectedDs2 = ds2.select(cols.toArray(new Column[0]));
        // 두 Dataset을 union 합니다.
        return selectedDs1.union(selectedDs2);
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
        System.out.println("TrainingDataset RowCount : " + jvRddData.count());
        Dataset<LabeledData> esData;
        if (input.getDataForRetraining() != null) {

            List<Map<String, String>> dataForRetraining = input.getDataForRetraining();

            Gson gson = new Gson();
            String jsonData = gson.toJson(dataForRetraining);

            System.out.println(jsonData);

            Dataset<Row> addDataset = spark.read().json(spark.sparkContext().parallelize(
                    JavaConverters.asScalaIterator(
                            Collections.singletonList(jsonData).iterator()
                    ).toSeq(),
                    1,
                    scala.reflect.ClassTag$.MODULE$.apply(String.class)
            ));

            Dataset<Row> newTrainingDataset = unionDatasets(jvRddData, addDataset);
            System.out.println("newTrainingDataset RowCount : " + newTrainingDataset.count());
            newTrainingDataset.show((int) newTrainingDataset.count());
            esData = processData(newTrainingDataset, input.getFilterOutFields(), input.getFeatureCols(), input.getClassCol());
        } else {
            esData = processData(jvRddData, input.getFilterOutFields(), input.getFeatureCols(), input.getClassCol());
        }
        return spark.createDataFrame(esData.rdd(), LabeledData.class);
    }

    public Dataset<Row> getDataRDDFromDb(String partType, String fileName) {
        String query = null;
        switch (partType) {
            case "BLB":
                // Bearing Left Ball
                query = String.format(" SELECT BERTRNNG.IDX, BERTRNNG.AI_LBSF, BERTRNNG.W_RPM, BERTRNNG.L_B_V_1X, BERTRNNG.L_B_V_6912BSF, BERTRNNG.L_B_V_32924BSF, BERTRNNG.L_B_V_32922BSF, " +
                        " BERTRNNG.L_B_V_Crestfactor, BERTRNNG.L_B_V_Demodulation, BERTRNNG.L_B_S_Fault1, BERTRNNG.L_B_S_Fault2, BERTRNNG.L_B_T_Temperature, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
                        " FROM `BERTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE BERTRNNG.FILENM = '%s' ", fileName);
                break;

            case "BLI":
                // Bearing Left Inside
                query = String.format(" SELECT BERTRNNG.IDX, BERTRNNG.AI_LBPFI, BERTRNNG.W_RPM, BERTRNNG.L_B_V_1X, BERTRNNG.L_B_V_6912BPFI, BERTRNNG.L_B_V_32924BPFI, BERTRNNG.L_B_V_32922BPFI, " +
                        " BERTRNNG.L_B_V_Crestfactor, BERTRNNG.L_B_V_Demodulation, BERTRNNG.L_B_S_Fault1, BERTRNNG.L_B_S_Fault2, BERTRNNG.L_B_T_Temperature, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
                        " FROM `BERTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE BERTRNNG.FILENM = '%s' ", fileName);
                break;

            case "BLO":
                // Bearing Left Outside
                query = String.format(" SELECT BERTRNNG.IDX, BERTRNNG.AI_LBPFO, BERTRNNG.W_RPM, BERTRNNG.L_B_V_1X, BERTRNNG.L_B_V_6912BPFO, BERTRNNG.L_B_V_32924BPFO, BERTRNNG.L_B_V_32922BPFO, " +
                        " BERTRNNG.L_B_V_Crestfactor, BERTRNNG.L_B_V_Demodulation, BERTRNNG.L_B_S_Fault1, BERTRNNG.L_B_S_Fault2, BERTRNNG.L_B_T_Temperature, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
                        " FROM `BERTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE BERTRNNG.FILENM = '%s' ", fileName);
                break;

            case "BLR":
                // Bearing Left Retainer
                query = String.format(" SELECT BERTRNNG.IDX, BERTRNNG.AI_LFTF, BERTRNNG.W_RPM, BERTRNNG.L_B_V_1X, BERTRNNG.L_B_V_6912FTF, BERTRNNG.L_B_V_32924FTF, BERTRNNG.L_B_V_32922FTF, " +
                        " BERTRNNG.L_B_V_Crestfactor, BERTRNNG.L_B_V_Demodulation, BERTRNNG.L_B_S_Fault1, BERTRNNG.L_B_S_Fault2, BERTRNNG.L_B_T_Temperature, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
                        " FROM `BERTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE BERTRNNG.FILENM = '%s' ", fileName);
                break;

            case "BRB":
                // Bearing Right Ball
                query = String.format(" SELECT BERTRNNG.IDX, BERTRNNG.AI_RBSF, BERTRNNG.W_RPM, BERTRNNG.R_B_V_1X, BERTRNNG.R_B_V_6912BSF, BERTRNNG.R_B_V_32924BSF, BERTRNNG.R_B_V_32922BSF, " +
                        " BERTRNNG.R_B_V_Crestfactor, BERTRNNG.R_B_V_Demodulation, BERTRNNG.R_B_S_Fault1, BERTRNNG.R_B_S_Fault2, BERTRNNG.R_B_T_Temperature, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
                        " FROM `BERTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE BERTRNNG.FILENM = '%s' ", fileName);
                break;

            case "BRI":
                // Bearing Right Inside
                query = String.format(" SELECT BERTRNNG.IDX, BERTRNNG.AI_RBPFI, BERTRNNG.W_RPM, BERTRNNG.R_B_V_1X, BERTRNNG.R_B_V_6912BPFI, BERTRNNG.R_B_V_32924BPFI, BERTRNNG.R_B_V_32922BPFI, " +
                        " BERTRNNG.R_B_V_Crestfactor, BERTRNNG.R_B_V_Demodulation, BERTRNNG.R_B_S_Fault1, BERTRNNG.R_B_S_Fault2, BERTRNNG.R_B_T_Temperature, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
                        " FROM `BERTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE BERTRNNG.FILENM = '%s' ", fileName);
                break;

            case "BRO":
                // Bearing Right Outside
                query = String.format(" SELECT BERTRNNG.IDX, BERTRNNG.AI_RBPFO, BERTRNNG.W_RPM, BERTRNNG.R_B_V_1X, BERTRNNG.R_B_V_6912BPFO, BERTRNNG.R_B_V_32924BPFO, BERTRNNG.R_B_V_32922BPFO, " +
                        " BERTRNNG.R_B_V_Crestfactor, BERTRNNG.R_B_V_Demodulation, BERTRNNG.R_B_S_Fault1, BERTRNNG.R_B_S_Fault2, BERTRNNG.R_B_T_Temperature, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
                        " FROM `BERTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE BERTRNNG.FILENM = '%s' ", fileName);
                break;

            case "BRR":
                // Bearing Right Retainer
                query = String.format(" SELECT BERTRNNG.IDX, BERTRNNG.AI_RFTF, BERTRNNG.W_RPM, BERTRNNG.R_B_V_1X, BERTRNNG.R_B_V_6912FTF, BERTRNNG.R_B_V_32924FTF, BERTRNNG.R_B_V_32922FTF, " +
                        " BERTRNNG.R_B_V_Crestfactor, BERTRNNG.R_B_V_Demodulation, BERTRNNG.R_B_S_Fault1, BERTRNNG.R_B_S_Fault2, BERTRNNG.R_B_T_Temperature, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
                        " FROM `BERTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE BERTRNNG.FILENM = '%s' ", fileName);
                break;

            case "WL":
                // Wheel Left
                query = String.format(" SELECT WHLTRNNG.IDX, WHLTRNNG.AI_LW, WHLTRNNG.W_RPM, WHLTRNNG.L_W_V_2X, WHLTRNNG.L_W_V_3X, WHLTRNNG.L_W_S_Fault3, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, WHLTRNNG.`DATE` " +
                        " FROM `WHLTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON WHLTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE WHLTRNNG.FILENM = '%s' ", fileName);
                break;

            case "WR":
                // Wheel Right
                query = String.format(" SELECT WHLTRNNG.IDX, WHLTRNNG.AI_RW, WHLTRNNG.W_RPM, WHLTRNNG.R_W_V_2X, WHLTRNNG.R_W_V_3X, WHLTRNNG.R_W_S_Fault3, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, WHLTRNNG.`DATE` " +
                        " FROM `WHLTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON WHLTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE WHLTRNNG.FILENM = '%s' ", fileName);
                break;

            case "G":
                // Gearbox
                query = String.format(" SELECT GRBTRNNG.IDX, GRBTRNNG.AI_GEAR, GRBTRNNG.W_RPM, GRBTRNNG.G_V_OverallRMS, GRBTRNNG.G_V_Wheel1X, GRBTRNNG.G_V_Wheel2X, " +
                        " GRBTRNNG.G_V_Pinion1X, GRBTRNNG.G_V_Pinion2X, GRBTRNNG.G_V_GMF1X, GRBTRNNG.G_V_GMF2X, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, GRBTRNNG.`DATE` " +
                        " FROM `GRBTRNNG` " +
                        " INNER JOIN `ENGTRNNG` ON GRBTRNNG.`DATE` = ENGTRNNG.`DATE` " +
                        " WHERE GRBTRNNG.FILENM = '%s' ", fileName);
                break;

            case "E":
                // Engine
                query = String.format(" SELECT ENGTRNNG.IDX, ENGTRNNG.AI_ENGINE, ENGTRNNG.W_RPM, ENGTRNNG.E_V_OverallRMS, " +
                        " ENGTRNNG.E_V_1_2X, ENGTRNNG.E_V_1X, ENGTRNNG.E_V_Crestfactor, " +
                        " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, ENGTRNNG.`DATE` " +
                        " FROM `ENGTRNNG` " +
                        " WHERE ENGTRNNG.FILENM = '%s' ", fileName);
                break;

            case "B_LIFE":
                // Bearing Remaining Life
                query = String.format(" SELECT * FROM `BERLIFE` WHERE `BERLIFE`.FILENM = '%s' ", fileName);
                break;

            case "W_LIFE":
                // Wheel Remaining Life
                query = String.format(" SELECT * FROM `WHLLIFE` WHERE `WHLLIFE`.FILENM = '%s' ", fileName);
                break;

            case "G_LIFE":
                // Gearbox Remaining Life
                query = String.format(" SELECT * FROM `GRBLIFE` WHERE `GRBLIFE`.FILENM = '%s' ", fileName);
                break;

            case "E_LIFE":
                // Engine Remaining Life
                query = String.format(" SELECT * FROM `ENGLIFE` WHERE `ENGLIFE`.FILENM = '%s' ", fileName);
                break;
        }
        try {
            System.out.println(">>> Get Training Data -> " + " partType : " + partType + " / " + "fileName : " + fileName);
            Dataset<Row> jdbcDF = spark.read()
                    .format("jdbc")
                    .option("url", "jdbc:mysql://" + storageConfig.getDbHost() + ":" + storageConfig.getDbPort() + "/" + storageConfig.getDbDatabase())
                    .option("user", storageConfig.getDbUser())
                    .option("password", storageConfig.getDbPassword())
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

        var jvRddData = this.getDataRDDFromDb(input.getPartType(), input.getFileName());

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
    public Dataset<Row> getUnlabeledDataFromDb(BaseAlgorithmPredictInput baseAlgorithmPredictInput, List<String> docIds) {
        System.out.println(">>> Selected docIds : " + docIds);
        String stringDocIds = docIds.toString().replace("[", "").replace("]", "");
        String partType = baseAlgorithmPredictInput.getDataType();
        String query = null;
        switch (partType) {
            case "BLB":
                // Bearing Left Ball
                query = String.format(" SELECT BERDATA.IDX, BERDATA.AI_LBSF, BERDATA.AI_LBSF_ALGO, BERDATA.AI_LBSF_MODEL, BERDATA.AI_LBSF_DATE, " +
                        " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BSF, BERDATA.L_B_V_32924BSF, BERDATA.L_B_V_32922BSF, " +
                        " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_LBSF IS NULL AND BERDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "BLI":
                // Bearing Left Inside
                query = String.format(" SELECT BERDATA.IDX, BERDATA.AI_LBPFI, BERDATA.AI_LBPFI_ALGO, BERDATA.AI_LBPFI_MODEL, BERDATA.AI_LBPFI_DATE, " +
                        " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BPFI, BERDATA.L_B_V_32924BPFI, BERDATA.L_B_V_32922BPFI, " +
                        " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_LBPFI IS NULL AND BERDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "BLO":
                // Bearing Left Outside
                query = String.format(" SELECT BERDATA.IDX, BERDATA.AI_LBPFO, BERDATA.AI_LBPFO_ALGO, BERDATA.AI_LBPFO_MODEL, BERDATA.AI_LBPFO_DATE, " +
                        " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BPFO, BERDATA.L_B_V_32924BPFO, BERDATA.L_B_V_32922BPFO, " +
                        " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_LBPFO IS NULL AND BERDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "BLR":
                // Bearing Left Retainer
                query = String.format(" SELECT BERDATA.IDX, BERDATA.AI_LFTF, BERDATA.AI_LFTF_ALGO, BERDATA.AI_LFTF_MODEL, BERDATA.AI_LFTF_DATE, " +
                        " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912FTF, BERDATA.L_B_V_32924FTF, BERDATA.L_B_V_32922FTF, " +
                        " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_LFTF IS NULL AND BERDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "BRB":
                // Bearing Right Ball
                query = String.format(" SELECT BERDATA.IDX, BERDATA.AI_RBSF, BERDATA.AI_RBSF_ALGO, BERDATA.AI_RBSF_MODEL, BERDATA.AI_RBSF_DATE, " +
                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BSF, BERDATA.R_B_V_32924BSF, BERDATA.R_B_V_32922BSF, " +
                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_RBSF IS NULL AND BERDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "BRI":
                // Bearing Right Inside
                query = String.format(" SELECT BERDATA.IDX, BERDATA.AI_RBPFI, BERDATA.AI_RBPFI_ALGO, BERDATA.AI_RBPFI_MODEL, BERDATA.AI_RBPFI_DATE, " +
                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BPFI, BERDATA.R_B_V_32924BPFI, BERDATA.R_B_V_32922BPFI, " +
                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_RBPFI IS NULL AND BERDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "BRO":
                // Bearing Right Outside
                query = String.format(" SELECT BERDATA.IDX, BERDATA.AI_RBPFO, BERDATA.AI_RBPFO_ALGO, BERDATA.AI_RBPFO_MODEL, BERDATA.AI_RBPFO_DATE, " +
                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BPFO, BERDATA.R_B_V_32924BPFO, BERDATA.R_B_V_32922BPFO, " +
                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_RBPFO IS NULL AND BERDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "BRR":
                // Bearing Right Retainer
                query = String.format(" SELECT BERDATA.IDX, BERDATA.AI_RFTF, BERDATA.AI_RFTF_ALGO, BERDATA.AI_RFTF_MODEL, BERDATA.AI_RFTF_DATE, " +
                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912FTF, BERDATA.R_B_V_32924FTF, BERDATA.R_B_V_32922FTF, " +
                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_RFTF IS NULL AND BERDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "WL":
                // Wheel Left
                query = String.format(" SELECT WHLDATA.IDX, WHLDATA.AI_LW, WHLDATA.AI_LW_ALGO, WHLDATA.AI_LW_MODEL, WHLDATA.AI_LW_DATE, " +
                        " WHLDATA.W_RPM, WHLDATA.L_W_V_2X, WHLDATA.L_W_V_3X, WHLDATA.L_W_S_Fault3, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` " +
                        " FROM `WHLDATA` " +
                        " INNER JOIN `ENGDATA` ON WHLDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE WHLDATA.AI_LW IS NULL AND WHLDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "WR":
                // Wheel Right
                query = String.format(" SELECT WHLDATA.IDX, WHLDATA.AI_RW, WHLDATA.AI_RW_ALGO, WHLDATA.AI_RW_MODEL, WHLDATA.AI_RW_DATE, " +
                        " WHLDATA.W_RPM, WHLDATA.R_W_V_2X, WHLDATA.R_W_V_3X, WHLDATA.R_W_S_Fault3, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` " +
                        " FROM `WHLDATA` " +
                        " INNER JOIN `ENGDATA` ON WHLDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE WHLDATA.AI_RW IS NULL AND WHLDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "G":
                // Gearbox
                query = String.format(" SELECT GRBDATA.IDX, GRBDATA.AI_GEAR, GRBDATA.AI_GEAR_ALGO, GRBDATA.AI_GEAR_MODEL, GRBDATA.AI_GEAR_DATE, " +
                        " GRBDATA.W_RPM, GRBDATA.G_V_OverallRMS, GRBDATA.G_V_Wheel1X, GRBDATA.G_V_Wheel2X, " +
                        " GRBDATA.G_V_Pinion1X, GRBDATA.G_V_Pinion2X, GRBDATA.G_V_GMF1X, GRBDATA.G_V_GMF2X, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, GRBDATA.`DATE` " +
                        " FROM `GRBDATA` " +
                        " INNER JOIN `ENGDATA` ON GRBDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE GRBDATA.AI_GEAR IS NULL AND GRBDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "E":
                // Engine
                query = String.format(" SELECT ENGDATA.IDX, ENGDATA.AI_ENGINE, ENGDATA.AI_ENGINE_ALGO, ENGDATA.AI_ENGINE_MODEL, ENGDATA.AI_ENGINE_DATE, " +
                        " ENGDATA.USER_ENGINE, ENGDATA.USER_ENGINE_ID, ENGDATA.USER_ENGINE_DATE, " +
                        " ENGDATA.W_RPM, ENGDATA.E_V_OverallRMS, ENGDATA.E_V_1_2X, ENGDATA.E_V_1X, ENGDATA.E_V_Crestfactor, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, ENGDATA.`DATE` " +
                        " FROM `ENGDATA` " +
                        " WHERE  AI_ENGINE IS NULL AND ENGDATA.IDX IN (%s) ", stringDocIds);
                break;

            case "B_LIFE":
                // Bearing Remaining Life
                query = String.format(" SELECT * FROM `BERLIFEDATA` WHERE `BERLIFEDATA`.AI_Trip IS NULL AND `BERLIFEDATA`.IDX IN (%s) ", stringDocIds);
                break;

            case "W_LIFE":
                // Wheel Remaining Life
                query = String.format(" SELECT * FROM `WHLLIFEDATA` WHERE `WHLLIFEDATA`.AI_Trip IS NULL AND `WHLLIFEDATA`.IDX IN (%s) ", stringDocIds);
                break;

            case "G_LIFE":
                // Gearbox Remaining Life
                query = String.format(" SELECT * FROM `GRBLIFEDATA` WHERE `GRBLIFEDATA`.AI_Trip IS NULL AND `GRBLIFEDATA`.IDX IN (%s) ", stringDocIds);
                break;

            case "E_LIFE":
                // Engine Remaining Life
                query = String.format(" SELECT * FROM `ENGLIFEDATA` WHERE `ENGLIFEDATA`.AI_Trip IS NULL AND `ENGLIFEDATA`.IDX IN (%s) ", stringDocIds);
                break;
        }
        System.out.println(">>> Get unlabeled data -> " + " partType : " + partType);
        try {
            Dataset<Row> jdbcDF = spark.read()
                    .format("jdbc")
                    .option("url", "jdbc:mysql://" + storageConfig.getDbHost() + ":" + storageConfig.getDbPort() + "/" + storageConfig.getDbDatabase())
                    .option("user", storageConfig.getDbUser())
                    .option("password", storageConfig.getDbPassword())
                    .option("query", query)
                    .load();
            return jdbcDF;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Dataset<Row> getAllUnlabeledDataFromDb(BaseAlgorithmPredictInput baseAlgorithmPredictInput) {
        String partType = baseAlgorithmPredictInput.getDataType();
        String query = null;
        switch (partType) {
            case "BLB":
                // Bearing Left Ball
                query = " SELECT BERDATA.IDX, BERDATA.AI_LBSF, BERDATA.AI_LBSF_ALGO, BERDATA.AI_LBSF_MODEL, BERDATA.AI_LBSF_DATE, " +
                        " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BSF, BERDATA.L_B_V_32924BSF, BERDATA.L_B_V_32922BSF, " +
                        " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_LBSF IS NULL ";
                break;

            case "BLI":
                // Bearing Left Inside
                query = " SELECT BERDATA.IDX, BERDATA.AI_LBPFI, BERDATA.AI_LBPFI_ALGO, BERDATA.AI_LBPFI_MODEL, BERDATA.AI_LBPFI_DATE, " +
                        " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BPFI, BERDATA.L_B_V_32924BPFI, BERDATA.L_B_V_32922BPFI, " +
                        " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_LBPFI IS NULL ";
                break;

            case "BLO":
                // Bearing Left Outside
                query = " SELECT BERDATA.IDX, BERDATA.AI_LBPFO, BERDATA.AI_LBPFO_ALGO, BERDATA.AI_LBPFO_MODEL, BERDATA.AI_LBPFO_DATE, " +
                        " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BPFO, BERDATA.L_B_V_32924BPFO, BERDATA.L_B_V_32922BPFO, " +
                        " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_LBPFO IS NULL ";
                break;

            case "BLR":
                // Bearing Left Retainer
                query = " SELECT BERDATA.IDX, BERDATA.AI_LFTF, BERDATA.AI_LFTF_ALGO, BERDATA.AI_LFTF_MODEL, BERDATA.AI_LFTF_DATE, " +
                        " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912FTF, BERDATA.L_B_V_32924FTF, BERDATA.L_B_V_32922FTF, " +
                        " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_LFTF IS NULL ";
                break;

            case "BRB":
                // Bearing Right Ball
                query = " SELECT BERDATA.IDX, BERDATA.AI_RBSF, BERDATA.AI_RBSF_ALGO, BERDATA.AI_RBSF_MODEL, BERDATA.AI_RBSF_DATE, " +
                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BSF, BERDATA.R_B_V_32924BSF, BERDATA.R_B_V_32922BSF, " +
                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_RBSF IS NULL ";
                break;

            case "BRI":
                // Bearing Right Inside
                query = " SELECT BERDATA.IDX, BERDATA.AI_RBPFI, BERDATA.AI_RBPFI_ALGO, BERDATA.AI_RBPFI_MODEL, BERDATA.AI_RBPFI_DATE, " +
                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BPFI, BERDATA.R_B_V_32924BPFI, BERDATA.R_B_V_32922BPFI, " +
                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_RBPFI IS NULL ";
                break;

            case "BRO":
                // Bearing Right Outside
                query = " SELECT BERDATA.IDX, BERDATA.AI_RBPFO, BERDATA.AI_RBPFO_ALGO, BERDATA.AI_RBPFO_MODEL, BERDATA.AI_RBPFO_DATE, " +
                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BPFO, BERDATA.R_B_V_32924BPFO, BERDATA.R_B_V_32922BPFO, " +
                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_RBPFO IS NULL ";
                break;

            case "BRR":
                // Bearing Right Retainer
                query = " SELECT BERDATA.IDX, BERDATA.AI_RFTF, BERDATA.AI_RFTF_ALGO, BERDATA.AI_RFTF_MODEL, BERDATA.AI_RFTF_DATE, " +
                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912FTF, BERDATA.R_B_V_32924FTF, BERDATA.R_B_V_32922FTF, " +
                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
                        " FROM `BERDATA` " +
                        " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE BERDATA.AI_RFTF IS NULL ";
                break;

            case "WL":
                // Wheel Left
                query = " SELECT WHLDATA.IDX, WHLDATA.AI_LW, WHLDATA.AI_LW_ALGO, WHLDATA.AI_LW_MODEL, WHLDATA.AI_LW_DATE, " +
                        " WHLDATA.W_RPM, WHLDATA.L_W_V_2X, WHLDATA.L_W_V_3X, WHLDATA.L_W_S_Fault3, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` " +
                        " FROM `WHLDATA` " +
                        " INNER JOIN `ENGDATA` ON WHLDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE WHLDATA.AI_LW IS NULL ";
                break;

            case "WR":
                // Wheel Right
                query = " SELECT WHLDATA.IDX, WHLDATA.AI_RW, WHLDATA.AI_RW_ALGO, WHLDATA.AI_RW_MODEL, WHLDATA.AI_RW_DATE, " +
                        " WHLDATA.W_RPM, WHLDATA.R_W_V_2X, WHLDATA.R_W_V_3X, WHLDATA.R_W_S_Fault3, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` " +
                        " FROM `WHLDATA` " +
                        " INNER JOIN `ENGDATA` ON WHLDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE WHLDATA.AI_RW IS NULL ";
                break;

            case "G":
                // Gearbox
                query = " SELECT GRBDATA.IDX, GRBDATA.AI_GEAR, GRBDATA.AI_GEAR_ALGO, GRBDATA.AI_GEAR_MODEL, GRBDATA.AI_GEAR_DATE, " +
                        " GRBDATA.W_RPM, GRBDATA.G_V_OverallRMS, GRBDATA.G_V_Wheel1X, GRBDATA.G_V_Wheel2X, " +
                        " GRBDATA.G_V_Pinion1X, GRBDATA.G_V_Pinion2X, GRBDATA.G_V_GMF1X, GRBDATA.G_V_GMF2X, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, GRBDATA.`DATE` " +
                        " FROM `GRBDATA` " +
                        " INNER JOIN `ENGDATA` ON GRBDATA.`DATE` = ENGDATA.`DATE` " +
                        " WHERE GRBDATA.AI_GEAR IS NULL ";
                break;

            case "E":
                // Engine
                query = " SELECT ENGDATA.IDX, ENGDATA.AI_ENGINE, ENGDATA.AI_ENGINE_ALGO, ENGDATA.AI_ENGINE_MODEL, ENGDATA.AI_ENGINE_DATE, " +
                        " ENGDATA.USER_ENGINE, ENGDATA.USER_ENGINE_ID, ENGDATA.USER_ENGINE_DATE, " +
                        " ENGDATA.W_RPM, ENGDATA.E_V_OverallRMS, ENGDATA.E_V_1_2X, ENGDATA.E_V_1X, ENGDATA.E_V_Crestfactor, " +
                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, ENGDATA.`DATE` " +
                        " FROM `ENGDATA` " +
                        " WHERE  AI_ENGINE IS NULL ";
                break;

            case "B_LIFE":
                // Bearing Remaining Life
                query = " SELECT * FROM `BERLIFEDATA` WHERE `BERLIFEDATA`.AI_Trip IS NULL ";
                break;

            case "W_LIFE":
                // Wheel Remaining Life
                query = " SELECT * FROM `WHLLIFEDATA` WHERE `WHLLIFEDATA`.AI_Trip IS NULL ";
                break;

            case "G_LIFE":
                // Gearbox Remaining Life
                query = " SELECT * FROM `GRBLIFEDATA` WHERE `GRBLIFEDATA`.AI_Trip IS NULL ";
                break;

            case "E_LIFE":
                // Engine Remaining Life
                query = " SELECT * FROM `ENGLIFEDATA` WHERE `ENGLIFEDATA`.AI_Trip IS NULL ";
                break;
        }
        System.out.println(">>> Get all unlabeled data -> " + " partType : " + partType);
        try {
            Dataset<Row> jdbcDF = spark.read()
                    .format("jdbc")
                    .option("url", "jdbc:mysql://" + storageConfig.getDbHost() + ":" + storageConfig.getDbPort() + "/" + storageConfig.getDbDatabase())
                    .option("user", storageConfig.getDbUser())
                    .option("password", storageConfig.getDbPassword())
                    .option("query", query)
                    .load();
            return jdbcDF;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
