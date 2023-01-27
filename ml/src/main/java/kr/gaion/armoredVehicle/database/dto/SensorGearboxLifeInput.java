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
    private int AI_Trip;

    private String AI_Trip_ALGO;

    private String AI_Trip_MODEL;

    private Date AI_Trip_DATE;

    // from Gearbox
    private double G_OverallRMS;

    private double G_Wheel1X;

    private double G_Wheel2X;

    private double G_Pinion1X;

    private double G_Pinion2X;

    private double G_GMF1X;

    private double G_GMF2X;
}
