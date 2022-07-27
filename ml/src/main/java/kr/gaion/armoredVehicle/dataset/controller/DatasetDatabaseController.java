package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import kr.gaion.armoredVehicle.dataset.service.DatasetDatabaseService;
import kr.gaion.armoredVehicle.ml.dto.RailSensorData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  @PageableAsQueryParam
  @GetMapping(path = "/api/data/database/get-all-data")
  public Page<Object> getAllConditionDataDB(
          @RequestParam("dataType") String dataType,
          @RequestParam(value = "train-no", required = false) String trainNumber,
          @RequestParam(value = "car-no", required = false) String carNumber,
          @RequestParam(value = "from-date", required = false) Date fromDate,
          @RequestParam(value = "to-date", required = false) Date toDate,
          @RequestParam(value = "severity", required = false) Integer severity,
          @RequestParam(value = "has_defect_score", required = false) boolean hasDefectScore,
          @RequestParam(value = "has_defect_user", required = false) Integer hasDefectUser,
          @Parameter(hidden = true) Pageable pageable) {
    try {
      return this.datasetDatabaseService.getConditionData(dataType, trainNumber, carNumber, fromDate, toDate, severity, pageable, hasDefectScore, hasDefectUser);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
