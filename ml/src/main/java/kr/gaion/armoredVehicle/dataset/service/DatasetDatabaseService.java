package kr.gaion.armoredVehicle.dataset.service;

import io.swagger.v3.oas.annotations.Parameter;
import kr.gaion.armoredVehicle.auth.User;
import kr.gaion.armoredVehicle.common.Utilities;
import kr.gaion.armoredVehicle.database.model.*;
import kr.gaion.armoredVehicle.database.repository.*;
import kr.gaion.armoredVehicle.dataset.dto.DbDataUpdateInput;
import kr.gaion.armoredVehicle.dataset.helper.CSVHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class DatasetDatabaseService {
    @NonNull
    private final StorageService storageService;
    @NonNull
    private final TrainingBearingRepository trainingBearingRepository;
    @NonNull
    private final TrainingWheelRepository trainingWheelRepository;
    @NonNull
    private final TrainingEngineRepository trainingEngineRepository;
    @NonNull
    private final TrainingGearboxRepository trainingGearboxRepository;
    @NonNull
    private final TrainingBearingLifeRepository trainingBearingLifeRepository;
    @NonNull
    private final TrainingEngineLifeRepository trainingEngineLifeRepository;
    @NonNull
    private final TrainingGearboxLifeRepository trainingGearboxLifeRepository;
    @NonNull
    private final TrainingWheelLifeRepository trainingWheelLifeRepository;
    @NonNull
    private final SensorBearingRepository sensorBearingRepository;
    @NonNull
    private final SensorWheelRepository sensorWheelRepository;
    @NonNull
    private final SensorEngineRepository sensorEngineRepository;
    @NonNull
    private final SensorGearboxRepository sensorGearboxRepository;
    @NonNull
    private final SensorBearingLifeRepository sensorBearingLifeRepository;
    @NonNull
    private final SensorWheelLifeRepository sensorWheelLifeRepository;
    @NonNull
    private final SensorGearboxLifeRepository sensorGearboxLifeRepository;
    @NonNull
    private final SensorEngineLifeRepository sensorEngineLifeRepository;
    @NonNull
    private final FileInfoRepository fileInfoRepository;
    @NonNull
    private final Utilities utilities;

    //save file to nas directory
    public String handleUploadFile(MultipartFile file) {
        this.storageService.store(file);
        return file.getOriginalFilename();
    }

    //import nas Database
    public String importCSVtoDatabase(List<MultipartFile> files, String dataType) throws IOException {
        for (MultipartFile file : files) {
            //fileInfo insert
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileId(file.getOriginalFilename().replace(".csv", ""));
            fileInfo.setFileName(file.getOriginalFilename().replace(".csv", ""));
            fileInfo.setFileSnsr("O");
            fileInfo.setFileType("T");
            fileInfo.setFileDiv("N");
            fileInfo.setFilePt(dataType);
            fileInfo.setCreatedAt(new Date());
            Object obj = SecurityContextHolder.getContext().getAuthentication().getDetails();
            User logUser = (User) obj;
            fileInfo.setCreatedBy(logUser);
            fileInfo.setFilePath(utilities.getProjTmpFolder());
            fileInfoRepository.save(fileInfo);

            switch (dataType) {
                case "B": {
                    try {
                        System.out.println("import CSV " + "dataType : " + dataType + " / " + "Original File name : " + file.getOriginalFilename());
                        List<TrainingBearing> trainingBearingList = CSVHelper.csvToBearing(file.getInputStream(), file.getOriginalFilename());
                        trainingBearingRepository.saveAll(trainingBearingList);
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }
                case "W": {
                    try {
                        System.out.println("import CSV " + "dataType : " + dataType + " / " + "Original File name : " + file.getOriginalFilename());
                        List<TrainingWheel> trainingWheelList = CSVHelper.csvToWheel(file.getInputStream(), file.getOriginalFilename());
                        trainingWheelRepository.saveAll(trainingWheelList);
                        break;

                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }
                case "G": {
                    try {
                        System.out.println("import CSV " + "dataType : " + dataType + " / " + "Original File name : " + file.getOriginalFilename());
                        List<TrainingGearbox> trainingGearboxList = CSVHelper.csvToGearbox(file.getInputStream(), file.getOriginalFilename());
                        trainingGearboxRepository.saveAll(trainingGearboxList);
                        break;

                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }
                case "E": {
                    try {
                        System.out.println("import CSV " + "dataType : " + dataType + " / " + "Original File name : " + file.getOriginalFilename());
                        List<TrainingEngine> trainingEngineList = CSVHelper.csvToEngine(file.getInputStream(), file.getOriginalFilename());
                        trainingEngineRepository.saveAll(trainingEngineList);
                        break;

                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }

                case "B_LIFE": {
                    // Bearing Remaining Life
                    try {
                        System.out.println("import CSV " + "dataType : " + dataType + " / " + "Original File name : " + file.getOriginalFilename());
                        List<TrainingBearingLife> trainingBearingLifeList = CSVHelper.csvToBearingLife(file.getInputStream(), file.getOriginalFilename());
                        trainingBearingLifeRepository.saveAll(trainingBearingLifeList);
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }

                case "W_LIFE": {
                    // Wheel Remaining Life
                    try {
                        System.out.println("import CSV " + "dataType : " + dataType + " / " + "Original File name : " + file.getOriginalFilename());
                        List<TrainingWheelLife> trainingWheelLifeList = CSVHelper.csvToWheelLife(file.getInputStream(), file.getOriginalFilename());
                        trainingWheelLifeRepository.saveAll(trainingWheelLifeList);
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }

                case "G_LIFE": {
                    // Gearbox Remaining Life
                    try {
                        System.out.println("import CSV " + "dataType : " + dataType + " / " + "Original File name : " + file.getOriginalFilename());
                        List<TrainingGearboxLife> trainingGearboxLifeList = CSVHelper.csvToGearboxLife(file.getInputStream(), file.getOriginalFilename());
                        trainingGearboxLifeRepository.saveAll(trainingGearboxLifeList);
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }

                case "E_LIFE": {
                    // Engine Remaining Life
                    try {
                        System.out.println("import CSV " + "dataType : " + dataType + " / " + "Original File name : " + file.getOriginalFilename());
                        List<TrainingEngineLife> trainingEngineLifeList = CSVHelper.csvToEngineLife(file.getInputStream(), file.getOriginalFilename());
                        trainingEngineLifeRepository.saveAll(trainingEngineLifeList);
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException("fail to store csv data: " + e.getMessage());
                    }
                }
            }
        }
        return "SUCCESS";
    }

    // get labeled data (for training)
    public List<?> getTrainingBearingData(String partType) throws IOException {
        System.out.println("get Training Bearing Data --> " + "partType : " + partType);
        switch (partType) {
            case "BLB":
                // Bearing Left Ball
                return trainingBearingRepository.findBearingLeftBall();
            case "BLI":
                // Bearing Left Inside
                return trainingBearingRepository.findBearingLeftInside();
            case "BLO":
                // Bearing Left Outside
                return trainingBearingRepository.findBearingLeftOutside();
            case "BLR":
                // Bearing Left Retainer
                return trainingBearingRepository.findBearingLeftRetainer();
            case "BRB":
                // Bearing Right Ball
                return trainingBearingRepository.findBearingRightBall();
            case "BRI":
                // Bearing Right Inside
                return trainingBearingRepository.findBearingRightInside();
            case "BRO":
                // Bearing Right Outside
                return trainingBearingRepository.findBearingRightOutside();
            case "BRR":
                // Bearing Right Retainer
                return trainingBearingRepository.findBearingRightRetainer();
        }
        return null;
    }

    public List<?> getTrainingWheelData(String partType) throws IOException {
        System.out.println("get Training Wheel Data --> " + "partType : " + partType);
        switch (partType) {
            case "WL":
                // Wheel Left
                return trainingWheelRepository.findWheelLeft();
            case "WR":
                // Wheel Right
                return trainingWheelRepository.findWheelRight();
        }
        return null;
    }

    public List<?> getTrainingGearboxData() throws IOException {
        System.out.println("get Training Gearbox Data");
        return trainingGearboxRepository.findGearbox();
    }

    public List<?> getTrainingEngineData() throws IOException {
        System.out.println("get Training Engine Data");
        return trainingEngineRepository.findEngine();
    }

    // get labeled remaining life data (for training)
    public List<TrainingBearingLife> getTrainingBearingLifeData() throws IOException {
        System.out.println("get Training Bearing Life Data");
        return trainingBearingLifeRepository.findAll();
    }

    public List<TrainingWheelLife> getTrainingWheelLifeData() throws IOException {
        System.out.println("get Training Wheel Life Data");
        return trainingWheelLifeRepository.findAll();
    }

    public List<TrainingGearboxLife> getTrainingGearboxLifeData() throws IOException {
        System.out.println("get Training Gearbox Life Data");
        return trainingGearboxLifeRepository.findAll();
    }

    public List<TrainingEngineLife> getTrainingEngineLifeData() throws IOException {
        System.out.println("get Training Engine Life Data");
        return trainingEngineLifeRepository.findAll();
    }

    // get unlabeled data (for predict)
    public Page<?> getUnlabeledBearingData(String partType, @Parameter(hidden = true) Pageable pageable) throws IOException {
        System.out.println("get Unlabeled Bearing Data --> " + "partType : " + partType);
        switch (partType) {
            case "BLB":
                // Bearing Left Ball
                return sensorBearingRepository.findSensorBearingLeftBallAiLBSFIsNull(pageable);
            case "BLI":
                // Bearing Left Inside
                return sensorBearingRepository.findSensorBearingLeftInsideAiLBPFIIsNull(pageable);
            case "BLO":
                // Bearing Left Outside
                return sensorBearingRepository.findSensorBearingLeftOutsideAiLBPFOIsNull(pageable);
            case "BLR":
                // Bearing Left Retainer
                return sensorBearingRepository.findSensorBearingLeftRetainerAiLFTFIsNull(pageable);
            case "BRB":
                // Bearing Right Ball
                return sensorBearingRepository.findSensorBearingRightBallAiRBSFIsNull(pageable);
            case "BRI":
                // Bearing Right Inside
                return sensorBearingRepository.findSensorBearingRightInsideAiRBPFIIsNull(pageable);
            case "BRO":
                // Bearing Right Outside
                return sensorBearingRepository.findSensorBearingRightOutsideAiRBPFOIsNull(pageable);
            case "BRR":
                // Bearing Right Retainer
                return sensorBearingRepository.findSensorBearingRightRetainerRFTFIsNull(pageable);
        }
        return null;
    }

    public Page<?> getUnlabeledWheelData(String partType, @Parameter(hidden = true) Pageable pageable) throws IOException {
        System.out.println("get Unlabeled Wheel Data --> " + "partType : " + partType);
        switch (partType) {
            case "WL":
                // Wheel Left
                return sensorWheelRepository.findSensorWheelLeftAiLWIsNull(pageable);
            case "WR":
                // Wheel Right
                return sensorWheelRepository.findSensorWheelRightAiRWIsNull(pageable);
        }
        return null;
    }

    public Page<?> getUnlabeledGearboxData(@Parameter(hidden = true) Pageable pageable) throws IOException {
        System.out.println("get Unlabeled Gearbox Data");
        return sensorGearboxRepository.findSensorGearboxAiGEARIsNull(pageable);
    }

    public Page<?> getUnlabeledEngineData(@Parameter(hidden = true) Pageable pageable) throws IOException {
        System.out.println("get Unlabeled Engine Data");
        return sensorEngineRepository.findSensorEngineAiENGINEIsNull(pageable);
    }

    // get unlabeled remaining life data (for training)
    public Page<?> getUnlabeledBearingLifeData(@Parameter(hidden = true) Pageable pageable) throws IOException {
        System.out.println("get Unlabeled Bearing Life Data");
        return sensorBearingLifeRepository.findAiTripIsNull(pageable);
    }

    public Page<?> getUnlabeledWheelLifeData(@Parameter(hidden = true) Pageable pageable) throws IOException {
        System.out.println("get Unlabeled Wheel Life Data");
        return sensorWheelLifeRepository.findAiTripIsNull(pageable);
    }

    public Page<?> getUnlabeledGearboxLifeData(@Parameter(hidden = true) Pageable pageable) throws IOException {
        System.out.println("get Unlabeled Gearbox Life Data");
        return sensorGearboxLifeRepository.findAiTripIsNull(pageable);
    }

    public Page<?> getUnlabeledEngineLifeData(@Parameter(hidden = true) Pageable pageable) throws IOException {
        System.out.println("get Unlabeled Engine Life Data");
        return sensorEngineLifeRepository.findAiTripIsNull(pageable);
    }

    public String updatePredictData(List<DbDataUpdateInput> inputs) {
        String partType = inputs.get(0).getPartType();
        switch (partType) {
            case "BLB":
                // Bearing Left Ball
                for (DbDataUpdateInput input : inputs) {
                    SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).orElse(null);
                    assert sensorBearing != null;
                    sensorBearing.setAiLbsf(input.getAiPredict());
                    sensorBearing.setAiLbsfAlgorithm(input.getAiAlgorithmName());
                    sensorBearing.setAiLbsfModel(input.getAiModelName());
                    sensorBearing.setAiLbsfDate(new Date());
                    sensorBearingRepository.save(sensorBearing);
                }
                break;

            case "BLI":
                // Bearing Left Inside
                for (DbDataUpdateInput input : inputs) {
                    SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).orElse(null);
                    assert sensorBearing != null;
                    sensorBearing.setAiLbpfi(input.getAiPredict());
                    sensorBearing.setAiLbpfiAlgorithm(input.getAiAlgorithmName());
                    sensorBearing.setAiLbpfiModel(input.getAiModelName());
                    sensorBearing.setAiLbpfiDate(new Date());
                    sensorBearingRepository.save(sensorBearing);
                }
                break;

            case "BLO":
                // Bearing Left Outside
                for (DbDataUpdateInput input : inputs) {
                    SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).orElse(null);
                    assert sensorBearing != null;
                    sensorBearing.setAiLbpfo(input.getAiPredict());
                    sensorBearing.setAiLbpfoAlgorithm(input.getAiAlgorithmName());
                    sensorBearing.setAiLbpfoModel(input.getAiModelName());
                    sensorBearing.setAiLbpfoDate(new Date());
                    sensorBearingRepository.save(sensorBearing);
                }
                break;

            case "BLR":
                // Bearing Left Retainer
                for (DbDataUpdateInput input : inputs) {
                    SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).orElse(null);
                    assert sensorBearing != null;
                    sensorBearing.setAiLftf(input.getAiPredict());
                    sensorBearing.setAiLftfAlgorithm(input.getAiAlgorithmName());
                    sensorBearing.setAiLftfModel(input.getAiModelName());
                    sensorBearing.setAiLftfDate(new Date());
                    sensorBearingRepository.save(sensorBearing);
                }
                break;

            case "BRB":
                // Bearing Right Ball
                for (DbDataUpdateInput input : inputs) {
                    SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).orElse(null);
                    assert sensorBearing != null;
                    sensorBearing.setAiRbsf(input.getAiPredict());
                    sensorBearing.setAiRbsfAlgorithm(input.getAiAlgorithmName());
                    sensorBearing.setAiRbsfModel(input.getAiModelName());
                    sensorBearing.setAiRbsfDate(new Date());
                    sensorBearingRepository.save(sensorBearing);
                }
                break;

            case "BRI":
                // Bearing Right Inside
                for (DbDataUpdateInput input : inputs) {
                    SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).orElse(null);
                    assert sensorBearing != null;
                    sensorBearing.setAiRbpfi(input.getAiPredict());
                    sensorBearing.setAiRbpfiAlgorithm(input.getAiAlgorithmName());
                    sensorBearing.setAiRbpfiModel(input.getAiModelName());
                    sensorBearing.setAiRbpfiDate(new Date());
                    sensorBearingRepository.save(sensorBearing);
                }
                break;

            case "BRO":
                // Bearing Right Outside
                for (DbDataUpdateInput input : inputs) {
                    SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).orElse(null);
                    assert sensorBearing != null;
                    sensorBearing.setAiRbpfo(input.getAiPredict());
                    sensorBearing.setAiRbpfoAlgorithm(input.getAiAlgorithmName());
                    sensorBearing.setAiRbpfoModel(input.getAiModelName());
                    sensorBearing.setAiRbpfoDate(new Date());
                    sensorBearingRepository.save(sensorBearing);
                }
                break;

            case "BRR":
                // Bearing Right Retainer
                for (DbDataUpdateInput input : inputs) {
                    SensorBearing sensorBearing = sensorBearingRepository.findById(input.getId()).orElse(null);
                    assert sensorBearing != null;
                    sensorBearing.setAiRftf(input.getAiPredict());
                    sensorBearing.setAiRftfAlgorithm(input.getAiAlgorithmName());
                    sensorBearing.setAiRftfModel(input.getAiModelName());
                    sensorBearing.setAiRftfDate(new Date());
                    sensorBearingRepository.save(sensorBearing);
                }
                break;

            case "WL":
                // Wheel Left
                for (DbDataUpdateInput input : inputs) {
                    SensorWheel sensorWheel = sensorWheelRepository.findById(input.getId()).orElse(null);
                    assert sensorWheel != null;
                    sensorWheel.setAiLw(input.getAiPredict());
                    sensorWheel.setAiLwAlgorithm(input.getAiAlgorithmName());
                    sensorWheel.setAiLwModel(input.getAiModelName());
                    sensorWheel.setAiLwDate(new Date());
                    sensorWheelRepository.save(sensorWheel);
                }
                break;

            case "WR":
                // Wheel Right
                for (DbDataUpdateInput input : inputs) {
                    SensorWheel sensorWheel = sensorWheelRepository.findById(input.getId()).orElse(null);
                    assert sensorWheel != null;
                    sensorWheel.setAiRw(input.getAiPredict());
                    sensorWheel.setAiRwAlgorithm(input.getAiAlgorithmName());
                    sensorWheel.setAiRwModel(input.getAiModelName());
                    sensorWheel.setAiRwDate(new Date());
                    sensorWheelRepository.save(sensorWheel);
                }
                break;

            case "G":
                // Gearbox
                for (DbDataUpdateInput input : inputs) {
                    SensorGearbox sensorGearbox = sensorGearboxRepository.findById(input.getId()).orElse(null);
                    assert sensorGearbox != null;
                    sensorGearbox.setAiGear(input.getAiPredict());
                    sensorGearbox.setAiGearAlgorithm(input.getAiAlgorithmName());
                    sensorGearbox.setAiGearModel(input.getAiModelName());
                    sensorGearbox.setAiGearDate(new Date());
                    sensorGearboxRepository.save(sensorGearbox);
                }
                break;

            case "E":
                // Engine
                for (DbDataUpdateInput input : inputs) {
                    SensorEngine sensorEngine = sensorEngineRepository.findById(input.getId()).orElse(null);
                    assert sensorEngine != null;
                    sensorEngine.setAiEngine(input.getAiPredict());
                    sensorEngine.setAiEngineAlgorithm(input.getAiAlgorithmName());
                    sensorEngine.setAiEngineModel(input.getAiModelName());
                    sensorEngine.setAiEngineDate(new Date());
                    sensorEngineRepository.save(sensorEngine);
                }
                break;

            case "B_LIFE":
                // Bearing Remaining Life
                for (DbDataUpdateInput input : inputs) {
                    SensorBearingLife sensorBearingLife = sensorBearingLifeRepository.findById(input.getId()).orElse(null);
                    assert sensorBearingLife != null;
                    sensorBearingLife.setAiTrip(input.getAiPredict());
                    sensorBearingLife.setAiTripAlgo(input.getAiAlgorithmName());
                    sensorBearingLife.setAiTripModel(input.getAiModelName());
                    sensorBearingLife.setAiTripDate(new Date());
                    sensorBearingLifeRepository.save(sensorBearingLife);
                }
                break;

            case "W_LIFE":
                // Wheel Remaining Life
                for (DbDataUpdateInput input : inputs) {
                    SensorWheelLife sensorWheelLife = sensorWheelLifeRepository.findById(input.getId()).orElse(null);
                    assert sensorWheelLife != null;
                    sensorWheelLife.setAiTrip(input.getAiPredict());
                    sensorWheelLife.setAiTripAlgo(input.getAiAlgorithmName());
                    sensorWheelLife.setAiTripModel(input.getAiModelName());
                    sensorWheelLife.setAiTripDate(new Date());
                    sensorWheelLifeRepository.save(sensorWheelLife);
                }
                break;

            case "G_LIFE":
                // Gearbox Remaining Life
                for (DbDataUpdateInput input : inputs) {
                    SensorGearboxLife sensorGearboxLife = sensorGearboxLifeRepository.findById(input.getId()).orElse(null);
                    assert sensorGearboxLife != null;
                    sensorGearboxLife.setAiTrip(input.getAiPredict());
                    sensorGearboxLife.setAiTripAlgo(input.getAiAlgorithmName());
                    sensorGearboxLife.setAiTripModel(input.getAiModelName());
                    sensorGearboxLife.setAiTripDate(new Date());
                    sensorGearboxLifeRepository.save(sensorGearboxLife);
                }
                break;

            case "E_LIFE":
                // Engine Remaining Life
                for (DbDataUpdateInput input : inputs) {
                    SensorEngineLife sensorEngineLife = sensorEngineLifeRepository.findById(input.getId()).orElse(null);
                    assert sensorEngineLife != null;
                    sensorEngineLife.setAiTrip(input.getAiPredict());
                    sensorEngineLife.setAiTripAlgo(input.getAiAlgorithmName());
                    sensorEngineLife.setAiTripModel(input.getAiModelName());
                    sensorEngineLife.setAiTripDate(new Date());
                    sensorEngineLifeRepository.save(sensorEngineLife);
                }
                break;
        }
        return "Saved to DB completed.";
    }
}
