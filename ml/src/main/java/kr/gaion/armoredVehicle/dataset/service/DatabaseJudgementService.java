package kr.gaion.armoredVehicle.dataset.service;

import kr.gaion.armoredVehicle.auth.User;
import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.model.SensorEngine;
import kr.gaion.armoredVehicle.database.model.SensorWheel;
import kr.gaion.armoredVehicle.database.repository.*;
import kr.gaion.armoredVehicle.dataset.dto.DbJudgementUpdateInput;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public String updateUserJudgement(String partType, List<DbJudgementUpdateInput> updateInputs) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getDetails();
        User logUser = (User) obj;
        switch (partType) {
            // bearing
            case "BLB":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserLbsf(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserLbsfId(logUser.getUserid());
                    tempData.setUserLbsfDate(new Date());
                    sensorBearingRepository.save(tempData);
                }

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


