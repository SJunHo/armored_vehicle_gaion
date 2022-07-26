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
    private long lifecycleId;

//    @Column(name="AI_Predict")
//    private double AiPredict;

    @Column(name="ACPOWER")
    private float acPower;

    @Column(name="AI_Algorithm")
    private double AiAlgorithm;

    @Column(name="AI_Model")
    private double AiModel;

    @Column(name="CPUUTIL")
    private float cpuUtil;

    @Column(name="DISKACCESSES")
    private float diskAccesses;

    @Column(name="DISKBLOCKS")
    private float diskBlocks;

    @Column(name="DISKUTIL")
    private float diskUtil;

    @Column(name="INSTRETIRED")
    private float instRetired;

    @Column(name="LASTLEVEL")
    private float lastLevel;

    @Column(name="MEMORYBUS")
    private float memoryBus;

    @Column(name="CORECYCLE")
    private float coreCycle;

    @Column(name="TIME")
    private String time;

    @Column(name="FILENM")
    private String filenm;
}
