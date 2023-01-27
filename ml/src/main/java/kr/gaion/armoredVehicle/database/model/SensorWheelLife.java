package kr.gaion.armoredVehicle.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WHLLIFEDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorWheelLife {
    @Id
    @GeneratedValue
    @Column(name = "IDX")
    private Long idx;

    @Column(name = "AI_Trip")
    private int aiTrip;

    @Column(name = "AI_Trip_ALGO")
    private String aiTripAlgo;

    @Column(name = "AI_Trip_MODEL")
    private String aiTripModel;

    @Column(name = "AI_Trip_DATE")
    private Date aiTripDate;

    @Column(name = "W_2X")
    private Double w2x;

    @Column(name = "W_3X")
    private Double w3x;

    @Column(name = "W_Fault3")
    private Double wFault3;

    @Column(name = "FILENM")
    private String filenm;
}
