package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorBearingLeftRetainerInput {
    // AI Predict
    private String AI_LFTF;

    private String AI_LFTF_ALGO;

    private String AI_LFTF_MODEL;

    private Date AI_LFTF_DATE;

    // User Judgement
    private int USER_LFTF;

    private String USER_LFTF_ID;

    private Date USER_LFTF_DATE;

    // from Bearing
    private double W_RPM;

    private double L_B_V_1X;

    private double L_B_V_6912FTF;

    private double L_B_V_32924FTF;

    private double L_B_V_32922FTF;

    private double L_B_V_Crestfactor;

    private double L_B_V_Demodulation;

    private double L_B_S_Fault1;

    private double L_B_S_Fault2;

    private double L_B_T_Temperature;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;

    // file name
    private String FILENM;
}
