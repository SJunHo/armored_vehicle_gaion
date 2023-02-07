package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GRBLIFEDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorGearboxLife {
    @Id
    @GeneratedValue
    @Column(name = "IDX")
    private long idx;

    @Column(name = "SDAID")
    private String carId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "DATE")
    private Date operateDateTime;

    @Column(name = "AI_Trip")
    private Integer aiTrip;

    @Column(name = "AI_Trip_ALGO")
    private String aiTripAlgo;

    @Column(name = "AI_Trip_MODEL")
    private String aiTripModel;

    @Column(name = "AI_Trip_DATE")
    private Date aiTripDate;

    @Column(name = "G_OverallRMS")
    private Double gOverallRMS;

    @Column(name = "G_Wheel1X")
    private Double gWheel1x;

    @Column(name = "G_Wheel2X")
    private Double gWheel2x;

    @Column(name = "G_Pinion1X")
    private Double gPinion1x;

    @Column(name = "G_Pinion2X")
    private Double gPinion2x;

    @Column(name = "G_GMF1X")
    private Double gGmf1x;

    @Column(name = "G_GMF2X")
    private Double gGmf2x;

    @Column(name = "FILENM")
    private String filenm;
}
