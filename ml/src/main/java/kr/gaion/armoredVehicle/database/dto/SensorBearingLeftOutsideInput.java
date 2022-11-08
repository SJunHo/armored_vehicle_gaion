package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorBearingLeftOutsideInput {
    private int IDX;

    // AI Predict
    private String AI_LBPFO;

    private String AI_LBPFO_ALGO;

    private String AI_LBPFO_MODEL;

    private Date AI_LBPFO_DATE;

    // User Judgement
    private String USER_LBPFO;

    private String USER_LBPFO_ID;

    private Date USER_LBPFO_DATE;

    // from Bearing
    private double W_RPM;

    private double L_B_V_1X;

    private double L_B_V_6912BPFO;

    private double L_B_V_32924BPFO;

    private double L_B_V_32922BPFO;

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
