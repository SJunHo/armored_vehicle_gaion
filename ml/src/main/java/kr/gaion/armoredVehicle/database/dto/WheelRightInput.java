package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class WheelRightInput {
    // AI_Predict
    private int AI_RW;

    // from Wheel
    private double W_RPM;

    private double R_W_V_2X;

    private double R_W_V_3X;

    private double R_W_S_Fault3;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;
}
