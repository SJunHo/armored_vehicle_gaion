package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorWheelLifeInput {
    private int IDX;

    // AI Predict
    private Integer AI_Trip;

    private String AI_Trip_ALGO;

    private String AI_Trip_MODEL;

    private Date AI_Trip_DATE;

    // from Engine
    private Double W_2X;

    private Double W_3X;

    private Double W_Fault3;
}
