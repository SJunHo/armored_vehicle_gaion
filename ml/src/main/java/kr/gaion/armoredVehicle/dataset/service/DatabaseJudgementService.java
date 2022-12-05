package kr.gaion.armoredVehicle.dataset.service;

import kr.gaion.armoredVehicle.auth.User;
import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.repository.SensorBearingRepository;
import kr.gaion.armoredVehicle.database.repository.SensorEngineRepository;
import kr.gaion.armoredVehicle.database.repository.SensorGearboxRepository;
import kr.gaion.armoredVehicle.database.repository.SensorWheelRepository;
import kr.gaion.armoredVehicle.dataset.dto.DbJudgementUpdateInput;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserLbpfo(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserLbpfoId(logUser.getUserid());
                    tempData.setUserLbpfoDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
            case "BLI":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserLbpfi(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserLbpfiId(logUser.getUserid());
                    tempData.setUserLbpfiDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
            case "BLR":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserLftf(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserLftfId(logUser.getUserid());
                    tempData.setUserLftfDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
            case "BRB":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRbsf(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRbsfId(logUser.getUserid());
                    tempData.setUserRbsfDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
            case "BRO":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRbpfo(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRbpfoId(logUser.getUserid());
                    tempData.setUserRbpfoDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
            case "BRI":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRbpfi(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRbpfiId(logUser.getUserid());
                    tempData.setUserRbpfiDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
            case "BRR":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRftf(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRftfId(logUser.getUserid());
                    tempData.setUserRftfDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
                // wheel
            case "WL":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorWheelRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserLw(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserLwId(logUser.getUserid());
                    tempData.setUserLwDate(new Date());
                    sensorWheelRepository.save(tempData);
                }
            case "WR":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorWheelRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRw(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRwId(logUser.getUserid());
                    tempData.setUserRwDate(new Date());
                    sensorWheelRepository.save(tempData);
                }
                // gearbox
            case "G":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorGearboxRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserGear(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserGearId(logUser.getUserid());
                    tempData.setUserGearDate(new Date());
                    sensorGearboxRepository.save(tempData);
                }
                // engine
            case "E":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorEngineRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserEngine(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserEngineId(logUser.getUserid());
                    tempData.setUserEngineDate(new Date());
                    sensorEngineRepository.save(tempData);
                }
        }
        return null;
    }

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

    // get BLB's User judgement values are not Null data
    public List<SensorBearingLeftBallInterface> getLeftBallUserLBSFData(String carId, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftBallUserLBSFData(carId, fromDate, toDate);
    }

    // get BLO's User judgement values are not Null data
    public List<SensorBearingLeftOutsideInterface> getLeftOutsideUserLBPFOData(String carId, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftOutsideUserLBPFOData(carId, fromDate, toDate);
    }

    // get BLI's User judgement values are not Null data
    public List<SensorBearingLeftInsideInterface> getLeftInsideUserLBPFIData(String carId, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftInsideUserLBPFIData(carId, fromDate, toDate);
    }

    // get BLR's User judgement values are not Null data
    public List<SensorBearingLeftRetainerInterface> getLeftRetainerUserLFTFData(String carId, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftRetainerUserLFTFData(carId, fromDate, toDate);
    }

    // get BRB's User judgement values are not Null data
    public List<SensorBearingRightBallInterface> getRightBallUserRBSFData(String carId, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightBallUserRBSFData(carId, fromDate, toDate);
    }

    // get BRO's User judgement values are not Null data
    public List<SensorBearingRightOutsideInterface> getRightOutsideUserRBPFOData(String carId, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightOutsideUserRBPFOData(carId, fromDate, toDate);
    }

    // get BRI's User judgement values are not Null data
    public List<SensorBearingRightInsideInterface> getRightInsideUserRBPFIData(String carId, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightInsideUserRBPFIData(carId, fromDate, toDate);
    }

    // get BRR's User judgement values are not Null data
    public List<SensorBearingRightRetainerInterface> getRightRetainerUserRFTFData(String carId, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightRetainerUserRFTFData(carId, fromDate, toDate);
    }

    // get WL's User judgement values are not Null data
    public List<SensorWheelLeftInterface> getLeftWheelUserLW(String carId, Date fromDate, Date toDate) {
        return sensorWheelRepository.getLeftWheelUserLW(carId, fromDate, toDate);
    }

    // get WR's User judgement values are not Null data
    public List<SensorWheelRightInterface> getRightWheelUserRW(String carId, Date fromDate, Date toDate) {
        return sensorWheelRepository.getRightWheelUserRW(carId, fromDate, toDate);
    }

    // get G's User judgement values are not Null data
    public List<SensorGearboxInterface> getGearboxUserGearData(String carId, Date fromDate, Date toDate) {
        return sensorGearboxRepository.getGearboxUserGearData(carId, fromDate, toDate);
    }

    // get E's User judgement values are not Null data
    public List<SensorEngineInterface> getEngineUserEngineData(String carId, Date fromDate, Date toDate) {
        return sensorEngineRepository.getEngineUserEngineData(carId, fromDate, toDate);
    }
}


