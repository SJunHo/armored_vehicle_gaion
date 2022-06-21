package kr.gaion.armoredVehicle.dataset.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import kr.gaion.armoredVehicle.dataset.service.DatabaseService;
import kr.gaion.armoredVehicle.spark.DatabaseSparkService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DatabaseController {
    @NonNull private final DatabaseService databaseService;

//    @PostMapping(path = "/api/data/upload-csv-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public List<String> uploadDatasetFile(
//            @Parameter(
//                    description = "Files to be uploaded",
//                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)  // Won't work without OCTET_STREAM as the mediaType.
//            )
//            @RequestParam("files") List<MultipartFile> files
//    ) {
//        return files.stream().map(this.databaseService::handleUploadFile).collect(Collectors.toList());
//    }
}
