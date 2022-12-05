package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorWheelRightInput {
    private int IDX;

    // AI Predict
    private String AI_RW;

    private String AI_RW_ALGO;

    private String AI_RW_MODEL;

    private Date AI_RW_DATE;

    // User Judgement
    private String USER_RW;

    private String USER_RW_ID;

    private Date USER_RW_DATE;

    // from Bearing
    private double W_RPM;

    private double R_W_V_2X;

    private double R_W_V_3X;

    private double R_W_S_Fault3;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;

    private Date DATE;
}
