package kr.gaion.armoredVehicle.dataset.service;

import kr.gaion.armoredVehicle.database.DatabaseModule;
import kr.gaion.armoredVehicle.database.model.*;
import kr.gaion.armoredVehicle.database.repository.*;
import kr.gaion.armoredVehicle.dataset.dto.DbDataUpdateInput;
import kr.gaion.armoredVehicle.dataset.helper.CSVHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class DatasetDatabaseService {
    @NonNull
    private final StorageService storageService;
    @NonNull
    private final DatabaseModule databaseModule;

    @NonNull
    private final TrainingBearingRepository trainingBearingRepository;
    @NonNull
    private final TrainingWheelRepository trainingWheelRepository;
    @NonNull
    private final TrainingEngineRepository trainingEngineRepository;
    @NonNull
    private final TrainingGearboxRepository trainingGearboxRepository;
    @NonNull
    private final TrainingTempLifeRepository trainingTempLifeRepository;

    @NonNull
    private final SensorBearingRepository sensorBearingRepository;
    @NonNull
    private final SensorWheelRepository sensorWheelRepository;
    @NonNull
    private final SensorEngineRepository sensorEngineRepository;
    @NonNull
    private final SensorGearboxRepository sensorGearboxRepository;
    @NonNull
    private final SensorTempLifeRepository sensorTempLifeRepository;

    //save file to nas directory
    public String handleUploadFile(MultipartFile file) {
        this.storageService.store(file);
        return file.getOriginalFilename();
    }

    //import nas Database
    public String importCSVtoDatabase(List<MultipartFile> files, String dataType) {
        for (MultipartFile file : files) {
            switch (dataType) {
                case "B": {
                    try {
                        System.out.println("file.getOriginalFilename() : " + file.getOriginalFilename());
                        List<TrainingBearing> trainingBearingList = CSVHelper.csvToBearing(file.getInputStream(), file.getOriginalFilename());
                        trainingBearingRepository.saveAll(trainingBearingList);
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }
                case "W": {
                    try {
                        List<TrainingWheel> trainingWheelList = CSVHelper.csvToWheel(file.getInputStream());
                        trainingWheelRepository.saveAll(trainingWheelList);
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }
                case "G": {
                    try {
                        List<TrainingGearbox> trainingGearboxList = CSVHelper.csvToGearbox(file.getInputStream());
                        trainingGearboxRepository.saveAll(trainingGearboxList);
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }
                case "E": {
                    try {
                        List<TrainingEngine> trainingEngineList = CSVHelper.csvToEngine(file.getInputStream());
                        trainingEngineRepository.saveAll(trainingEngineList);
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
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

    // get labeled data (for training)
    public List<?> getTrainingBearingData(String partType) throws IOException {
        System.out.println("############### getTrainingBearingData service ###############");
        List<?> trainingBearingData = null;
        switch (partType) {
            case "BLB":
                // Bearing Left Ball
                trainingBearingData = trainingBearingRepository.findBearingLeftBall();
                break;
            case "BLI":
                // Bearing Left Inside
                trainingBearingData = trainingBearingRepository.findBearingLeftInside();
                break;
            case "BLO":
                // Bearing Left Outside
                trainingBearingData = trainingBearingRepository.findBearingLeftOutside();
                break;
            case "BLR":
                // Bearing Left Retainer
                trainingBearingData = trainingBearingRepository.findBearingLeftRetainer();
                break;
            case "BRB":
                // Bearing Right Ball
                trainingBearingData = trainingBearingRepository.findBearingRightBall();
                break;

            case "BRI":
                // Bearing Right Inside
                trainingBearingData = trainingBearingRepository.findBearingRightInside();
                break;

            case "BRO":
                // Bearing Right Outside
                trainingBearingData = trainingBearingRepository.findBearingRightOutside();
                break;

            case "BRR":
                // Bearing Right Retainer
                trainingBearingData = trainingBearingRepository.findBearingRightRetainer();
                break;
        }

        return trainingBearingData;
    }

    // TODO: 부품별 메소드 만들기
//    public List<?> getTrainingWheelData(String partType) throws IOException {
//        List<?> trainingWheelData = null;
//        switch (partType) {
//            case "WL":
//                // Wheel Left
//                trainingWheelData = trainingWheelRepository.findWheelLeft();
//                break;
//            case "BLI":
//                // Wheel Right
//                trainingWheelData = trainingWheelRepository.findWheelRight();
//                break;
//        }
//
//        return trainingWheelData;
//    }
//
//    public List<?> getTrainingGearboxData(String partType) throws IOException {
//        return trainingGearboxRepository.findAll();
//    }
//
//    public List<?> getTrainingEngineData(String partType) throws IOException {
//        return trainingEngineRepository.findAll();
//    }

    // get unlabeled data (for predict)
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
        return sensorTempLifeRepository.findSensorTempLifeByAiPredictIsNull();
    }

    public String updatePredictData(ArrayList<DbDataUpdateInput> inputs) throws IOException {
        if (inputs.get(0).getDataType().equals("B")) {
            for (DbDataUpdateInput input : inputs) {
                SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).get();
                sensorBearing.setAiPredict(input.getAiPredict());
                sensorBearing.setAiAlgorithm(input.getAiAlgorithm());
                sensorBearing.setAiModel(input.getModelName());
                sensorBearingRepository.save(sensorBearing);
            }
        }
        if (inputs.get(0).getDataType().equals("E")) {
        }
        if (inputs.get(0).getDataType().equals("G")) {
        }
        if (inputs.get(0).getDataType().equals("W")) {
        }
        if (inputs.get(0).getDataType().equals("T")) {
            for (DbDataUpdateInput input : inputs) {
                SensorTempLife sensorTempLife = sensorTempLifeRepository.findById(input.getId()).get();
                sensorTempLife.setAiPredict(input.getAiPredict());
                sensorTempLife.setAiAlgorithm(input.getAiAlgorithm());
                sensorTempLife.setAiModel(input.getModelName());
                sensorTempLifeRepository.save(sensorTempLife);
            }
        }

        return "save";
    }

}
