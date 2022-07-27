package kr.gaion.armoredVehicle.dataset.service;

import kr.gaion.armoredVehicle.database.DatabaseModule;
import kr.gaion.armoredVehicle.database.model.SensorBearing;
import kr.gaion.armoredVehicle.database.repository.SensorBearingRepository;
import kr.gaion.armoredVehicle.dataset.helper.CSVHelper;
import kr.gaion.armoredVehicle.database.model.TrainingBearing;
import kr.gaion.armoredVehicle.database.repository.TrainingBearingRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class DatasetDatabaseService {
    @NonNull private final StorageService storageService;
    @NonNull private final DatabaseModule databaseModule;
    @NonNull private final TrainingBearingRepository trainingBearingRepository;
    @NonNull private final SensorBearingRepository sensorBearingRepository;
    //save file to nas directory
    public String handleUploadFile(MultipartFile file) {
        this.storageService.store(file);
        return file.getOriginalFilename();
    }

    //import nas Database
    public String importCSVtoDatabase(List<MultipartFile> files, String dataType){

        for(MultipartFile file : files){
            switch(dataType){
                case "bearing": {
                    try {
                        List<TrainingBearing> trainingBearingList = CSVHelper.csvToTutorials(file.getInputStream());
                        trainingBearingRepository.saveAll(trainingBearingList);
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }
                case "wheel": {

                }
                case "gearBox": {

                }
                case "engine": {

                }
            }
        }
        return "success";
    }

    public String insertFileInfoToDatabase(){

        return "fileInfo";
    }


    public Page<SensorBearing> getUnlabeledBearingData(Pageable pageable) throws IOException {
        return sensorBearingRepository.findSensorBearingByAiPredictIsNull(pageable);
    }
}
