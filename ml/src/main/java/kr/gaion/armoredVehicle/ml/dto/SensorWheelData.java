package kr.gaion.armoredVehicle.ml.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;
import java.sql.Time;
import java.util.Date;

@Data
public class SensorWheelData {
    @JsonProperty("IDX")
    private Integer idx;

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

    @JsonProperty("L_W_V_2X")
    private double lwv2x;

    @JsonProperty("L_W_V_3X")
    private double lwv3x;

    @JsonProperty("L_W_S_Fault3")
    private double lwsFault3;

    @JsonProperty("R_W_V_2X")
    private double rwv2x;

    @JsonProperty("R_W_V_3X")
    private double rwv3x;

    @JsonProperty("R_W_S_Fault3")
    private double rwsFault3;

    @JsonProperty("FILENM")
    private String filenm;
}
