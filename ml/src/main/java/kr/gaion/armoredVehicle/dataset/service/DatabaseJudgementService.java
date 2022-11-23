package kr.gaion.armoredVehicle.dataset.service;

import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.model.SensorEngine;
import kr.gaion.armoredVehicle.database.model.SensorWheel;
import kr.gaion.armoredVehicle.database.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j
public class DatabaseJudgementService {
    @NonNull
    private final SensorBearingRepository sensorBearingRepository;
    @NonNull
    private final SensorWheelRepository sensorWheelRepository;
    @NonNull
    private final SensorGearboxRepository sensorGearboxRepository;
    @NonNull
    private final SensorEngineRepository sensorEngineRepository;

    public String findClassLabel(String part) {
        switch (part) {
            // bearing
            case "BLB":
                return "AI_LBSF";
            case "BLO":
                return "AI_LBPFO";
            case "BLI":
                return "AI_LBPFI";
            case "BLR":
                return "AI_LFTF";
            case "BRB":
                return "AI_RBSF";
            case "BRO":
                return "AI_RBPFO";
            case "BRI":
                return "AI_RBPFI";
            case "BRR":
                return "AI_RFTF";
            // wheel
            case "WL":
                return "AI_LW";
            case "WR":
                return "AI_RW";
            // gearbox
            case "G":
                return "AI_GEAR";
            // engine
            case "E":
                return "AI_ENGINE";
        }
        return null;
    }

//    public String findColumns(String part) {
//        switch (part) {
//            // bearing
//            case "BLB":
//                return "BERDATA.IDX, BERDATA.AI_LBSF, BERDATA.AI_LBSF_ALGO, BERDATA.AI_LBSF_MODEL, BERDATA.AI_LBSF_DATE, " +
//                        "BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BSF, BERDATA.L_B_V_32924BSF, BERDATA.L_B_V_32922BSF, " +
//                        "BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
//                        "ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` ";
//            case "BLO":
//                return "BERDATA.IDX, BERDATA.AI_LBPFO, BERDATA.AI_LBPFO_ALGO, BERDATA.AI_LBPFO_MODEL, BERDATA.AI_LBPFO_DATE, " +
//                        "BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BPFO, BERDATA.L_B_V_32924BPFO, BERDATA.L_B_V_32922BPFO, " +
//                        "BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
//                        "ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` ";
//            case "BLI":
//                return "BERDATA.IDX, BERDATA.AI_LBPFI, BERDATA.AI_LBPFI_ALGO, BERDATA.AI_LBPFI_MODEL, BERDATA.AI_LBPFI_DATE, " +
//                        "BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BPFI, BERDATA.L_B_V_32924BPFI, BERDATA.L_B_V_32922BPFI, " +
//                        "BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
//                        "ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` ";
//            case "BLR":
//                return "BERDATA.IDX, BERDATA.AI_LFTF, BERDATA.AI_LFTF_ALGO, BERDATA.AI_LFTF_MODEL, BERDATA.AI_LFTF_DATE, " +
//                        " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912FTF, BERDATA.L_B_V_32924FTF, BERDATA.L_B_V_32922FTF, " +
//                        " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
//                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` ";
//            case "BRB":
//                return "BERDATA.IDX, BERDATA.AI_RBSF, BERDATA.AI_RBSF_ALGO, BERDATA.AI_RBSF_MODEL, BERDATA.AI_RBSF_DATE, " +
//                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BSF, BERDATA.R_B_V_32924BSF, BERDATA.R_B_V_32922BSF, " +
//                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
//                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` ";
//            case "BRO":
//                return "BERDATA.IDX, BERDATA.AI_RBPFO, BERDATA.AI_RBPFO_ALGO, BERDATA.AI_RBPFO_MODEL, BERDATA.AI_RBPFO_DATE, " +
//                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BPFO, BERDATA.R_B_V_32924BPFO, BERDATA.R_B_V_32922BPFO, " +
//                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
//                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` ";
//            case "BRI":
//                return "BERDATA.IDX, BERDATA.AI_RBPFI, BERDATA.AI_RBPFI_ALGO, BERDATA.AI_RBPFI_MODEL, BERDATA.AI_RBPFI_DATE, " +
//                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BPFI, BERDATA.R_B_V_32924BPFI, BERDATA.R_B_V_32922BPFI, " +
//                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
//                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` ";
//            case "BRR":
//                return "BERDATA.IDX, BERDATA.AI_RFTF, BERDATA.AI_RFTF_ALGO, BERDATA.AI_RFTF_MODEL, BERDATA.AI_RFTF_DATE, " +
//                        " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912FTF, BERDATA.R_B_V_32924FTF, BERDATA.R_B_V_32922FTF, " +
//                        " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
//                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` ";
//            // wheel
//            case "WL":
//                return "WHLDATA.IDX, WHLDATA.AI_LW, WHLDATA.AI_LW_ALGO, WHLDATA.AI_LW_MODEL, WHLDATA.AI_LW_DATE, " +
//                        " WHLDATA.W_RPM, WHLDATA.L_W_V_2X, WHLDATA.L_W_V_3X, WHLDATA.L_W_S_Fault3, " +
//                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` ";
//            case "WR":
//                return "WHLDATA.IDX, WHLDATA.AI_RW, WHLDATA.AI_RW_ALGO, WHLDATA.AI_RW_MODEL, WHLDATA.AI_RW_DATE, " +
//                        " WHLDATA.W_RPM, WHLDATA.R_W_V_2X, WHLDATA.R_W_V_3X, WHLDATA.R_W_S_Fault3, " +
//                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` ";
//            // gearbox
//            case "G":
//                return "GRBDATA.IDX, GRBDATA.AI_GEAR, GRBDATA.AI_GEAR_ALGO, GRBDATA.AI_GEAR_MODEL, GRBDATA.AI_GEAR_DATE, " +
//                        " GRBDATA.W_RPM, GRBDATA.G_V_OverallRMS, GRBDATA.G_V_Wheel1X, GRBDATA.G_V_Wheel2X, " +
//                        " GRBDATA.G_V_Pinion1X, GRBDATA.G_V_Pinion2X, GRBDATA.G_V_GMF1X, GRBDATA.G_V_GMF2X, " +
//                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, GRBDATA.`DATE` ";
//            // engine
//            case "E":
//                return "ENGDATA.IDX, ENGDATA.AI_ENGINE, ENGDATA.AI_ENGINE_ALGO, ENGDATA.AI_ENGINE_MODEL, ENGDATA.AI_ENGINE_DATE, " +
//                        " ENGDATA.W_RPM, ENGDATA.E_V_OverallRMS, ENGDATA.E_V_1_2X, ENGDATA.E_V_1X, ENGDATA.E_V_Crestfactor, " +
//                        " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, ENGDATA.`DATE` ";
//        }
//        return null;
//    }

