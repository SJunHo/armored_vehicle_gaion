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
    private Date DATE;
    // AI Predict
    private Integer AI_Trip;

    private String AI_Trip_ALGO;

    private String AI_Trip_MODEL;

    private Date AI_Trip_DATE;

    // from Engine
    private Double E_OverallRMS;

    private Double E_1_2X;

    private Double E_1X;

    private Double E_CrestFactor;
}
