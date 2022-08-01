package kr.gaion.armoredVehicle.dataset.service;

import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.database.DatabaseModule;
import kr.gaion.armoredVehicle.database.model.SensorBearing;
import kr.gaion.armoredVehicle.database.model.SensorTempLife;
import kr.gaion.armoredVehicle.database.repository.SensorBearingRepository;
import kr.gaion.armoredVehicle.database.repository.SensorTempLifeRepository;
import kr.gaion.armoredVehicle.dataset.helper.CSVHelper;
import kr.gaion.armoredVehicle.database.model.TrainingBearing;
import kr.gaion.armoredVehicle.database.model.TrainingTempLife;
import kr.gaion.armoredVehicle.database.repository.TrainingBearingRepository;
import kr.gaion.armoredVehicle.database.repository.TrainingTempLifeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
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
    @NonNull private final TrainingTempLifeRepository trainingTempLifeRepository;
    @NonNull private final SensorTempLifeRepository sensorTempLifeRepository;

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
                        List<TrainingBearing> trainingBearingList = CSVHelper.csvToBearing(file.getInputStream());
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
                case "tempLife": {
                    try {
                        List<TrainingTempLife> trainingTempLifeList = CSVHelper.csvToTempLife(file.getInputStream());
                        trainingTempLifeRepository.saveAll(trainingTempLifeList);
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }
            }
        }
        return "success";
    }

    //import Table data
    public List<SensorBearing> getUnlabeledBearingData() throws IOException {
        return sensorBearingRepository.findSensorBearingByAiPredictIsNull();
    }

    public List<SensorTempLife> getUnlabeledTempLifeData() throws IOException {
        return sensorTempLifeRepository.findSensorTempLifeByAcPowerIsNull();
    }


}