    public List<String> findDistinctByCarId(String partType) {
        var targetColumn = findClassLabel(partType);
        var componentType = partType.substring(0, 1);
        System.out.println(componentType);

        List<String> result = new ArrayList<>();
        switch (componentType) {
            case "B":
                return sensorBearingRepository.findDistinctByCarId(targetColumn);
            case "W":
                return sensorWheelRepository.findDistinctByCarId(targetColumn);
            case "E":
                return sensorEngineRepository.findDistinctByCarId(targetColumn);
            case "G":
                return sensorGearboxRepository.findDistinctByCarId(targetColumn);
        }
        return result;
    }

    ////////////////////////////////////////////////////

    public Page<SensorBearingLeftBallInterface> getBearingLeftBallPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorBearingRepository.getLeftBallAiLBSFPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorBearingLeftOutsideInterface> getBearingLeftOutsidePredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorBearingRepository.getLeftOutsideAiLBPFOPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorBearingLeftInsideInterface> getBearingLeftInsidePredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorBearingRepository.getLeftInsideAiLBPFIPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorBearingLeftRetainerInterface> getBearingLeftRetainerPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorBearingRepository.getLeftRetainerAiLFTFPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorBearingRightBallInterface> getBearingRightBallPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorBearingRepository.getRightBallAiRBSFPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorBearingRightOutsideInterface> getBearingRightOutsidePredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorBearingRepository.getRightOutsideAiRBPFOPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorBearingRightInsideInterface> getBearingRightInsidePredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorBearingRepository.getRightInsideAiRBPFIPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorBearingRightRetainerInterface> getBearingRightRetainerPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorBearingRepository.getRightRetainerAiRFTFPredictedData(carId, fromDate, toDate, pageable);
    }

    ///////////////////////////////////////////////////////

    public Page<SensorWheelLeftInterface> getWheelLeftPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorWheelRepository.getLeftWheelAiLWPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorWheelRightInterface> getWheelRightPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorWheelRepository.getRightWheelAiRWPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorGearboxInterface> getGearboxPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorGearboxRepository.getGearboxAiGearPredictedData(carId, fromDate, toDate, pageable);
    }

    public Page<SensorEngineInterface> getEnginePredictedData(String carId, Date fromDate, Date toDate, Pageable pageable) {
        return sensorEngineRepository.getEngineAiEnginePredictedData(carId, fromDate, toDate, pageable);
    }

}


