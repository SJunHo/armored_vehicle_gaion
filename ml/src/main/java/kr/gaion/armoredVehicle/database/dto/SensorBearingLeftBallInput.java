package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorBearingLeftBallInput {
    private int IDX;

    // AI Predict
    private String AI_LBSF;

    private String AI_LBSF_ALGO;

    private String AI_LBSF_MODEL;

    private Date AI_LBSF_DATE;

    // User Judgement
    private String USER_LBSF;

    private String USER_LBSF_ID;

    private Date USER_LBSF_DATE;

    // from Bearing
    private double W_RPM;

    private double L_B_V_1X;

    private double L_B_V_6912BSF;

    private double L_B_V_32924BSF;

    private double L_B_V_32922BSF;

    private double L_B_V_Crestfactor;

    private double L_B_V_Demodulation;

    private double L_B_S_Fault1;

    private double L_B_S_Fault2;

    private double L_B_T_Temperature;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;

    private Date DATE;

    // file name
    private String FILENM;
}
