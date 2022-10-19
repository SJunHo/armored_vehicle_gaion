package kr.gaion.armoredVehicle.dataset.helper;

import kr.gaion.armoredVehicle.database.model.*;
import org.apache.commons.csv.CSVParser;
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

                trainingBearing.setWrpm(Double.parseDouble(csvRecord.get("W_RPM")));
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

    public static List<TrainingWheel> csvToWheel(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingWheel> trainingWheelList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingWheel trainingWheel = new TrainingWheel();
                trainingWheel.setCarId(csvRecord.get("SDAID"));
                trainingWheel.setOperateDate(new SimpleDateFormat("yyyy-MM-dd").parse(csvRecord.get("OPERDATE")));
                trainingWheel.setOperateTime(new SimpleDateFormat("HH:mm:ss").parse(csvRecord.get("OPERTIME")));
                trainingWheel.setOperateDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(csvRecord.get("DATE")));
                trainingWheel.setTimeIndex(Long.parseLong(csvRecord.get("TIME")));

                trainingWheel.setWrpm(Double.parseDouble(csvRecord.get("W_RPM")));
                trainingWheel.setAiPredict(Double.parseDouble(csvRecord.get("AI_Predict")));

                trainingWheel.setLwv2x(Double.parseDouble(csvRecord.get("L_W_V_2X")));
                trainingWheel.setLwv3x(Double.parseDouble(csvRecord.get("L_W_V_3X")));
                trainingWheel.setLwsFault3(Double.parseDouble(csvRecord.get("L_W_S_Fault3")));
                trainingWheel.setRwv2x(Double.parseDouble(csvRecord.get("R_W_V_2X")));
                trainingWheel.setRwv3x(Double.parseDouble(csvRecord.get("R_W_V_3X")));
                trainingWheel.setRwsFault3(Double.parseDouble(csvRecord.get("R_W_S_Fault3")));

                trainingWheelList.add(trainingWheel);
            }
            return trainingWheelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TrainingGearbox> csvToGearbox(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingGearbox> trainingGearboxList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingGearbox trainingGearbox = new TrainingGearbox();
                trainingGearbox.setCarId(csvRecord.get("SDAID"));
                trainingGearbox.setOperateDate(new SimpleDateFormat("yyyy-MM-dd").parse(csvRecord.get("OPERDATE")));
                trainingGearbox.setOperateTime(new SimpleDateFormat("HH:mm:ss").parse(csvRecord.get("OPERTIME")));
                trainingGearbox.setOperateDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(csvRecord.get("DATE")));
                trainingGearbox.setTimeIndex(Long.parseLong(csvRecord.get("TIME")));

                trainingGearbox.setWrpm(Double.parseDouble(csvRecord.get("W_RPM")));
                trainingGearbox.setAiPredict(Double.parseDouble(csvRecord.get("AI_Predict")));

                trainingGearbox.setGvOverallRms(Double.parseDouble(csvRecord.get("G_V_OverallRMS")));
                trainingGearbox.setGvWheel1x(Double.parseDouble(csvRecord.get("G_V_Wheel1X")));
                trainingGearbox.setGvWheel2x(Double.parseDouble(csvRecord.get("G_V_Wheel2X")));
                trainingGearbox.setGvPinion1x(Double.parseDouble(csvRecord.get("G_V_Pinion1X")));
                trainingGearbox.setGvPinion2x(Double.parseDouble(csvRecord.get("G_V_Pinion2X")));
                trainingGearbox.setGvGmf1x(Double.parseDouble(csvRecord.get("G_V_GMF1X")));
                trainingGearbox.setGvGmf2x(Double.parseDouble(csvRecord.get("G_V_GMF2X")));

                trainingGearboxList.add(trainingGearbox);
            }
            return trainingGearboxList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TrainingEngine> csvToEngine(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingEngine> trainingEngineList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingEngine trainingEngine = new TrainingEngine();
                trainingEngine.setCarId(csvRecord.get("SDAID"));
                trainingEngine.setOperateDate(new SimpleDateFormat("yyyy-MM-dd").parse(csvRecord.get("OPERDATE")));
                trainingEngine.setOperateTime(new SimpleDateFormat("HH:mm:ss").parse(csvRecord.get("OPERTIME")));
                trainingEngine.setOperateDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(csvRecord.get("DATE")));
                trainingEngine.setTimeIndex(Long.parseLong(csvRecord.get("TIME")));

                trainingEngine.setWrpm(Double.parseDouble(csvRecord.get("W_RPM")));
                trainingEngine.setAiPredict(Double.parseDouble(csvRecord.get("AI_Predict")));

                trainingEngine.setEvOverallRms(Double.parseDouble(csvRecord.get("E_V_OverallRMS")));
                trainingEngine.setEv12x(Double.parseDouble(csvRecord.get("E_V_1-2X")));
                trainingEngine.setEv1x(Double.parseDouble(csvRecord.get("E_V_1X")));
                trainingEngine.setEvCrestfactor(Double.parseDouble(csvRecord.get("E_V_Crestfactor")));
                trainingEngine.setAch(Double.parseDouble(csvRecord.get("AC_h")));
                trainingEngine.setAcv(Double.parseDouble(csvRecord.get("AC_v")));
                trainingEngine.setAca(Double.parseDouble(csvRecord.get("AC_a")));
                trainingEngine.setLa(Double.parseDouble(csvRecord.get("LA")));
                trainingEngine.setLo(Double.parseDouble(csvRecord.get("LO")));

                trainingEngineList.add(trainingEngine);
            }
            return trainingEngineList;
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