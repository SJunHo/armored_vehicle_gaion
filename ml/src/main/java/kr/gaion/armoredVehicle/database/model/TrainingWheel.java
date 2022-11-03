package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WHLTRNNG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingWheel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "train_wheel_generator")
    @SequenceGenerator(name = "train_wheel_generator", sequenceName = "train_wheel_seq")
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
    private String fileNm;

    @Column(name = "AI_LW")
    private int aiLw;

    @Column(name = "AI_RW")
    private int aiRw;
}