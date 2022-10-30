package kr.gaion.armoredVehicle.ml.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class SensorTempLifeData {
    @JsonProperty("IDX")
    private Long idx;

//    @JsonProperty("ACPOWER")
//    private Double acPower;

    @JsonProperty("AI_Predict")
    private Double AiPredict;

    @JsonProperty("AI_Algorithm")
    private String aiAlgorithm;

    @JsonProperty("AI_Model")
    private String aiModel;

    @JsonProperty("CPUUTIL")
    private Double cpuUtil;

    @JsonProperty("DISKACCESSES")
    private Double diskAccesses;

    @JsonProperty("DISKBLOCKS")
    private Double diskBlocks;

    @JsonProperty("DISKUTIL")
    private Double diskUtil;

    @JsonProperty("INSTRETIRED")
    private Double instRetired;

    @JsonProperty("LASTLEVEL")
    private Double lastLevel;

    @JsonProperty("MEMORYBUS")
    private Double memoryBus;

    @JsonProperty("CORECYCLE")
    private Double coreCycle;

    @JsonProperty("TIME")
    private String time;

    @JsonProperty("FILENM")
    private String filenm;
}
