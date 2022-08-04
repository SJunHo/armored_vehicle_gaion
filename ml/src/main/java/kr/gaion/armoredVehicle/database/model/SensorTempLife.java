package kr.gaion.armoredVehicle.database.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TEMPLIFEDATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorTempLife {
    @Id
    @GeneratedValue
    @Column(name="IDX")
    private Long idx;

//    @Column(name="ACPOWER")
//    private Double acPower;

    @Column(name="AI_Predict")
    private Double AiPredict;

    @Column(name="AI_Algorithm")
    private String aiAlgorithm;

    @Column(name="AI_Model")
    private String aiModel;

    @Column(name="CPUUTIL")
    private Double cpuUtil;

    @Column(name="DISKACCESSES")
    private Double diskAccesses;

    @Column(name="DISKBLOCKS")
    private Double diskBlocks;

    @Column(name="DISKUTIL")
    private Double diskUtil;

    @Column(name="INSTRETIRED")
    private Double instRetired;

    @Column(name="LASTLEVEL")
    private Double lastLevel;

    @Column(name="MEMORYBUS")
    private Double memoryBus;

    @Column(name="CORECYCLE")
    private Double coreCycle;

    @Column(name="TIME")
    private String time;

    @Column(name="FILENM")
    private String filenm;
}
