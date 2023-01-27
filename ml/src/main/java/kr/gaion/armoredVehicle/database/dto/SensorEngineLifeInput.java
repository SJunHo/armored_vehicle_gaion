package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorEngineLifeInput {
    private int IDX;

    // AI Predict
    private int AI_Trip;

    private String AI_Trip_ALGO;

    private String AI_Trip_MODEL;

    private Date AI_Trip_DATE;

    // from Engine
    private double E_OverallRMS;

    private double E_1_2X;

    private double E_1X;

    private double E_CrestFactor;
}
