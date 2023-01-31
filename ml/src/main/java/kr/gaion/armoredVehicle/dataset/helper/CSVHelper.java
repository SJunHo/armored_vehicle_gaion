package kr.gaion.armoredVehicle.dataset.helper;

import kr.gaion.armoredVehicle.database.model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class CSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<TrainingBearing> csvToBearing(InputStream is, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingBearing> trainingBearingList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingBearing trainingBearing = new TrainingBearing();
                trainingBearing.setCarId(csvRecord.get("SDAID"));

                // Date(set to timezone)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                trainingBearing.setOperateDateTime(sdf.parse(csvRecord.get("DATE").trim()));

                trainingBearing.setTimeIndex(Long.parseLong(csvRecord.get("TIME")));

                // File Name
                trainingBearing.setFileNm(fileName.replace(".csv", ""));

                trainingBearing.setWrpm(Double.parseDouble(csvRecord.get("W_RPM")));

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

                // AI-Predict
                trainingBearing.setAiLbpfo(Integer.parseInt(csvRecord.get("AI_LBPFO")));
                trainingBearing.setAiLbpfi(Integer.parseInt(csvRecord.get("AI_LBPFI")));
                trainingBearing.setAiLbsf(Integer.parseInt(csvRecord.get("AI_LBSF")));
                trainingBearing.setAiLftf(Integer.parseInt(csvRecord.get("AI_LFTF")));
                trainingBearing.setAiRbpfo(Integer.parseInt(csvRecord.get("AI_RBPFO")));
                trainingBearing.setAiRbpfi(Integer.parseInt(csvRecord.get("AI_RBPFI")));
                trainingBearing.setAiRbsf(Integer.parseInt(csvRecord.get("AI_RBSF")));
                trainingBearing.setAiRftf(Integer.parseInt(csvRecord.get("AI_RFTF")));

                trainingBearingList.add(trainingBearing);
            }
            return trainingBearingList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TrainingWheel> csvToWheel(InputStream is, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingWheel> trainingWheelList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingWheel trainingWheel = new TrainingWheel();
                trainingWheel.setCarId(csvRecord.get("SDAID"));

                // Date(set to timezone)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                trainingWheel.setOperateDateTime(sdf.parse(csvRecord.get("DATE")));

                trainingWheel.setTimeIndex(Long.parseLong(csvRecord.get("TIME")));

                // File Name
                trainingWheel.setFileNm(fileName.replace(".csv", ""));

                trainingWheel.setWrpm(Double.parseDouble(csvRecord.get("W_RPM")));

                //left
                trainingWheel.setLwv2x(Double.parseDouble(csvRecord.get("L_W_V_2X")));
                trainingWheel.setLwv3x(Double.parseDouble(csvRecord.get("L_W_V_3X")));
                trainingWheel.setLwsFault3(Double.parseDouble(csvRecord.get("L_W_S_Fault3")));

                // right
                trainingWheel.setRwv2x(Double.parseDouble(csvRecord.get("R_W_V_2X")));
                trainingWheel.setRwv3x(Double.parseDouble(csvRecord.get("R_W_V_3X")));
                trainingWheel.setRwsFault3(Double.parseDouble(csvRecord.get("R_W_S_Fault3")));

                // AI-Predict
                trainingWheel.setAiLw(Integer.parseInt(csvRecord.get("AI_LW")));
                trainingWheel.setAiRw(Integer.parseInt(csvRecord.get("AI_RW")));

                trainingWheelList.add(trainingWheel);
            }
            return trainingWheelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TrainingGearbox> csvToGearbox(InputStream is, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingGearbox> trainingGearboxList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingGearbox trainingGearbox = new TrainingGearbox();
                trainingGearbox.setCarId(csvRecord.get("SDAID"));

                // Date(set to timezone)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                trainingGearbox.setOperateDateTime(sdf.parse(csvRecord.get("DATE")));

                trainingGearbox.setTimeIndex(Long.parseLong(csvRecord.get("TIME")));

                // File Name
                trainingGearbox.setFileNm(fileName.replace(".csv", ""));

                trainingGearbox.setWrpm(Double.parseDouble(csvRecord.get("W_RPM")));

                trainingGearbox.setGvOverallRms(Double.parseDouble(csvRecord.get("G_V_OverallRMS")));
                trainingGearbox.setGvWheel1x(Double.parseDouble(csvRecord.get("G_V_Wheel1X")));
                trainingGearbox.setGvWheel2x(Double.parseDouble(csvRecord.get("G_V_Wheel2X")));
                trainingGearbox.setGvPinion1x(Double.parseDouble(csvRecord.get("G_V_Pinion1X")));
                trainingGearbox.setGvPinion2x(Double.parseDouble(csvRecord.get("G_V_Pinion2X")));
                trainingGearbox.setGvGmf1x(Double.parseDouble(csvRecord.get("G_V_GMF1X")));
                trainingGearbox.setGvGmf2x(Double.parseDouble(csvRecord.get("G_V_GMF2X")));

                // AI-Predict
                trainingGearbox.setAiGear(Integer.parseInt(csvRecord.get("AI_GEAR")));

                trainingGearboxList.add(trainingGearbox);
            }
            return trainingGearboxList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TrainingEngine> csvToEngine(InputStream is, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingEngine> trainingEngineList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingEngine trainingEngine = new TrainingEngine();
                trainingEngine.setCarId(csvRecord.get("SDAID"));

                // Date(set to timezone)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                trainingEngine.setOperateDateTime(sdf.parse(csvRecord.get("DATE")));

                trainingEngine.setTimeIndex(Long.parseLong(csvRecord.get("TIME")));

                // File Name
                trainingEngine.setFileNm(fileName.replace(".csv", ""));

                trainingEngine.setWrpm(Double.parseDouble(csvRecord.get("W_RPM")));

                trainingEngine.setEvOverallRms(Double.parseDouble(csvRecord.get("E_V_OverallRMS")));
                trainingEngine.setEv12x(Double.parseDouble(csvRecord.get("E_V_1_2X")));
                trainingEngine.setEv1x(Double.parseDouble(csvRecord.get("E_V_1X")));
                trainingEngine.setEvCrestfactor(Double.parseDouble(csvRecord.get("E_V_Crestfactor")));
                trainingEngine.setAch(Double.parseDouble(csvRecord.get("AC_h")));
                trainingEngine.setAcv(Double.parseDouble(csvRecord.get("AC_v")));
                trainingEngine.setAca(Double.parseDouble(csvRecord.get("AC_a")));
                trainingEngine.setLa(Double.parseDouble(csvRecord.get("LA")));
                trainingEngine.setLo(Double.parseDouble(csvRecord.get("LO")));

                // AI-Predict
                trainingEngine.setAiEngine(Integer.parseInt(csvRecord.get("AI_ENGINE")));

                trainingEngineList.add(trainingEngine);
            }
            return trainingEngineList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TrainingBearingLife> csvToBearingLife(InputStream is, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingBearingLife> trainingBearingLifeList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingBearingLife trainingBearingLife = new TrainingBearingLife();

                // File Name
                trainingBearingLife.setFilenm(fileName.replace(".csv", ""));

                // Parameters
                trainingBearingLife.setBOverallRMS(Double.parseDouble(csvRecord.get("B_OverallRMS")));
                trainingBearingLife.setB1x(Double.parseDouble(csvRecord.get("B_1X")));
                trainingBearingLife.setB6912bpfo(Double.parseDouble(csvRecord.get("B_6912BPFO")));
                trainingBearingLife.setB6912bpfi(Double.parseDouble(csvRecord.get("B_6912BPFI")));
                trainingBearingLife.setB6912bsf(Double.parseDouble(csvRecord.get("B_6912BSF")));
                trainingBearingLife.setB6912ftf(Double.parseDouble(csvRecord.get("B_6912FTF")));
                trainingBearingLife.setB32924bpfo(Double.parseDouble(csvRecord.get("B_32924BPFO")));
                trainingBearingLife.setB32924bpfi(Double.parseDouble(csvRecord.get("B_32924BPFI")));
                trainingBearingLife.setB32924bsf(Double.parseDouble(csvRecord.get("B_32924BSF")));
                trainingBearingLife.setB32924ftf(Double.parseDouble(csvRecord.get("B_32924FTF")));
                trainingBearingLife.setB32922bpfo(Double.parseDouble(csvRecord.get("B_32922BPFO")));
                trainingBearingLife.setB32922bpfi(Double.parseDouble(csvRecord.get("B_32922BPFI")));
                trainingBearingLife.setB32922bsf(Double.parseDouble(csvRecord.get("B_32922BSF")));
                trainingBearingLife.setBCrestFactor(Double.parseDouble(csvRecord.get("B_CrestFactor")));
                trainingBearingLife.setBDemodulation(Double.parseDouble(csvRecord.get("B_Demodulation")));
                trainingBearingLife.setBFault1(Double.parseDouble(csvRecord.get("B_Fault1")));
                trainingBearingLife.setBFault2(Double.parseDouble(csvRecord.get("B_Fault2")));

                // AI-Predict
                trainingBearingLife.setTrip(Integer.parseInt(csvRecord.get("Trip")));

                trainingBearingLifeList.add(trainingBearingLife);
            }
            return trainingBearingLifeList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<TrainingWheelLife> csvToWheelLife(InputStream is, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingWheelLife> trainingWheelLifeList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingWheelLife trainingWheelLife = new TrainingWheelLife();

                // File Name
                trainingWheelLife.setFilenm(fileName.replace(".csv", ""));

                // Parameters
                trainingWheelLife.setW2x(Double.parseDouble(csvRecord.get("W_2X")));
                trainingWheelLife.setW3x(Double.parseDouble(csvRecord.get("W_3X")));
                trainingWheelLife.setWFault3(Double.parseDouble(csvRecord.get("W_Fault3")));

                // AI-Predict
                trainingWheelLife.setTrip(Integer.parseInt(csvRecord.get("Trip")));

                trainingWheelLifeList.add(trainingWheelLife);
            }
            return trainingWheelLifeList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<TrainingGearboxLife> csvToGearboxLife(InputStream is, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingGearboxLife> trainingGearboxLifeList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingGearboxLife trainingGearboxLife = new TrainingGearboxLife();

                // File Name
                trainingGearboxLife.setFilenm(fileName.replace(".csv", ""));

                // Parameters
                trainingGearboxLife.setGOverallRMS(Double.parseDouble(csvRecord.get("G_OverallRMS")));
                trainingGearboxLife.setGWheel1x(Double.parseDouble(csvRecord.get("G_Wheel1X")));
                trainingGearboxLife.setGWheel2x(Double.parseDouble(csvRecord.get("G_Wheel2X")));
                trainingGearboxLife.setGPinion1x(Double.parseDouble(csvRecord.get("G_Pinion1X")));
                trainingGearboxLife.setGPinion2x(Double.parseDouble(csvRecord.get("G_Pinion2X")));
                trainingGearboxLife.setGGmf1x(Double.parseDouble(csvRecord.get("G_GMF1X")));
                trainingGearboxLife.setGGmf2x(Double.parseDouble(csvRecord.get("G_GMF2X")));

                // AI-Predict
                trainingGearboxLife.setTrip(Integer.parseInt(csvRecord.get("Trip")));

                trainingGearboxLifeList.add(trainingGearboxLife);
            }
            return trainingGearboxLifeList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<TrainingEngineLife> csvToEngineLife(InputStream is, String fileName) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<TrainingEngineLife> trainingEngineLifeList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                TrainingEngineLife trainingEngineLife = new TrainingEngineLife();

                // File Name
                trainingEngineLife.setFilenm(fileName.replace(".csv", ""));

                // Parameters
                trainingEngineLife.setEOverallRms(Double.parseDouble(csvRecord.get("E_OverallRMS")));
                trainingEngineLife.setE12x(Double.parseDouble(csvRecord.get("E_1_2X")));
                trainingEngineLife.setE1X(Double.parseDouble(csvRecord.get("E_1X")));
                trainingEngineLife.setECrestFactor(Double.parseDouble(csvRecord.get("E_CrestFactor")));

                // AI-Predict
                trainingEngineLife.setTrip(Integer.parseInt(csvRecord.get("Trip")));

                trainingEngineLifeList.add(trainingEngineLife);
            }
            return trainingEngineLifeList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
