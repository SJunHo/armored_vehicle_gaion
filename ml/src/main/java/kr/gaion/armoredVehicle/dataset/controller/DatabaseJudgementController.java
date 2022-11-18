package kr.gaion.armoredVehicle.dataset.controller;

import kr.gaion.armoredVehicle.dataset.service.DatabaseJudgementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

    @GetMapping(path = "/api/judgement/data")
    public List<?> getPredictedData(
            @RequestParam("partType") String partType,
            @RequestParam("carId") String carId,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        return this.databaseJudgementService.getPredictedData(partType, carId, fromDate, toDate);
    }
}
