package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorEngineInput {
    // AI Predict
    private String AI_ENGINE;

    private String AI_ENGINE_ALGO;

    private String AI_ENGINE_MODEL;

    private Date AI_ENGINE_DATE;

    // User Judgement
    private int USER_ENGINE;

    private String USER_ENGINE_ID;

    private Date USER_ENGINE_DATE;

    // from Bearing
    private double W_RPM;

    private double E_V_OverallRMS;

    private double E_V_1_2X;

    private double E_V_1X;

    private double E_V_Crestfactor;

    private double AC_h;

    private double AC_v;

    private double AC_a;

    // file name
    private String FILENM;
}
