package kr.gaion.armoredVehicle.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ENGLIFE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEngineLife {
    @Id
    @GeneratedValue
    @Column(name = "IDX")
    private Long idx;

    @Column(name = "Trip")
    private int trip;

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
