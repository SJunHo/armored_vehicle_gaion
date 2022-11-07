package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GRBDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorGearbox {
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

    // AI Predict Values
    @Column(name = "AI_GEAR")
    private String aiGear;

    // Ai Predict Algorithm
    @Column(name = "AI_ALGO")
    private String aiGearAlgorithm;

    // Ai Predict Model
    @Column(name = "AI_GEAR_MODEL")
    private String aiGearModel;

    // Ai Predict Date
    @Column(name = "AI_GEAR_DATE")
    private String aiGearDate;

    // User Judgement Values
    @Column(name = "USER_GEAR")
    private String userGear;

    // User Judgement ID
    @Column(name = "USER_GEAR_ID")
    private String userGearId;

    // User Judgement Date
    @Column(name = "USER_GEAR_DATE")
    private String userGearDate;

    // Sensor Values
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
    private String filenm;
}
