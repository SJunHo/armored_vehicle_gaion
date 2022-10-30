package kr.gaion.armoredVehicle.database.service;

import kr.gaion.armoredVehicle.database.dto.DBDataUpdateInput;
import kr.gaion.armoredVehicle.database.dto.SensorPredictedData;
import kr.gaion.armoredVehicle.database.dto.SensorSavedData;
import kr.gaion.armoredVehicle.database.model.*;
import kr.gaion.armoredVehicle.database.repository.*;
import kr.gaion.armoredVehicle.ml.dto.SensorBearingData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j
public class PredictedResultDatabaseService {
    @NonNull private final SdaRepository sdaRepository;
    @NonNull private final SensorBearingRepository sensorBearingRepository;
    @NonNull private final SensorGearboxRepository sensorGearboxRepository;
    @NonNull private final SensorWheelRepository sensorWheelRepository;
    @NonNull private final SensorEngineRepository sensorEngineRepository;
    @NonNull private final SensorTempLifeRepository sensorTempLifeRepository;

    public SensorPredictedData getSensorDataNotNull(String partType, String fromDate, String toDate, Pageable pageable) {
        SensorPredictedData returnValue = new SensorPredictedData();
        switch (partType) {
            case "B":
                returnValue.setSensorBearing(sensorBearingRepository.findSensorBearingByAiPredictIsNotNull(fromDate, toDate, pageable));
                break;
            case "G":
                returnValue.setSensorGearbox(sensorGearboxRepository.findSensorGearboxByAiPredictIsNotNull(fromDate, toDate, pageable));
                break;
            case "W":
                returnValue.setSensorWheel(sensorWheelRepository.findSensorWheelByAiPredictIsNotNull(fromDate, toDate, pageable));
                break;
            case "E":
                returnValue.setSensorEngine(sensorEngineRepository.findSensorEngineByAiPredictIsNotNull(fromDate, toDate, pageable));
                System.out.println(returnValue);
                break;
            case "T":
                returnValue.setSensorTempLife(sensorTempLifeRepository.findSensorTempLifeByAiPredictIsNotNull(fromDate, toDate, pageable));
                break;
        }
        return returnValue;
    }

//    public SensorSavedData updateSensorData(String partType, List<DBDataUpdateInput> inputs) throws IOException {
//        if (partType.equals("B")) {
//            sensorBearingRepository.save(inputs);
//        }
//    }

    public List<String> getSdaList() {
        return sdaRepository.getSdaIdList();
    }
}
