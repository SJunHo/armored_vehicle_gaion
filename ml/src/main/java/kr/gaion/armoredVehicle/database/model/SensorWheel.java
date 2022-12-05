package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WHLDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorWheel {
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
    @Column(name = "AI_LW")
    private Integer aiLw;

    @Column(name = "AI_RW")
    private Integer aiRw;

    // Ai Predict Algorithm
    @Column(name = "AI_LW_ALGO")
    private String aiLwAlgorithm;

    @Column(name = "AI_RW_ALGO")
    private String aiRwAlgorithm;

    // Ai Predict Model
    @Column(name = "AI_LW_MODEL")
    private String aiLwModel;

    @Column(name = "AI_RW_MODEL")
    private String aiRwModel;

    // Ai Predict Date
    @Column(name = "AI_LW_DATE")
    private Date aiLwDate;

    @Column(name = "AI_RW_DATE")
    private Date aiRwDate;

    // User Judgement Values
    @Column(name = "USER_LW")
    private Integer userLw;

    @Column(name = "USER_RW")
    private Integer userRw;

    // User Judgement ID
    @Column(name = "USER_LW_ID")
    private String userLwId;

    @Column(name = "USER_RW_ID")
    private String userRwId;

    // User Judgement Date
    @Column(name = "USER_LW_DATE")
    private Date userLwDate;

    @Column(name = "USER_RW_DATE")
    private Date userRwDate;

    // Sensor Values
    @Column(name = "W_RPM")
    private double wrpm;

    @Column(name = "L_W_V_2X")
    private double lwv2x;

    @Column(name = "L_W_V_3X")
    private double lwv3x;

    @Column(name = "L_W_S_Fault3")
    private double lwsFault3;

    @Column(name = "R_W_V_2X")
    private double rwv2x;

    @Column(name = "R_W_V_3X")
    private double rwv3x;

    @Column(name = "R_W_S_Fault3")
    private double rwsFault3;

    @Column(name = "FILENM")
    private String filenm;
}
