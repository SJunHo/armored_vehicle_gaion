package kr.gaion.armoredVehicle.dataset.controller;

import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.dataset.dto.DbJudgementUpdateInput;
import kr.gaion.armoredVehicle.dataset.service.DatabaseJudgementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController("/api/judgement")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DatabaseJudgementController {
    @NonNull
    private final DatabaseJudgementService databaseJudgementService;

    @GetMapping(path = "/api/judgement/cars")
    public List<String> findDistinctByCarId(
            @RequestParam("partType") String partType
    ) {
        return this.databaseJudgementService.findDistinctByCarId(partType);
    }

    @GetMapping(path = "/api/judgement/models")
    public List<String> findDistinctByModelName(
            @RequestParam("partType") String partType
    ) {
        return this.databaseJudgementService.findDistinctByModelName(partType);
    }


    @PostMapping(path = "/api/judgement/update")
    public String updateUserJudgement(
            @RequestParam("partType") String partType,
            @RequestBody List<DbJudgementUpdateInput> updateInputs
    ) {
        return this.databaseJudgementService.updateUserJudgement(partType, updateInputs);
    }

    //고장예지 작업자 판정
    @GetMapping(path = "/api/judgement/get-bearing-left-ball-predicted-data")
    public List<SensorBearingLeftBallInterface> getBearingLeftBallPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getBearingLeftBallPredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-bearing-left-outside-predicted-data")
    public List<SensorBearingLeftOutsideInterface> getBearingLeftOutsidePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getBearingLeftOutsidePredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-bearing-left-inside-predicted-data")
    public List<SensorBearingLeftInsideInterface> getBearingLeftInsidePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getBearingLeftInsidePredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-bearing-left-retainer-predicted-data")
    public List<SensorBearingLeftRetainerInterface> getBearingLeftRetainerPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getBearingLeftRetainerPredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-bearing-right-ball-predicted-data")
    public List<SensorBearingRightBallInterface> getBearingRightBallPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getBearingRightBallPredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-bearing-right-outside-predicted-data")
    public List<SensorBearingRightOutsideInterface> getBearingRightOutsidePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getBearingRightOutsidePredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-bearing-right-inside-predicted-data")
    public List<SensorBearingRightInsideInterface> getBearingRightInsidePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getBearingRightInsidePredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-bearing-right-retainer-predicted-data")
    public List<SensorBearingRightRetainerInterface> getBearingRightRetainerPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getBearingRightRetainerPredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-wheel-left-predicted-data")
    public List<SensorWheelLeftInterface> getWheelLeftPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getWheelLeftPredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-wheel-right-predicted-data")
    public List<SensorWheelRightInterface> getWheelRightPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getWheelRightPredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-gearbox-predicted-data")
    public List<SensorGearboxInterface> getGearboxPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getGearboxPredictedData(carId, modelName, fromDate, toDate);
    }

    @GetMapping(path = "/api/judgement/get-engine-predicted-data")
    public List<SensorEngineInterface> getEnginePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getEnginePredictedData(carId, modelName, fromDate, toDate);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    //고장예지 결과 조회
    // get BLB's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-left-ball-judged-data")
    public List<SensorBearingLeftBallInterface> getLeftBallUserLBSFData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftBallUserLBSFData(carId, modelName, fromDate, toDate);
    }

    // get BLO's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-left-outside-judged-data")
    public List<SensorBearingLeftOutsideInterface> getLeftOutsideUserLBPFOData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftOutsideUserLBPFOData(carId, modelName, fromDate, toDate);
    }

    // get BLI's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-left-inside-judged-data")
    public List<SensorBearingLeftInsideInterface> getLeftInsideUserLBPFIData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftInsideUserLBPFIData(carId, modelName, fromDate, toDate);
    }

    // get BLR's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-left-retainer-judged-data")
    public List<SensorBearingLeftRetainerInterface> getLeftRetainerUserLFTFData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftRetainerUserLFTFData(carId, modelName, fromDate, toDate);
    }

    // get BRB's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-right-ball-judged-data")
    public List<SensorBearingRightBallInterface> getRightBallUserRBSFData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightBallUserRBSFData(carId, modelName, fromDate, toDate);
    }

    // get BRO's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-right-outside-judged-data")
    public List<SensorBearingRightOutsideInterface> getRightOutsideUserRBPFOData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightOutsideUserRBPFOData(carId, modelName, fromDate, toDate);
    }

    // get BRI's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-right-inside-judged-data")
    public List<SensorBearingRightInsideInterface> getRightInsideUserRBPFIData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightInsideUserRBPFIData(carId, modelName, fromDate, toDate);
    }

    // get BRR's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-right-retainer-judged-data")
    public List<SensorBearingRightRetainerInterface> getRightRetainerUserRFTFData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightRetainerUserRFTFData(carId, modelName, fromDate, toDate);
    }

    // get WL's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-wheel-left-judged-data")
    public List<SensorWheelLeftInterface> getLeftWheelUserLW(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftWheelUserLW(carId, modelName, fromDate, toDate);
    }

    // get WR's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-wheel-right-judged-data")
    public List<SensorWheelRightInterface> getRightWheelUserRW(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightWheelUserRW(carId, modelName, fromDate, toDate);
    }

    // get G's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-gearbox-judged-data")
    public List<SensorGearboxInterface> getGearboxUserGearData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getGearboxUserGearData(carId, modelName, fromDate, toDate);
    }

    // get E's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-engine-judged-data")
    public List<SensorEngineInterface> getEngineUserEngineData(
            @RequestParam("carId") String carId,
            @RequestParam("modelName") String modelName,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getEngineUserEngineData(carId, modelName, fromDate, toDate);
    }
}
