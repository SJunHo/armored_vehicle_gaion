package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GRBTRNNG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingGearbox {
    @Id
    @GeneratedValue
    @Column(name = "IDX")
    private long idx;

    @Column(name = "SDAID")
    private String carId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "DATE")
    private Date operateDateTime;

    @Column(name = "TIME")
    private long timeIndex;

    @Column(name = "W_RPM")
    private double wrpm;

    @Column(name = "G_V_OverallRMS")
    private double gvOverallRms;

    @Column(name = "G_V_Wheel1X")
    private double gvWheel1x;

    @Column(name = "G_V_Wheel2X")
    private double gvWheel2x;

    @Column(name = "G_V_Pinion1X")
    private double gvPinion1x;

    @Column(name = "G_V_Pinion2X")
    private double gvPinion2x;

    @Column(name = "G_V_GMF1X")
    private double gvGmf1x;

    @Column(name = "G_V_GMF2X")
    private double gvGmf2x;

    @Column(name = "FILENM")
    private String fileNm;

    @Column(name = "AI_GEAR")
    private int aiGear;
}