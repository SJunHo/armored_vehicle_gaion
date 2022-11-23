package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.gaion.armoredVehicle.database.dto.SensorWheelLeftInterface;
import kr.gaion.armoredVehicle.database.dto.SensorWheelRightInterface;
import kr.gaion.armoredVehicle.database.dto.WheelInput;
import kr.gaion.armoredVehicle.database.model.SensorWheel;
import kr.gaion.armoredVehicle.dataset.service.DatabaseJudgementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-bearing-predicted-data")
    public Page<?> getPredictedData(
            @RequestParam("partType") String partType,
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getPredictedData(partType, carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-wheel-left-predicted-data")
    public Page<SensorWheelLeftInterface> getWheelLeftPredictedData(
            @RequestParam("partType") String partType,
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getWheelLeftPredictedData(partType, carId, fromDate, toDate, pageable);
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/judgement/get-wheel-right-predicted-data")
    public Page<SensorWheelRightInterface> getWheelRightPredictedData(
            @RequestParam("partType") String partType,
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.databaseJudgementService.getWheelRightPredictedData(partType, carId, fromDate, toDate, pageable);
    }

}
