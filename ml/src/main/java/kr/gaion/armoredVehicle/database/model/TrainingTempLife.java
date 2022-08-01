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
    private Long idx;

    @Column(name="ACPOWER")
    private Float acPower;

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
}
