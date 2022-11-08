package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorWheelLeftInput {
    private int IDX;

    // AI Predict
    private String AI_LW;

    private String AI_LW_ALGO;

    private String AI_LW_MODEL;

    private Date AI_LW_DATE;

    // User Judgement
    private String USER_LW;

    private String USER_LW_ID;

    private Date USER_LW_DATE;

    // from Bearing
    private double W_RPM;

    private double L_W_V_2X;

    private double L_W_V_3X;

    private double L_W_S_Fault3;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;

    private Date DATE;

    // file name
    private String FILENM;
}
