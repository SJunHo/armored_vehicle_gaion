package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class EngineInput {
    private int IDX;

    // AI_Predict
    private int AI_ENGINE;

    // from Engine
    private double W_RPM;

    private double E_V_OverallRMS;

    private double E_V_1_2X;

    private double E_V_1X;

    private double E_V_Crestfactor;

    private double AC_h;

    private double AC_v;

    private double AC_a;

    private Date DATE;
}
