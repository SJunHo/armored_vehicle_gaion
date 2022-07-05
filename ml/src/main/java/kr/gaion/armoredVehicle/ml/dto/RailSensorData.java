package kr.gaion.armoredVehicle.ml.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

@Data
public class RailSensorData {
  @JsonProperty("Round")
  private Integer round;

  private String esId;

  @JsonProperty("ID")
  private String id;

  @JsonProperty("Train No")
  private Integer trainNo;

  @JsonProperty("Car No")
  private Integer carNo;

  private String type;

  @JsonProperty("W/B")
  private String wb;

  @JsonProperty("L/R")
  private String lr;

  @JsonProperty("N/S")
  private String ns;

  @JsonProperty("1/2")
  private Integer oneTwo;

  @JsonProperty("Time")
  private Date time;

  @JsonProperty("Measurement Time")
  private Date measurementTime;

  private Double velocity;

  @JsonProperty("velocity-c")
  private Double velocityC;

  @JsonProperty("AirTemp")
  private Double airTemp;

  @JsonProperty("airTemp-c")
  private Double airTempC;

  @JsonProperty("Loadcell")
  private Double loadcell;

  @JsonProperty("Loadcell-c")
  private Double loadcellC;

  @JsonProperty("Vib")
  private Double vib;

  @JsonProperty("Vib-v")
  private Double vibV;

  @JsonProperty("Vib-c")
  private Double vibC;

  @JsonProperty("Temp")
  private Double temp;

  @JsonProperty("Temp-v")
  private Double tempV;

  @JsonProperty("Temp-c")
  private Double tempC;

  @JsonProperty("Sound")
  private Double sound;

  @JsonProperty("Sound-v")
  private Double soundV;

  @JsonProperty("Sound-c")
  private Double soundC;

  @JsonProperty("AE")
  private Double ae;

  @JsonProperty("AE-v")
  private Double aeV;

  @JsonProperty("AE-c")
  private Double aeC;

  @JsonProperty("Total_Value")
  private Double totalValue;

  @JsonProperty("Total_Count")
  private Double totalCount;

  @JsonProperty("ATV")
  private Double atv;

  @JsonProperty("ATC")
  private Double atc;

  @JsonProperty("defect_prob")
  private Double defectProb;

  private Double severity;

  @JsonProperty("rec_severity")
  private Double recSeverity;

  @JsonProperty("remaining_lifetime")
  private Double remainingLifetime;

  @JsonProperty("defect_score")
  private Double defectScore;

  @JsonProperty("defect_user")
  private Double defectUser;

  @JsonProperty("Entry Speed")
  private Double entrySpeed;

  @JsonProperty("Load-c")
  private Double loadC;

  @JsonProperty("Load-v")
  private Double loadV;

  @JsonProperty("Weighting State equation")
  private Double weightingStateEquation;

}
