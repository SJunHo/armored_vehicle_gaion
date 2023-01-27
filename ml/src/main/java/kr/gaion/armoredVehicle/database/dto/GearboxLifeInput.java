package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GearboxLifeInput {
    private int IDX;

    // label
    private int Trip;

    // from Gearbox
    private double G_OverallRMS;

    private double G_Wheel1X;

    private double G_Wheel2X;

    private double G_Pinion1X;

    private double G_Pinion2X;

    private double G_GMF1X;

    private double G_GMF2X;
}
