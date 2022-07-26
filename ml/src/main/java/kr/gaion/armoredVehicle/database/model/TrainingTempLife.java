package kr.gaion.armoredVehicle.database.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "TEMPLIFE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTempLife {
    @Id
    @GeneratedValue
    @Column(name="IDX")
    private long lifecycleId;

    @Column(name="ACPOWER")
    private float acPower;

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
}
