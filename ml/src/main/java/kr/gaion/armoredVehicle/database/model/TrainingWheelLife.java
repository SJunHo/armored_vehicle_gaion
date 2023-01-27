package kr.gaion.armoredVehicle.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "WHLLIFE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingWheelLife {
    @Id
    @GeneratedValue
    @Column(name = "IDX")
    private Long idx;

    @Column(name = "Trip")
    private int trip;

    @Column(name = "W_2X")
    private Double w2x;

    @Column(name = "W_3X")
    private Double w3x;

    @Column(name = "W_Fault3")
    private Double wFault3;

    @Column(name = "FILENM")
    private String filenm;
}
