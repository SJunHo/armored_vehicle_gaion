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
public class TempLifeData {
    @Id
    @GeneratedValue
    @Column(name="seq")
    private long lifecycleId;

    @Column(name="acPower")
    private float acPower;

    @Column(name="cpuUtil")
    private float cpuUtil;

    @Column(name="diskAccesses")
    private float diskAccesses;

    @Column(name="diskBlocks")
    private float diskBlocks;

    @Column(name="diskUtil")
    private float diskUtil;

    @Column(name="instRetired")
    private float instRetired;

    @Column(name="lastLevel")
    private float lastLevel;

    @Column(name="memoryBus")
    private float memoryBus;

    @Column(name="coreCycle")
    private float coreCycle;

    @Column(name="time")
    private String time;
}
