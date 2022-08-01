package kr.gaion.armoredVehicle.dataset.service;
import kr.gaion.armoredVehicle.database.DatabaseModule;
import kr.gaion.armoredVehicle.database.model.*;
import kr.gaion.armoredVehicle.database.repository.*;
import kr.gaion.armoredVehicle.dataset.dto.DbDataUpdateInput;
import kr.gaion.armoredVehicle.dataset.helper.CSVHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class DatasetDatabaseService {
    @NonNull private final StorageService storageService;
    @NonNull private final DatabaseModule databaseModule;
    @NonNull private final TrainingBearingRepository trainingBearingRepository;
    @NonNull private final SensorBearingRepository sensorBearingRepository;
    @NonNull private final SensorWheelRepository sensorWheelRepository;
    @NonNull private final SensorEngineRepository sensorEngineRepository;
    @NonNull private final SensorGearboxRepository sensorGearboxRepository;
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
                case "gearbox": {

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

    public List<SensorWheel> getUnlabeledWheelData() throws IOException {
        return sensorWheelRepository.findSensorWheelByAiPredictIsNull();
    }

    public List<SensorGearbox> getUnlabeledGearboxData() throws IOException {
        return sensorGearboxRepository.findSensorGearboxByAiPredictIsNull();
    }

    public List<SensorEngine> getUnlabeledEngineData() throws IOException {
        return sensorEngineRepository.findSensorEngineByAiPredictIsNull();
    }

    public List<SensorTempLife> getUnlabeledTempLifeData() throws IOException {
        return sensorTempLifeRepository.findSensorTempLifeByAcPowerIsNull();
    }

    public String updatePredictData(ArrayList<DbDataUpdateInput> inputs) throws IOException {
        if(inputs.get(0).getDataType().equals("B")){
            for(DbDataUpdateInput input : inputs){
                SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).get();
                sensorBearing.setAiPredict(input.getAiPredict());
                sensorBearing.setAiAlgorithm(input.getAiAlgorithm());
                sensorBearing.setAiModel(input.getModelName());
                sensorBearingRepository.save(sensorBearing);
            }
        }
        if(inputs.get(0).getDataType().equals("E")){}
        if(inputs.get(0).getDataType().equals("G")){}
        if(inputs.get(0).getDataType().equals("W")){}

        return "save";
    }

}
