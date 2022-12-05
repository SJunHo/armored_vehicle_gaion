package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ENGDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorEngine {
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
    @Column(name = "AI_ENGINE")
    private Integer aiEngine;

    // Ai Predict Algorithm
    @Column(name = "AI_ENGINE_ALGO")
    private String aiEngineAlgorithm;

    // Ai Predict Model
    @Column(name = "AI_ENGINE_MODEL")
    private String aiEngineModel;

    // Ai Predict Date
    @Column(name = "AI_ENGINE_DATE")
    private Date aiEngineDate;

    // User Judgement Values
    @Column(name = "USER_ENGINE")
    private Integer userEngine;

    // User Judgement ID
    @Column(name = "USER_ENGINE_ID")
    private String userEngineId;

    // User Judgement Date
    @Column(name = "USER_ENGINE_DATE")
    private Date userEngineDate;

    // Sensor Values
    @Column(name = "W_RPM")
    private double wrpm;

    @Column(name = "E_V_OverallRMS")
    private double evOverallRms;

    @Column(name = "E_V_1_2X")
    private double ev12x;

    @Column(name = "E_V_1X")
    private double ev1x;

    @Column(name = "E_V_Crestfactor")
    private double evCrestfactor;

    @Column(name = "AC_h")
    private double ach;

    @Column(name = "AC_v")
    private double acv;

    @Column(name = "AC_a")
    private double aca;

    @Column(name = "LA")
    private double la;

    @Column(name = "LO")
    private double lo;

    @Column(name = "FILENM")
    private String filenm;
}
