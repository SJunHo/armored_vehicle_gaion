package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BearingRightInsideInput {
    // AI_Predict
    private int AI_RBPFI;

    // from Bearing
    private double W_RPM;

    private double R_B_V_1X;

    private double R_B_V_6912BPFI;

    private double R_B_V_32924BPFI;

    private double R_B_V_32922BPFI;

    private double R_B_V_Crestfactor;

    private double R_B_V_Demodulation;

    private double R_B_S_Fault1;

    private double R_B_S_Fault2;

    private double R_B_T_Temperature;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;
}
