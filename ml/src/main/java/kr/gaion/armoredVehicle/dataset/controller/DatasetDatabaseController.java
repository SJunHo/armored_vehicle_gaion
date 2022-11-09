package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import kr.gaion.armoredVehicle.database.model.SensorTempLife;
import kr.gaion.armoredVehicle.dataset.dto.DbDataUpdateInput;
import kr.gaion.armoredVehicle.dataset.service.DatasetDatabaseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
            @RequestParam("files") List<MultipartFile> files,
            @PathVariable("partType") String partType
    ) throws IOException {
        String result = datasetDatabaseService.importCSVtoDatabase(files, partType);

        System.out.println("import CSV to Database result : " + result);

        return files.stream().map(this.datasetDatabaseService::handleUploadFile).collect(Collectors.toList());
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

    // get unlabeled data (for predict)
    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-bearing-data")
    public Page<?> getUnlabeledBearingData(@RequestParam("partType") String partType, @Parameter(hidden = true) Pageable pageable) {
        try {
            return this.datasetDatabaseService.getUnlabeledBearingData(partType, pageable);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-wheel-data")
    public Page<?> getUnlabeledWheelData(@RequestParam("partType") String partType, @Parameter(hidden = true) Pageable pageable) {
        try {
            return this.datasetDatabaseService.getUnlabeledWheelData(partType, pageable);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-gearbox-data")
    public Page<?> getUnlabeledGearboxData(@RequestParam("partType") String partType, @Parameter(hidden = true) Pageable pageable) {
        try {
            return this.datasetDatabaseService.getUnlabeledGearboxData(pageable);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-engine-data")
    public Page<?> getUnlabeledEngineData(@RequestParam("partType") String partType, @Parameter(hidden = true) Pageable pageable) {
        try {
            return this.datasetDatabaseService.getUnlabeledEngineData(pageable);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PageableAsQueryParam
    @GetMapping(path = "/api/data/database/get-all-unlabeled-templife-data")
    public Page<SensorTempLife> getUnlabeledTempLifeData(@RequestParam("partType") String partType, @Parameter(hidden = true) Pageable pageable) {
        try {
            return this.datasetDatabaseService.getUnlabeledTempLifeData(pageable);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(path = "/api/data/database/update")
    public String updateData(@RequestBody ArrayList<DbDataUpdateInput> input) {
        return this.datasetDatabaseService.updatePredictData(input);
    }
}
