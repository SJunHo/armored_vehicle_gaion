package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class GearboxInput {
    private int IDX;

    // AI_Predict
    private int AI_GEAR;

    // from Gearbox
    private double W_RPM;

    private double G_V_OverallRMS;

    private double G_V_Wheel1X;

    private double G_V_Wheel2X;

    private double G_V_Pinion1X;

    private double G_V_Pinion2X;

    private double G_V_GMF1X;

    private double G_V_GMF2X;

    // from Engine
    private double AC_h;

    private double AC_v;

    private double AC_a;

    private Date DATE;
}
