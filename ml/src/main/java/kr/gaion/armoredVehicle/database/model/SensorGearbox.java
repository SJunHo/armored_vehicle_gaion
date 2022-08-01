package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "GRBDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorGearbox {
    @Id
    @GeneratedValue
    @Column(name="IDX")
    private long idx;

    @Column(name="SDAID")
    private String carId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="OPERDATE")
    private Date operateDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name="OPERTIME")
    private Time operateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="DATE")
    private DateTime operateDateTime;

    @Column(name="TIME")
    private long timeIndex;

    @Column(name="AI_Predict")
    private double aiPredict;

    @Column(name="AI_Algorithm")
    private double aiAlgorithm;

    @Column(name="AI_Model")
    private double aiModel;

    @Column(name="W_RPM")
    private double wRPM;

    @Column(name="G_V_OverallRMS")
    private double gvOverallRms;

    @Column(name="G_V_Wheel1X")
    private double gvWheel1x;

    @Column(name="G_V_Wheel2X")
    private double gvWheel2x;

    @Column(name="G_V_Pinion1X")
    private double gvPinion1x;

    @Column(name="G_V_Pinion2X")
    private double gvPinion2x;

    @Column(name="G_V_GMF1X")
    private double gvGmf1x;

    @Column(name="G_V_GMF2X")
    private double gvGmf2x;

    @Column(name="FILENM")
    private String filenm;
}