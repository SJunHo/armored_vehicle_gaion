package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import kr.gaion.armoredVehicle.dataset.service.DatasetDatabaseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
