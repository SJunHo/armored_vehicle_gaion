package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EngineLifeInput {
    private int IDX;

    // label
    private int Trip;

    // from Engine
    private double E_OverallRMS;

    private double E_1_2X;

    private double E_1X;

    private double E_CrestFactor;
}
