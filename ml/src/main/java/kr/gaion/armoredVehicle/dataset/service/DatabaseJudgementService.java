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

    public static String findClassLabel(String part) {
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

    public List<String> findDistinctByModelName(String partType) {
        switch (partType) {
            case "BLB":
                return sensorBearingRepository.findDistinctByLBSFModel();
            case "BLO":
                return sensorBearingRepository.findDistinctByLBPFOModel();
            case "BLI":
                return sensorBearingRepository.findDistinctByLBSFIModel();
            case "BLR":
                return sensorBearingRepository.findDistinctByLFTFModel();
            case "BRB":
                return sensorBearingRepository.findDistinctByRBSFModel();
            case "BRO":
                return sensorBearingRepository.findDistinctByRBPFOModel();
            case "BRI":
                return sensorBearingRepository.findDistinctByRBSFIModel();
            case "BRR":
                return sensorBearingRepository.findDistinctByRFTFModel();
            // wheel
            case "WL":
                return sensorWheelRepository.findDistinctByLWModel();
            case "WR":
                return sensorWheelRepository.findDistinctByRWModel();
            // gearbox
            case "G":
                return sensorGearboxRepository.findDistinctByGEARModel();
            // engine
            case "E":
                return sensorEngineRepository.findDistinctByENGINEModel();
        }
        return null;
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
                break;
            case "BLO":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserLbpfo(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserLbpfoId(logUser.getUserid());
                    tempData.setUserLbpfoDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
                break;
            case "BLI":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserLbpfi(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserLbpfiId(logUser.getUserid());
                    tempData.setUserLbpfiDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
                break;
            case "BLR":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserLftf(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserLftfId(logUser.getUserid());
                    tempData.setUserLftfDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
                break;
            case "BRB":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRbsf(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRbsfId(logUser.getUserid());
                    tempData.setUserRbsfDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
                break;
            case "BRO":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRbpfo(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRbpfoId(logUser.getUserid());
                    tempData.setUserRbpfoDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
                break;
            case "BRI":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRbpfi(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRbpfiId(logUser.getUserid());
                    tempData.setUserRbpfiDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
                break;
            case "BRR":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorBearingRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRftf(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRftfId(logUser.getUserid());
                    tempData.setUserRftfDate(new Date());
                    sensorBearingRepository.save(tempData);
                }
                break;
            // wheel
            case "WL":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorWheelRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserLw(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserLwId(logUser.getUserid());
                    tempData.setUserLwDate(new Date());
                    sensorWheelRepository.save(tempData);
                }
                break;
            case "WR":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorWheelRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserRw(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserRwId(logUser.getUserid());
                    tempData.setUserRwDate(new Date());
                    sensorWheelRepository.save(tempData);
                }
                break;
            // gearbox
            case "G":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorGearboxRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserGear(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserGearId(logUser.getUserid());
                    tempData.setUserGearDate(new Date());
                    sensorGearboxRepository.save(tempData);
                }
                break;
            // engine
            case "E":
                for (DbJudgementUpdateInput dbJudgementUpdateInput : updateInputs) {
                    var tempData = sensorEngineRepository.findById(dbJudgementUpdateInput.getIdx()).get();
                    tempData.setUserEngine(dbJudgementUpdateInput.getUserJudgement());
                    tempData.setUserEngineId(logUser.getUserid());
                    tempData.setUserEngineDate(new Date());
                    sensorEngineRepository.save(tempData);
                }
                break;
        }
        return null;
    }

    public List<SensorBearingLeftBallInterface> getBearingLeftBallPredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftBallAiLBSFPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorBearingLeftOutsideInterface> getBearingLeftOutsidePredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftOutsideAiLBPFOPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorBearingLeftInsideInterface> getBearingLeftInsidePredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftInsideAiLBPFIPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorBearingLeftRetainerInterface> getBearingLeftRetainerPredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftRetainerAiLFTFPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorBearingRightBallInterface> getBearingRightBallPredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightBallAiRBSFPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorBearingRightOutsideInterface> getBearingRightOutsidePredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightOutsideAiRBPFOPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorBearingRightInsideInterface> getBearingRightInsidePredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightInsideAiRBPFIPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorBearingRightRetainerInterface> getBearingRightRetainerPredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightRetainerAiRFTFPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorWheelLeftInterface> getWheelLeftPredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorWheelRepository.getLeftWheelAiLWPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorWheelRightInterface> getWheelRightPredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorWheelRepository.getRightWheelAiRWPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorGearboxInterface> getGearboxPredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorGearboxRepository.getGearboxAiGearPredictedData(carId, modelName, fromDate, toDate);
    }

    public List<SensorEngineInterface> getEnginePredictedData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorEngineRepository.getEngineAiEnginePredictedData(carId, modelName, fromDate, toDate);
    }

    // get BLB's User judgement values are not Null data
    public List<SensorBearingLeftBallInterface> getLeftBallUserLBSFData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftBallUserLBSFData(carId, modelName, fromDate, toDate);
    }

    // get BLO's User judgement values are not Null data
    public List<SensorBearingLeftOutsideInterface> getLeftOutsideUserLBPFOData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftOutsideUserLBPFOData(carId, modelName, fromDate, toDate);
    }

    // get BLI's User judgement values are not Null data
    public List<SensorBearingLeftInsideInterface> getLeftInsideUserLBPFIData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftInsideUserLBPFIData(carId, modelName, fromDate, toDate);
    }

    // get BLR's User judgement values are not Null data
    public List<SensorBearingLeftRetainerInterface> getLeftRetainerUserLFTFData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getLeftRetainerUserLFTFData(carId, modelName, fromDate, toDate);
    }

    // get BRB's User judgement values are not Null data
    public List<SensorBearingRightBallInterface> getRightBallUserRBSFData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightBallUserRBSFData(carId, modelName, fromDate, toDate);
    }

    // get BRO's User judgement values are not Null data
    public List<SensorBearingRightOutsideInterface> getRightOutsideUserRBPFOData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightOutsideUserRBPFOData(carId, modelName, fromDate, toDate);
    }

    // get BRI's User judgement values are not Null data
    public List<SensorBearingRightInsideInterface> getRightInsideUserRBPFIData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightInsideUserRBPFIData(carId, modelName, fromDate, toDate);
    }

    // get BRR's User judgement values are not Null data
    public List<SensorBearingRightRetainerInterface> getRightRetainerUserRFTFData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorBearingRepository.getRightRetainerUserRFTFData(carId, modelName, fromDate, toDate);
    }

    // get WL's User judgement values are not Null data
    public List<SensorWheelLeftInterface> getLeftWheelUserLW(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorWheelRepository.getLeftWheelUserLW(carId, modelName, fromDate, toDate);
    }

    // get WR's User judgement values are not Null data
    public List<SensorWheelRightInterface> getRightWheelUserRW(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorWheelRepository.getRightWheelUserRW(carId, modelName, fromDate, toDate);
    }

    // get G's User judgement values are not Null data
    public List<SensorGearboxInterface> getGearboxUserGearData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorGearboxRepository.getGearboxUserGearData(carId, modelName, fromDate, toDate);
    }

    // get E's User judgement values are not Null data
    public List<SensorEngineInterface> getEngineUserEngineData(String carId, String modelName, Date fromDate, Date toDate) {
        return sensorEngineRepository.getEngineUserEngineData(carId, modelName, fromDate, toDate);
    }
}


