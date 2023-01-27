package kr.gaion.armoredVehicle.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "GRBLIFE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingGearboxLife {
    @Id
    @GeneratedValue
    @Column(name = "IDX")
    private Long idx;

    @Column(name = "Trip")
    private int trip;

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
