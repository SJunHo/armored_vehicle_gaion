package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import kr.gaion.armoredVehicle.dataset.dto.AllTimeESStats;
import kr.gaion.armoredVehicle.dataset.dto.ESDataUpdateInput;
import kr.gaion.armoredVehicle.dataset.dto.ImportESDataFromFileInput;
import kr.gaion.armoredVehicle.dataset.dto.UpdateDataLookupInput;
import kr.gaion.armoredVehicle.dataset.model.DataLookup;
import kr.gaion.armoredVehicle.dataset.service.DataLookupService;
import kr.gaion.armoredVehicle.dataset.service.DatasetService;
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

@RestController("/api/data")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DatasetController {
  @NonNull private final DatasetService datasetService;
  @NonNull private final DataLookupService dataLookupService;

  @PageableAsQueryParam
  @GetMapping(path = "/api/data/predict")
  public Page<RailSensorData> getDataToPredict(@Parameter(hidden = true) Pageable pageable) {
    try {
      return this.datasetService.getPredictingData(pageable);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @PostMapping(path = "/api/data")
  public RailSensorData[] updateRailSensorData(@RequestBody List<ESDataUpdateInput> input) throws IOException {
    return this.datasetService.updateRailSensorData(input);
  }

  @PageableAsQueryParam
  @GetMapping(path = "/api/data/all")
  public Page<RailSensorData> getAllConditionData(
      @RequestParam("wb") String wb,
      @RequestParam(value = "train-no", required = false) String trainNumber,
      @RequestParam(value = "car-no", required = false) String carNumber,
      @RequestParam(value = "from-date", required = false) Date fromDate,
      @RequestParam(value = "to-date", required = false) Date toDate,
      @RequestParam(value = "severity", required = false) Integer severity,
      @RequestParam(value = "has_defect_score", required = false) boolean hasDefectScore,
      @RequestParam(value = "has_defect_user", required = false) Integer hasDefectUser,
      @Parameter(hidden = true) Pageable pageable) {
    try {
      return this.datasetService.getConditionData(wb, trainNumber, carNumber, fromDate, toDate, severity, pageable, hasDefectScore, hasDefectUser);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @GetMapping(path = "/api/data/trains")
  public List<String> getTrainLists() {
    try {
      return this.datasetService.getTrainsList();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @GetMapping(path = "/api/data/trains-cars")
  public List<String> getCarLists(@RequestParam(value = "train-number", required = false) String trainNumber) {
    try {
      return this.datasetService.getCarsList(trainNumber);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @PostMapping(path = "/api/data/upload-dataset-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public List<String> uploadDatasetFile(
      @Parameter(
        description = "Files to be uploaded",
        content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)  // Won't work without OCTET_STREAM as the mediaType.
      )
      @RequestParam("files") List<MultipartFile> files
  ) {
    return files.stream().map(this.datasetService::handleUploadFile).collect(Collectors.toList());
  }

  @PostMapping(path = "/api/data/import/csv-to-es")
  public void importESIndexFromCSV(@RequestBody ImportESDataFromFileInput input) throws IOException {
    this.datasetService.handleImportESIndexFromFile(input);
  }

  @PostMapping(path = "/api/data-lookup/")
  public DataLookup createDataLookup(@RequestBody UpdateDataLookupInput input) {
    return this.dataLookupService.createDataLookup(input);
  }

  @PutMapping(path = "/api/data-lookup/")
  public DataLookup updateDataLookup(@RequestBody UpdateDataLookupInput input) {
    return this.dataLookupService.updateDataLookup(input);
  }

  @DeleteMapping(path = "/api/data-lookup/{lookupName}")
  public DataLookup deleteDataLookup(@PathVariable String lookupName) {
    return this.dataLookupService.deleteDataLookup(lookupName);
  }

  @GetMapping(path = "/api/data-lookup")
  public List<DataLookup> getAllDataLookups() {
    return this.dataLookupService.getAllDataLookup();
  }

  @GetMapping(path = "/api/data/stats/month-daily-count")
  public List<Long> countRecordsDailyInLast30Days() throws IOException {
    return this.datasetService.countRecordsDailyInLast30Days();
  }

  @GetMapping(path = "/api/data/stats/6-months-count")
  public List<Long> countRecordsMonthlyLast6Months() throws IOException {
    return this.datasetService.countRecordsMonthlyLast6Months();
  }

  @GetMapping(path = "/api/data/stats/all-times")
  public AllTimeESStats allTimeStats() throws IOException {
    return this.datasetService.getAllTimeStats();
  }
}
