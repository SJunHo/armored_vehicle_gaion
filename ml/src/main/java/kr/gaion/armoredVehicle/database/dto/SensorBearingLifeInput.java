package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SensorBearingLifeInput {
    private int IDX;

    // AI Predict
    private int AI_Trip;

    private String AI_Trip_ALGO;

    private String AI_Trip_MODEL;

    private Date AI_Trip_DATE;

    // from Bearing
    private double B_OverallRMS;

    private double B_1X;

    private double B_6912BPFO;

    private double B_6912BPFI;

    private double B_6912BSF;

    private double B_6912FTF;

    private double B_32924BPFO;

    private double B_32924BPFI;

    private double B_32924BSF;

    private double B_32924FTF;

    private double B_32922BPFO;

    private double B_32922BPFI;

    private double B_32922BSF;

    private double B_32922FTF;

    private double B_CrestFactor;

    private double B_Demodulation;

    private double B_Fault1;

    private double B_Fault2;

    private double B_Temperature;
}
