package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorBearingRightOutsideInput {
    private int IDX;

    // AI Predict
    private String AI_RBPFO;

    private String AI_RBPFO_ALGO;

    private String AI_RBPFO_MODEL;

    private Date AI_RBPFO_DATE;

    // User Judgement
    private String USER_RBPFO;

    private String USER_RBPFO_ID;

    private Date USER_RBPFO_DATE;

    // from Bearing
    private double W_RPM;

    private double R_B_V_1X;

    private double R_B_V_6912BPFO;

    private double R_B_V_32924BPFO;

    private double R_B_V_32922BPFO;

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
