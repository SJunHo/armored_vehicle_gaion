package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.dataset.dto.DbJudgementUpdateInput;
import kr.gaion.armoredVehicle.dataset.service.DatabaseJudgementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping(path = "/api/judgement/update")
    public String updateUserJudgement(
            @RequestParam("partType") String partType,
            @RequestBody List<DbJudgementUpdateInput> updateInputs
    ) {
        return this.databaseJudgementService.updateUserJudgement(partType, updateInputs);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-bearing-left-ball-predicted-data")
    public Page<SensorBearingLeftBallInterface> getBearingLeftBallPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getBearingLeftBallPredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-bearing-left-outside-predicted-data")
    public Page<SensorBearingLeftOutsideInterface> getBearingLeftOutsidePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getBearingLeftOutsidePredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-bearing-left-inside-predicted-data")
    public Page<SensorBearingLeftInsideInterface> getBearingLeftInsidePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getBearingLeftInsidePredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-bearing-left-retainer-predicted-data")
    public Page<SensorBearingLeftRetainerInterface> getBearingLeftRetainerPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getBearingLeftRetainerPredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-bearing-right-ball-predicted-data")
    public Page<SensorBearingRightBallInterface> getBearingRightBallPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getBearingRightBallPredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-bearing-right-outside-predicted-data")
    public Page<SensorBearingRightOutsideInterface> getBearingRightOutsidePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getBearingRightOutsidePredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-bearing-right-inside-predicted-data")
    public Page<SensorBearingRightInsideInterface> getBearingRightInsidePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getBearingRightInsidePredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-bearing-right-retainer-predicted-data")
    public Page<SensorBearingRightRetainerInterface> getBearingRightRetainerPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getBearingRightRetainerPredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-wheel-left-predicted-data")
    public Page<SensorWheelLeftInterface> getWheelLeftPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getWheelLeftPredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-wheel-right-predicted-data")
    public Page<SensorWheelRightInterface> getWheelRightPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getWheelRightPredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-gearbox-predicted-data")
    public Page<SensorGearboxInterface> getGearboxPredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getGearboxPredictedData(carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-engine-predicted-data")
    public Page<SensorEngineInterface> getEnginePredictedData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getEnginePredictedData(carId, fromDate, toDate, pageable);
    }

    // get BLB's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-left-ball-judged-data")
    public List<SensorBearingLeftBallInterface> getLeftBallUserLBSFData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftBallUserLBSFData(carId, fromDate, toDate);
    }

    // get BLO's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-left-outside-judged-data")
    public List<SensorBearingLeftOutsideInterface> getLeftOutsideUserLBPFOData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftOutsideUserLBPFOData(carId, fromDate, toDate);
    }

    // get BLI's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-left-inside-judged-data")
    public List<SensorBearingLeftInsideInterface> getLeftInsideUserLBPFIData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftInsideUserLBPFIData(carId, fromDate, toDate);
    }

    // get BLR's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-left-retainer-judged-data")
    public List<SensorBearingLeftRetainerInterface> getLeftRetainerUserLFTFData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftRetainerUserLFTFData(carId, fromDate, toDate);
    }

    // get BRB's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-right-ball-judged-data")
    public List<SensorBearingRightBallInterface> getRightBallUserRBSFData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightBallUserRBSFData(carId, fromDate, toDate);
    }

    // get BRO's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-right-outside-judged-data")
    public List<SensorBearingRightOutsideInterface> getRightOutsideUserRBPFOData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightOutsideUserRBPFOData(carId, fromDate, toDate);
    }

    // get BRI's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-right-inside-judged-data")
    public List<SensorBearingRightInsideInterface> getRightInsideUserRBPFIData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightInsideUserRBPFIData(carId, fromDate, toDate);
    }

    // get BRR's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-bearing-right-retainer-judged-data")
    public List<SensorBearingRightRetainerInterface> getRightRetainerUserRFTFData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightRetainerUserRFTFData(carId, fromDate, toDate);
    }

    // get WL's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-wheel-left-judged-data")
    public List<SensorWheelLeftInterface> getLeftWheelUserLW(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getLeftWheelUserLW(carId, fromDate, toDate);
    }

    // get WR's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-wheel-right-judged-data")
    public List<SensorWheelRightInterface> getRightWheelUserRW(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getRightWheelUserRW(carId, fromDate, toDate);
    }

    // get G's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-gearbox-judged-data")
    public List<SensorGearboxInterface> getGearboxUserGearData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getGearboxUserGearData(carId, fromDate, toDate);
    }

    // get E's User judgement values are not Null data
    @GetMapping(path = "/api/judgement/get-engine-judged-data")
    public List<SensorEngineInterface> getEngineUserEngineData(
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getEngineUserEngineData(carId, fromDate, toDate);
    }
}
