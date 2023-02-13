package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorGearboxInput {
    private int IDX;

    private String SDAID;

    // AI Predict
    private String AI_GEAR;

    private String AI_GEAR_ALGO;

    private String AI_GEAR_MODEL;

    private Date AI_GEAR_DATE;

    // User Judgement
    private String USER_GEAR;

    private String USER_GEAR_ID;

    private Date USER_GEAR_DATE;

    // from Bearing
    private double W_RPM;

    private double G_V_OverallRMS;

    private double G_V_Wheel1X;

    private double G_V_Wheel2X;

    private double G_V_Pinion1X;

    private double G_V_Pinion2X;

    private double G_V_GMF1X;

    private double G_V_GMF2X;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;

    private Date DATE;
}
