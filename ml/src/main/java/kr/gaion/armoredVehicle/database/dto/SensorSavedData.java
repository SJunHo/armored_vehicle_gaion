package kr.gaion.armoredVehicle.database.dto;

import kr.gaion.armoredVehicle.database.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SensorSavedData {
    private List<SensorBearing> sensorBearing;
    private List<SensorWheel> sensorWheel;
    private List<SensorGearbox> sensorGearbox;
    private List<SensorEngine> sensorEngine;
    private List<SensorTempLife> sensorTempLife;
}
