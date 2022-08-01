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
@Table(name = "ENGDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorEngine {
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

    @Column(name="E_V_OverallRMS")
    private double evOverallRms;

    @Column(name="E_V_1-2X")
    private double ev12x;

    @Column(name="E_V_1X")
    private double ev1x;

    @Column(name="E_V_Crestfactor")
    private double evCrestfactor;

    @Column(name="AC_h")
    private double ach;

    @Column(name="AC_v")
    private double acv;

    @Column(name="AC_a")
    private double aca;

    @Column(name="LA")
    private double la;

    @Column(name="LO")
    private double lo;

    @Column(name="FILENM")
    private String filenm;
}
