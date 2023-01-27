package kr.gaion.armoredVehicle.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BERLIFEDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorBearingLife {
    @Id
    @GeneratedValue
    @Column(name = "IDX")
    private Long idx;

    @Column(name = "AI_Trip")
    private int aiTrip;

    @Column(name = "AI_Trip_ALGO")
    private String aiTripAlgo;

    @Column(name = "AI_Trip_MODEL")
    private String aiTripModel;

    @Column(name = "AI_Trip_DATE")
    private Date aiTripDate;

    @Column(name = "B_OverallRMS")
    private Double bOverallRMS;

    @Column(name = "B_1X")
    private Double b1x;

    @Column(name = "B_6912BPFO")
    private Double b6912bpfo;

    @Column(name = "B_6912BPFI")
    private Double b6912bpfi;

    @Column(name = "B_6912BSF")
    private Double b6912bsf;

    @Column(name = "B_6912FTF")
    private Double b6912ftf;

    @Column(name = "B_32924BPFO")
    private Double b32924bpfo;

    @Column(name = "B_32924BPFI")
    private Double b32924bpfi;

    @Column(name = "B_32924BSF")
    private Double b32924bsf;

    @Column(name = "B_32924FTF")
    private Double b32924ftf;

    @Column(name = "B_32922BPFO")
    private Double b32922bpfo;

    @Column(name = "B_32922BPFI")
    private Double b32922bpfi;

    @Column(name = "B_32922BSF")
    private Double b32922bsf;

    @Column(name = "B_32922FTF")
    private Double b32922ftf;

    @Column(name = "B_CrestFactor")
    private Double bCrestFactor;

    @Column(name = "B_Demodulation")
    private Double bDemodulation;

    @Column(name = "B_Fault1")
    private Double bFault1;

    @Column(name = "B_Fault2")
    private Double bFault2;

    @Column(name = "B_Temperature")
    private Double bTemperature;

    @Column(name = "FILENM")
    private String filenm;
}
