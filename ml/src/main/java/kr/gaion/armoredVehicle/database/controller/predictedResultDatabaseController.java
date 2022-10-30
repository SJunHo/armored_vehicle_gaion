package kr.gaion.armoredVehicle.database.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.gaion.armoredVehicle.database.dto.DBDataUpdateInput;
import kr.gaion.armoredVehicle.database.dto.SensorPredictedData;
import kr.gaion.armoredVehicle.database.dto.SensorSavedData;
import kr.gaion.armoredVehicle.database.model.SensorBearing;
import kr.gaion.armoredVehicle.database.service.PredictedResultDatabaseService;
import kr.gaion.armoredVehicle.dataset.service.DataLookupService;
import kr.gaion.armoredVehicle.ml.dto.SensorBearingData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController("/api/data/predicted-data")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class predictedResultDatabaseController {
    @NonNull private PredictedResultDatabaseService predictedResultDatabaseService;
    @NonNull private DataLookupService dataLookupService;

    @PostMapping(path = "/get-sensor-data")
    public SensorPredictedData getSensorData (@RequestParam("part") String partType, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, @Parameter(hidden = true) Pageable pageable) {
        return predictedResultDatabaseService.getSensorDataNotNull(partType, fromDate, toDate, pageable);
    }

    @PostMapping(path = "/get-sda-id-list")
    public List<String> getSdaList() {
        return predictedResultDatabaseService.getSdaList();
    }

//    @PostMapping(path = "/api/update-data")
//    public SensorSavedData updateSensorData(@RequestBody List<DBDataUpdateInput> input) throws IOException {
//        return this.predictedResultDatabaseService.updateSensorData(input);
//    }
}
