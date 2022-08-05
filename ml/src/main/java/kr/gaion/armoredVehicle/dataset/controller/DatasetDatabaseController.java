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
  public List<SensorBearing> getUnlabeledBearingData(@RequestParam("dataType") String partType) {
//      if(partType.equals("B")){
//
//      }else if(partType.equals("W")){
//
//      }else if(partType.equals("E")){
//
//      }else if(partType.equals("G")){
//
//      }
    try {
        return this.datasetDatabaseService.getUnlabeledBearingData();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @GetMapping(path = "/api/data/database/get-all-wheel-data")
  public List<SensorWheel> getUnlabeledWheelData(@RequestParam("dataType") String dataType) {
    try {
      System.out.println("123123123123");
      return this.datasetDatabaseService.getUnlabeledWheelData();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @GetMapping(path = "/api/data/database/get-all-gearbox-data")
  public List<SensorGearbox> getUnlabeledGearboxData(@RequestParam("dataType") String dataType) {
    try {
      return this.datasetDatabaseService.getUnlabeledGearboxData();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @GetMapping(path = "/api/data/database/get-all-engine-data")
  public List<SensorEngine> getUnlabeledEngineData(@RequestParam("dataType") String dataType) {
    try {
      return this.datasetDatabaseService.getUnlabeledEngineData();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @GetMapping(path = "/api/data/database/get-all-templife-data")
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
