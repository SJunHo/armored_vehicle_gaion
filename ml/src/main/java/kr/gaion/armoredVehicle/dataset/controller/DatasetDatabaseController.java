package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import kr.gaion.armoredVehicle.database.model.*;
import kr.gaion.armoredVehicle.dataset.dto.DbDataUpdateInput;
import kr.gaion.armoredVehicle.dataset.service.DatasetDatabaseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
    ) {
        System.out.println(partType);
        
        String result = datasetDatabaseService.importCSVtoDatabase(files, partType);
        return files.stream().map(this.datasetDatabaseService::handleUploadFile).collect(Collectors.toList());
    }

    // get labeled data (for training)
    @GetMapping(path = "/api/data/database/get-all-labeled-bearing-data")
    public List<?> getTrainingBearingData(@RequestParam("dataType") String partType) {
        System.out.println("############### getTrainingBearingData controller ###############");
        try {
            return this.datasetDatabaseService.getTrainingBearingData(partType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // TODO: 부품별 메소드 만들기
//    @GetMapping(path = "/api/data/database/get-all-labeled-wheel-data")
//    public List<?> getTrainingWheelData(@RequestParam("dataType") String partType) {
//        try {
//            return this.datasetDatabaseService.getTrainingWheelData(partType);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @GetMapping(path = "/api/data/database/get-all-labeled-gearbox-data")
//    public List<?> getTrainingGearboxData(@RequestParam("dataType") String partType) {
//        try {
//            return this.datasetDatabaseService.getTrainingGearboxData(partType);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @GetMapping(path = "/api/data/database/get-all-labeled-engine-data")
//    public List<?> getTrainingengineData(@RequestParam("dataType") String partType) {
//        try {
//            return this.datasetDatabaseService.getTrainingengineData(partType);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    // get unlabeled data (for predict)
    @GetMapping(path = "/api/data/database/get-all-unlabeled-bearing-data")
    public List<SensorBearing> getUnlabeledBearingData(@RequestParam("dataType") String partType) {
        try {
            return this.datasetDatabaseService.getUnlabeledBearingData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-unlabeled-wheel-data")
    public List<SensorWheel> getUnlabeledWheelData(@RequestParam("dataType") String dataType) {
        try {
            return this.datasetDatabaseService.getUnlabeledWheelData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-unlabeled-gearbox-data")
    public List<SensorGearbox> getUnlabeledGearboxData(@RequestParam("dataType") String dataType) {
        try {
            return this.datasetDatabaseService.getUnlabeledGearboxData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-unlabeled-engine-data")
    public List<SensorEngine> getUnlabeledEngineData(@RequestParam("dataType") String dataType) {
        try {
            return this.datasetDatabaseService.getUnlabeledEngineData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/data/database/get-all-unlabeled-templife-data")
    public List<SensorTempLife> getUnlabeledTempLifeData(@RequestParam("dataType") String dataType) {
        try {
            return this.datasetDatabaseService.getUnlabeledTempLifeData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(path = "/api/data/database/update")
    public String updateData(@RequestBody ArrayList<DbDataUpdateInput> input) throws IOException {
        return this.datasetDatabaseService.updatePredictData(input);
    }
}
