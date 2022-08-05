package kr.gaion.armoredVehicle.dataset.helper;

import kr.gaion.armoredVehicle.database.model.TrainingTempLife;
import org.apache.commons.csv.CSVParser;
import kr.gaion.armoredVehicle.database.model.TrainingBearing;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<TrainingBearing> csvToBearing(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingBearing> trainingBearingList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingBearing trainingBearing = new TrainingBearing();
                trainingBearing.setCarId(csvRecord.get("SDAID"));
                trainingBearing.setOperateDate(new SimpleDateFormat("yyyy-MM-dd").parse(csvRecord.get("OPERDATE")));
                trainingBearing.setOperateTime(new SimpleDateFormat("HH:mm:ss").parse(csvRecord.get("OPERTIME")));
                trainingBearing.setOperateDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(csvRecord.get("DATE")));
                trainingBearing.setTimeIndex(Long.parseLong(csvRecord.get("TIME")));
                //left
                trainingBearing.setLbvOverallRMS(Double.parseDouble(csvRecord.get("L_B_V_OverallRMS")));
                trainingBearing.setLbv1x(Double.parseDouble(csvRecord.get("L_B_V_1X")));
                trainingBearing.setLbv6912bpfo(Double.parseDouble(csvRecord.get("L_B_V_6912BPFO")));
                trainingBearing.setLbv6912bpfi(Double.parseDouble(csvRecord.get("L_B_V_6912BPFI")));
                trainingBearing.setLbv6912bsf(Double.parseDouble(csvRecord.get("L_B_V_6912BSF")));
                trainingBearing.setLbv6912ftf(Double.parseDouble(csvRecord.get("L_B_V_6912FTF")));
                trainingBearing.setLbv32924bpfo(Double.parseDouble(csvRecord.get("L_B_V_32924BPFO")));
                trainingBearing.setLbv32924bpfi(Double.parseDouble(csvRecord.get("L_B_V_32924BPFI")));
                trainingBearing.setLbv32924bsf(Double.parseDouble(csvRecord.get("L_B_V_32924BSF")));
                trainingBearing.setLbv32924ftf(Double.parseDouble(csvRecord.get("L_B_V_32924FTF")));
                trainingBearing.setLbv32922bpfo(Double.parseDouble(csvRecord.get("L_B_V_32922BPFO")));
                trainingBearing.setLbv32922bpfi(Double.parseDouble(csvRecord.get("L_B_V_32922BPFI")));
                trainingBearing.setLbv32922bsf(Double.parseDouble(csvRecord.get("L_B_V_32922BSF")));
                trainingBearing.setLbv32922ftf(Double.parseDouble(csvRecord.get("L_B_V_32922FTF")));
                trainingBearing.setLbvCrestfactor(Double.parseDouble(csvRecord.get("L_B_V_Crestfactor")));
                trainingBearing.setLbvDemodulation(Double.parseDouble(csvRecord.get("L_B_V_Demodulation")));
                trainingBearing.setLbsFault1(Double.parseDouble(csvRecord.get("L_B_S_Fault1")));
                trainingBearing.setLbsFault2(Double.parseDouble(csvRecord.get("L_B_S_Fault2")));
                trainingBearing.setLbtTemperature(Double.parseDouble(csvRecord.get("L_B_T_Temperature")));
                //right
                trainingBearing.setRbvOverallRMS(Double.parseDouble(csvRecord.get("R_B_V_OverallRMS")));
                trainingBearing.setRbv1x(Double.parseDouble(csvRecord.get("R_B_V_1X")));
                trainingBearing.setRbv6912bpfo(Double.parseDouble(csvRecord.get("R_B_V_6912BPFO")));
                trainingBearing.setRbv6912bpfi(Double.parseDouble(csvRecord.get("R_B_V_6912BPFI")));
                trainingBearing.setRbv6912bsf(Double.parseDouble(csvRecord.get("R_B_V_6912BSF")));
                trainingBearing.setRbv6912ftf(Double.parseDouble(csvRecord.get("R_B_V_6912FTF")));
                trainingBearing.setRbv32924bpfo(Double.parseDouble(csvRecord.get("R_B_V_32924BPFO")));
                trainingBearing.setRbv32924bpfi(Double.parseDouble(csvRecord.get("R_B_V_32924BPFI")));
                trainingBearing.setRbv32924bsf(Double.parseDouble(csvRecord.get("R_B_V_32924BSF")));
                trainingBearing.setRbv32924ftf(Double.parseDouble(csvRecord.get("R_B_V_32924FTF")));
                trainingBearing.setRbv32922bpfo(Double.parseDouble(csvRecord.get("R_B_V_32922BPFO")));
                trainingBearing.setRbv32922bpfi(Double.parseDouble(csvRecord.get("R_B_V_32922BPFI")));
                trainingBearing.setRbv32922bsf(Double.parseDouble(csvRecord.get("R_B_V_32922BSF")));
                trainingBearing.setRbv32922ftf(Double.parseDouble(csvRecord.get("R_B_V_32922FTF")));
                trainingBearing.setRbvCrestfactor(Double.parseDouble(csvRecord.get("R_B_V_Crestfactor")));
                trainingBearing.setRbvDemodulation(Double.parseDouble(csvRecord.get("R_B_V_Demodulation")));
                trainingBearing.setRbsFault1(Double.parseDouble(csvRecord.get("R_B_S_Fault1")));
                trainingBearing.setRbsFault2(Double.parseDouble(csvRecord.get("R_B_S_Fault2")));
                trainingBearing.setRbtTemperature(Double.parseDouble(csvRecord.get("R_B_T_Temperature")));

                trainingBearing.setAiPredict(Double.parseDouble(csvRecord.get("AI_Predict")));

                trainingBearingList.add(trainingBearing);
            }
            return trainingBearingList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TrainingTempLife> csvToTempLife(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<TrainingTempLife> trainingTempLifeList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                TrainingTempLife trainingTempLife = new TrainingTempLife();
                trainingTempLife.setAiPredict(Double.parseDouble(csvRecord.get("AI_Predict")));
                trainingTempLife.setCoreCycle(Double.parseDouble(csvRecord.get("CORECYCLE")));
                trainingTempLife.setCpuUtil(Double.parseDouble(csvRecord.get("CPUUTIL")));
                trainingTempLife.setDiskAccesses(Double.parseDouble(csvRecord.get("DISKACCESSES")));
                trainingTempLife.setDiskBlocks(Double.parseDouble(csvRecord.get("DISKBLOCKS")));
                trainingTempLife.setDiskUtil(Double.parseDouble(csvRecord.get("DISKUTIL")));
                trainingTempLife.setInstRetired(Double.parseDouble(csvRecord.get("INSTRETIRED")));
                trainingTempLife.setLastLevel(Double.parseDouble(csvRecord.get("LASTLEVEL")));
                trainingTempLife.setMemoryBus(Double.parseDouble(csvRecord.get("MEMORYBUS")));
                trainingTempLife.setTime(csvRecord.get("TIME"));


                trainingTempLifeList.add(trainingTempLife);
            }
            return trainingTempLifeList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
