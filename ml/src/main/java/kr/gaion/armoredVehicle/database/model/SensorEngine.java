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
    private Date operateDateTime;

    @Column(name="TIME")
    private Long timeIndex;

    @Column(name="AI_Predict")
    private Double aiPredict;

    @Column(name="AI_Algorithm")
    private String aiAlgorithm;

    @Column(name="AI_Model")
    private String aiModel;

    @Column(name="W_RPM")
    private Double wrpm;

    @Column(name="E_V_OverallRMS")
    private Double evOverallRms;

    @Column(name="E_V_1-2X")
    private Double ev12x;

    @Column(name="E_V_1X")
    private Double ev1x;

    @Column(name="E_V_Crestfactor")
    private Double evCrestfactor;

    @Column(name="AC_h")
    private Double ach;

    @Column(name="AC_v")
    private Double acv;

    @Column(name="AC_a")
    private Double aca;

    @Column(name="LA")
    private Double la;

    @Column(name="LO")
    private Double lo;

    @Column(name="FILENM")
    private String filenm;
}
