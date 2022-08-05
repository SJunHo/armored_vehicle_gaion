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
@Table(name = "WHLDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorWheel {
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
    private long timeIndex;

    @Column(name="AI_Predict")
    private Double aiPredict;

    @Column(name="AI_Algorithm")
    private String aiAlgorithm;

    @Column(name="AI_Model")
    private String aiModel;

    @Column(name="W_RPM")
    private double wrpm;

    @Column(name="L_W_V_2X")
    private double lwv2x;

    @Column(name="L_W_V_3X")
    private double lwv3x;

    @Column(name="L_W_S_Fault3")
    private double lwsFault3;

    @Column(name="R_W_V_2X")
    private double rwv2x;

    @Column(name="R_W_V_3X")
    private double rwv3x;

    @Column(name="R_W_S_Fault3")
    private double rwsFault3;

    @Column(name="FILENM")
    private String filenm;
}
