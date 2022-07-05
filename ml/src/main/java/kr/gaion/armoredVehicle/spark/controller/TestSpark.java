package kr.gaion.armoredVehicle.spark.controller;

import au.com.bytecode.opencsv.CSVReader;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.elasticsearch.ESIndexConfig;
import kr.gaion.armoredVehicle.spark.SparkConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class TestSpark {

        public static void main(String[] args) {
                String path = "D:\\Sources\\armored-vehicle\\testData\\data\\bearing_training_001.csv";

                Dataset<Row> dataset = ReadCSV(path);

                Dataset<Row> editDataset = dataset.drop("DATE","TIME","DATETIME","CARID","index");

                Dataset<Row>[] splitDataset = getInputData(editDataset);
                System.out.println(Arrays.toString(splitDataset));

                Dataset<Row> train_set = splitDataset[0];
                Dataset<Row> test_set = splitDataset[1];

                train_set.show();
                test_set.show();

        }

        public static Dataset<Row> ReadCSV(String path) {
                StructType schema = new StructType()
                        .add("CARID", "string")
                        .add("DATE", "string").add("TIME", "string")
                        .add("DATETIME", "string").add("index", "long")
                        .add("L_B_V_OverallRMS", "double").add("L_B_V_1X", "double")
                        .add("L_B_V_6912BPFO", "double").add("L_B_V_6912BPFI", "double")
                        .add("L_B_V_6912BSF", "double").add("L_B_V_6912FTF", "double")
                        .add("L_B_V_32924BPFO", "double").add("L_B_V_32924BPFI", "double")
                        .add("L_B_V_32924BSF", "double").add("L_B_V_32924FTF", "double")
                        .add("L_B_V_32922BPFO", "double").add("L_B_V_32922BPFI", "double")
                        .add("L_B_V_32922BSF", "double").add("L_B_V_32922FTF", "double")
                        .add("L_B_V_Crestfactor", "double").add("L_B_V_Demodulation", "double")
                        .add("L_B_S_Fault1", "double").add("L_B_S_Fault2", "double")
                        .add("L_B_T_Temperature", "double")
                        .add("R_B_V_OveraRlRMS", "double").add("R_B_V_1X", "double")
                        .add("R_B_V_6912BPFO", "double").add("R_B_V_6912BPFI", "double")
                        .add("R_B_V_6912BSF", "double").add("R_B_V_6912FTF", "double")
                        .add("R_B_V_32924BPFO", "double").add("R_B_V_32924BPFI", "double")
                        .add("R_B_V_32924BSF", "double").add("R_B_V_32924FTF", "double")
                        .add("R_B_V_32922BPFO", "double").add("R_B_V_32922BPFI", "double")
                        .add("R_B_V_32922BSF", "double").add("R_B_V_32922FTF", "double")
                        .add("R_B_V_Crestfactor", "double").add("R_B_V_Demodulation", "double")
                        .add("R_B_S_Fault1", "double").add("R_B_S_Fault2", "double")
                        .add("R_B_T_Temperature", "double")
                        .add("AI_Predict", "int");
                SparkSession spark = SparkSession.builder().appName("ML").config("spark.master", "local").getOrCreate();

                Dataset<Row> originalData = spark.read().option("header", "true").schema(schema).csv(path);
                ArrayList<String> inputColsList = new ArrayList<>(Arrays.asList(originalData.columns()));
                System.out.println(inputColsList);
                return originalData;
        }

        public static Dataset<Row>[] getInputData(Dataset<Row> inputDataset){
                ArrayList<String> inputColsList = new ArrayList<>(Arrays.asList(inputDataset.columns()));

                inputColsList.remove("AI_Predict");

                System.out.println("inputColsList: " + inputColsList);
                String[] inputCols = inputColsList.parallelStream().toArray(String[]::new);

                VectorAssembler assembler = new VectorAssembler().setInputCols(inputCols).setOutputCol("features");
                Dataset<Row> dataset = assembler.transform(inputDataset);
                dataset.show();
                Dataset<Row>[] set = dataset.randomSplit(new double[] {0.8, 0.2}, 421);
                return set;
        }
}






