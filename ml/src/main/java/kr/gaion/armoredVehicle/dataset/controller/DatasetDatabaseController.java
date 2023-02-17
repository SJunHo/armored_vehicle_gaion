package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import kr.gaion.armoredVehicle.dataset.dto.DbDataUpdateInput;
import kr.gaion.armoredVehicle.dataset.service.DatasetDatabaseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController("/api/data/database")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DatasetDatabaseController {
    @NonNull
    private final DatasetDatabaseService datasetDatabaseService;

    @PostMapping(path = "/api/data/database/upload-dataset-file-to-database/{partType}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<String> uploadCSVFileAndImportDB(
            @Parameter(
                    description = "Files to be uploaded",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)  // Won't work without OCTET_STREAM as the mediaType.
            )
            @RequestBody List<MultipartFile> files,
            @PathVariable("partType") String partType
    ) throws IOException {
        System.out.println("start to import CSV to Database");

        String result = datasetDatabaseService.importCSVtoDatabase(files, partType);

        System.out.println("import CSV to Database result : " + result);

        return files.stream().map(this.datasetDatabaseService::handleUploadFile).collect(Collectors.toList());
    }
    
    @GetMapping(path = "/api/data/database/life/cars")
    public List<String> findDistinctByCarIdFromLifeData(
            @RequestParam("partType") String partType
    ) {
        return this.datasetDatabaseService.findDistinctByCarIdFromLifeData(partType);
    }


    // get labeled data (for training)
    @GetMapping(path = "/api/data/database/get-all-labeled-bearing-data")
    public List<?> getTrainingBearingData(@RequestParam("partType") String partType) {
        try {
            return this.datasetDatabaseService.getTrainingBearingData(partType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-labeled-wheel-data")
    public List<?> getTrainingWheelData(@RequestParam("partType") String partType) {
        try {
            return this.datasetDatabaseService.getTrainingWheelData(partType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-labeled-gearbox-data")
    public List<?> getTrainingGearboxData() {
        try {
            return this.datasetDatabaseService.getTrainingGearboxData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-labeled-engine-data")
    public List<?> getTrainingEngineData() {
        try {
            return this.datasetDatabaseService.getTrainingEngineData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // get labeled remaining life data (for training)
    @GetMapping(path = "/api/data/database/get-all-labeled-bearing-life-data")
    public List<?> getTrainingBearingLifeData() {
        try {
            return this.datasetDatabaseService.getTrainingBearingLifeData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-labeled-wheel-life-data")
    public List<?> getTrainingWheelLifeData() {
        try {
            return this.datasetDatabaseService.getTrainingWheelLifeData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-labeled-gearbox-life-data")
    public List<?> getTrainingGearboxLifeData() {
        try {
            return this.datasetDatabaseService.getTrainingGearboxLifeData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-labeled-engine-life-data")
    public List<?> getTrainingEngineLifeData() {
        try {
            return this.datasetDatabaseService.getTrainingEngineLifeData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // get unlabeled data (for predict)
    @GetMapping(path = "/api/data/database/get-all-unlabeled-bearing-data")
    public List<?> getUnlabeledBearingData(
            @RequestParam("carId") String carId,
            @RequestParam("partType") String partType,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        try {
            return this.datasetDatabaseService.getUnlabeledBearingData(carId, partType, fromDate, toDate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-wheel-data")
    public List<?> getUnlabeledWheelData(
            @RequestParam("carId") String carId,
            @RequestParam("partType") String partType,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        try {
            return this.datasetDatabaseService.getUnlabeledWheelData(carId, partType, fromDate, toDate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-gearbox-data")
    public List<?> getUnlabeledGearboxData(
            @RequestParam("carId") String carId,
            @RequestParam("partType") String partType,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        try {
            return this.datasetDatabaseService.getUnlabeledGearboxData(carId, fromDate, toDate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-engine-data")
    public List<?> getUnlabeledEngineData(
            @RequestParam("carId") String carId,
            @RequestParam("partType") String partType,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        try {
            return this.datasetDatabaseService.getUnlabeledEngineData(carId, fromDate, toDate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // get unlabeled remaining life data (for predict)
    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-bearing-life-data")
    public List<?> getUnlabeledBearingLifeData(
            @RequestParam("carId") String carId,
            @RequestParam("partType") String partType,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        try {
            return this.datasetDatabaseService.getUnlabeledBearingLifeData(carId, fromDate, toDate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-wheel-life-data")
    public List<?> getUnlabeledWheelLifeData(
            @RequestParam("carId") String carId,
            @RequestParam("partType") String partType,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        try {
            return this.datasetDatabaseService.getUnlabeledWheelLifeData(carId, fromDate, toDate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-gearbox-life-data")
    public List<?> getUnlabeledGearboxLifeData(
            @RequestParam("carId") String carId,
            @RequestParam("partType") String partType,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        try {
            return this.datasetDatabaseService.getUnlabeledGearboxLifeData(carId, fromDate, toDate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-engine-life-data")
    public List<?> getUnlabeledEngineLifeData(
            @RequestParam("carId") String carId,
            @RequestParam("partType") String partType,
            @RequestParam(value = "from-date", required = false) Date fromDate,
            @RequestParam(value = "to-date", required = false) Date toDate
    ) {
        try {
            return this.datasetDatabaseService.getUnlabeledEngineLifeData(carId, fromDate, toDate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(path = "/api/data/database/update")
    public String updateData(@RequestBody List<DbDataUpdateInput> input) {
        System.out.println(input);
        return this.datasetDatabaseService.updatePredictData(input);
    }
}
