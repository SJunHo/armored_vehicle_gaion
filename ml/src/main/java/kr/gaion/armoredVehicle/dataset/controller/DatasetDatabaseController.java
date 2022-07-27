package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import kr.gaion.armoredVehicle.database.model.SensorBearing;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController("/api/data/database")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DatasetDatabaseController {
    @NonNull private final DatasetDatabaseService datasetDatabaseService;

    @PostMapping(path="/api/data/database/upload-dataset-file-to-database", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<String> uploadCSVFileAndImportDB(
            @Parameter(
                description = "Files to be uploaded",
                content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)  // Won't work without OCTET_STREAM as the mediaType.
            )
            @RequestParam("files") List<MultipartFile> files
    ) {
        String result = datasetDatabaseService.importCSVtoDatabase(files, "bearing");
        return files.stream().map(this.datasetDatabaseService::handleUploadFile).collect(Collectors.toList());
    }


  @GetMapping(path = "/api/data/database/get-all-bearing-data")
  public Page<SensorBearing> getUnlabeledBearingData(@RequestParam("dataType") String dataType, @Parameter(hidden = true) Pageable pageable) {
    try {
      return this.datasetDatabaseService.getUnlabeledBearingData(pageable);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
//  @GetMapping(path = "/api/data/database/get-all-wheel-data")
//  public Page<SensorBearing> getUnlabeledWheelData(@RequestParam("dataType") String dataType, @Parameter(hidden = true) Pageable pageable) {
//    try {
//      return this.datasetDatabaseService.getUnlabeledWheelData(pageable);
//    } catch (IOException e) {
//      e.printStackTrace();
//      return null;
//    }
//  }
//  @GetMapping(path = "/api/data/database/get-all-gearbox-data")
//  public Page<SensorBearing> getUnlabeledGearboxData(@RequestParam("dataType") String dataType, @Parameter(hidden = true) Pageable pageable) {
//    try {
//      return this.datasetDatabaseService.getUnlabeledGearboxData(pageable);
//    } catch (IOException e) {
//      e.printStackTrace();
//      return null;
//    }
//  }
//  @GetMapping(path = "/api/data/database/get-all-engine-data")
//  public Page<SensorBearing> getUnlabeledEngineData(@RequestParam("dataType") String dataType, @Parameter(hidden = true) Pageable pageable) {
//    try {
//      return this.datasetDatabaseService.getUnlabeledEngineData(pageable);
//    } catch (IOException e) {
//      e.printStackTrace();
//      return null;
//    }
//  }
}
