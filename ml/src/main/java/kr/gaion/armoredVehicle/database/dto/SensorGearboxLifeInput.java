package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorGearboxLifeInput {
    private int IDX;

    // AI Predict
    private Integer AI_Trip;

    private String AI_Trip_ALGO;

    private String AI_Trip_MODEL;

    private Date AI_Trip_DATE;

    // from Gearbox
    private Double G_OverallRMS;

    private Double G_Wheel1X;

    private Double G_Wheel2X;

    private Double G_Pinion1X;

    private Double G_Pinion2X;

    private Double G_GMF1X;

    private Double G_GMF2X;
}
