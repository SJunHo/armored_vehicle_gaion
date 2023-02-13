package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorBearingRightRetainerInput {
    private int IDX;

    private String SDAID;

    // AI Predict
    private String AI_RFTF;

    private String AI_RFTF_ALGO;

    private String AI_RFTF_MODEL;

    private Date AI_RFTF_DATE;

    // User Judgement
    private String USER_RFTF;

    private String USER_RFTF_ID;

    private Date USER_RFTF_DATE;

    // from Bearing
    private double W_RPM;

    private double R_B_V_1X;

    private double R_B_V_6912FTF;

    private double R_B_V_32924FTF;

    private double R_B_V_32922FTF;

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
}
