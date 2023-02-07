package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ENGLIFEDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorEngineLife {
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

    @Column(name = "E_OverallRMS")
    private Double eOverallRms;

    @Column(name = "E_1_2X")
    private Double e12x;

    @Column(name = "E_1X")
    private Double e1X;

    @Column(name = "E_CrestFactor")
    private Double eCrestFactor;

    @Column(name = "FILENM")
    private String filenm;
}
