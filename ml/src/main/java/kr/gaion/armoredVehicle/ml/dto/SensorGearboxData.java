package kr.gaion.armoredVehicle.ml.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;
import java.sql.Time;
import java.util.Date;

@Data
public class SensorGearboxData {
    @JsonProperty("IDX")
    private long idx;

    @JsonProperty("SDAID")
    private String carId;

    @JsonProperty("OPERDATE")
    private Date operateDate;

    @JsonProperty("OPERTIME")
    private Time operateTime;

    @JsonProperty("DATE")
    private Date operateDateTime;

    @JsonProperty("TIME")
    private long timeIndex;

    @JsonProperty("AI_Predict")
    private Double aiPredict;

    @JsonProperty("AI_Algorithm")
    private String aiAlgorithm;

    @JsonProperty("AI_Model")
    private String aiModel;

    @JsonProperty("W_RPM")
    private double wrpm;

    @JsonProperty("G_V_OverallRMS")
    private double gvOverallRms;

    @JsonProperty("G_V_Wheel1X")
    private double gvWheel1x;

    @JsonProperty("G_V_Wheel2X")
    private double gvWheel2x;

    @JsonProperty("G_V_Pinion1X")
    private double gvPinion1x;

    @JsonProperty("G_V_Pinion2X")
    private double gvPinion2x;

    @JsonProperty("G_V_GMF1X")
    private double gvGmf1x;

    @JsonProperty("G_V_GMF2X")
    private double gvGmf2x;

    @JsonProperty("FILENM")
    private String filenm;
}
