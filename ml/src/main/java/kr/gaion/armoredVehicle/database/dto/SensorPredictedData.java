package kr.gaion.armoredVehicle.database.dto;

import kr.gaion.armoredVehicle.database.model.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class SensorPredictedData {
    private Page<SensorBearing> sensorBearing;
    private Page<SensorWheel> sensorWheel;
    private Page<SensorGearbox> sensorGearbox;
    private Page<SensorEngine> sensorEngine;
    private Page<SensorTempLife> sensorTempLife;
}
