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
    private Date DATE;
    // AI Predict
    private Integer AI_Trip;

    private String AI_Trip_ALGO;

    private String AI_Trip_MODEL;

    private Date AI_Trip_DATE;

    // from Bearing
    private Double B_OverallRMS;

    private Double B_1X;

    private Double B_6912BPFO;

    private Double B_6912BPFI;

    private Double B_6912BSF;

    private Double B_6912FTF;

    private Double B_32924BPFO;

    private Double B_32924BPFI;

    private Double B_32924BSF;

    private Double B_32924FTF;

    private Double B_32922BPFO;

    private Double B_32922BPFI;

    private Double B_32922BSF;

    private Double B_32922FTF;

    private Double B_CrestFactor;

    private Double B_Demodulation;

    private Double B_Fault1;

    private Double B_Fault2;

    private Double B_Temperature;
}
