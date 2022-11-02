package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BearingLeftRetainerInput {
    // AI_Predict
    private int AI_LFTF;

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
}
