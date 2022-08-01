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

//    @Column(name="AI_Predict")
//    private Double AiPredict;

    @Column(name="ACPOWER")
    private Float acPower;

    @Column(name="AI_Algorithm")
    private String aiAlgorithm;

    @Column(name="AI_Model")
    private String aiModel;

    @Column(name="CPUUTIL")
    private Float cpuUtil;

    @Column(name="DISKACCESSES")
    private Float diskAccesses;

    @Column(name="DISKBLOCKS")
    private Float diskBlocks;

    @Column(name="DISKUTIL")
    private Float diskUtil;

    @Column(name="INSTRETIRED")
    private Float instRetired;

    @Column(name="LASTLEVEL")
    private Float lastLevel;

    @Column(name="MEMORYBUS")
    private Float memoryBus;

    @Column(name="CORECYCLE")
    private Float coreCycle;

    @Column(name="TIME")
    private String time;

    @Column(name="FILENM")
    private String filenm;
}
