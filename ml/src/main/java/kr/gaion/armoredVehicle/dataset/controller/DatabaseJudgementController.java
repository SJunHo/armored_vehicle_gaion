package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.model.SensorWheel;
import kr.gaion.armoredVehicle.dataset.dto.DbJudgementUpdateInput;
import kr.gaion.armoredVehicle.dataset.service.DatabaseJudgementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    ////////////////////////////////////////////////

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


}
