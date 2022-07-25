package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ENGTRNNG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEngine {
    @Id
    @GeneratedValue
    @Column(name="IDX")
    private long idx;

    @Column(name="SDAID")
    private String sdaId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="OPERDATE")
    private Date operateDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name="OPERTIME")
    private Date operateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="DATE")
    private Date operateDateTime;

    @Column(name="TIME")
    private long timeIndex;

    @Column(name="W_RPM")
    private double wRPM;

    @Column(name="E_V_OverallRMS")
    private double evOverallRms;

    @Column(name="E_V_1/2X")
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

    @Column(name="AI_Predict")
    private int AiPredict;
}