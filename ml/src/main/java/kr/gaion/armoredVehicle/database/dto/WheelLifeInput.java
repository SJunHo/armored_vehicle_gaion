package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class WheelLifeInput {
    private int IDX;

    // label
    private int Trip;

    // from Engine
    private double W_2X;

    private double W_3X;

    private double W_Fault3;

    private Date date;
}
