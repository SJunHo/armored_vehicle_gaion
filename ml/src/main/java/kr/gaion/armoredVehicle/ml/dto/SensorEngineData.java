package kr.gaion.armoredVehicle.ml.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import java.sql.Time;
import java.util.Date;

@Data
public class SensorEngineData {
    @JsonProperty("IDX")
    private long idx;

    @JsonProperty("SDAID")
    private String carId;

    @JsonProperty("OPERDATE")
    private Date operateDate;

    @JsonProperty("OPERTIME")
    private Time operateTime;

    @JsonProperty("DATE")
    private DateTime operateDateTime;

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

    @JsonProperty("E_V_OverallRMS")
    private double evOverallRms;

    @JsonProperty("E_V_1-2X")
    private double ev12x;

    @JsonProperty("E_V_1X")
    private double ev1x;

    @JsonProperty("E_V_Crestfactor")
    private double evCrestfactor;

    @JsonProperty("AC_h")
    private double ach;

    @JsonProperty("AC_v")
    private double acv;

    @JsonProperty("AC_a")
    private double aca;

    @JsonProperty("LA")
    private double la;

    @JsonProperty("LO")
    private double lo;

    @JsonProperty("FILENM")
    private String filenm;
}
