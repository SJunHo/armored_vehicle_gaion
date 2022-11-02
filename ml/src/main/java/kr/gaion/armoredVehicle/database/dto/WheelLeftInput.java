package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class WheelLeftInput {
    // AI_Predict
    private int AI_LW;

    // from Wheel
    private double W_RPM;

    private double L_W_V_2X;

    private double L_W_V_3X;

    private double L_W_S_Fault3;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;
}
