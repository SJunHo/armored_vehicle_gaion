package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorBearingRightBallInput {
    private int IDX;

    // AI Predict
    private String AI_RBSF;

    private String AI_RBSF_ALGO;

    private String AI_RBSF_MODEL;

    private Date AI_RBSF_DATE;

    // User Judgement
    private String USER_RBSF;

    private String USER_RBPFO_ID;

    private Date USER_RBSF_DATE;

    // from Bearing
    private double W_RPM;

    private double R_B_V_1X;

    private double R_B_V_6912BSF;

    private double R_B_V_32924BSF;

    private double R_B_V_32922BSF;

    private double R_B_V_Crestfactor;

    private double R_B_V_Demodulation;

    private double R_B_S_Fault1;

    private double R_B_S_Fault2;

    private double R_B_T_Temperature;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;

    private Date DATE;

    // file name
    private String FILENM;
}
