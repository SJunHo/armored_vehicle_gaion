package kr.gaion.armoredVehicle.ml.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;
import java.sql.Time;
import java.util.Date;

@Data
public class SensorBearingData {
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

    @JsonProperty("L_B_V_OverallRMS")
    private double lbvOverallRMS;

    @JsonProperty("L_B_V_1X")
    private double lbv1x;

    @JsonProperty("L_B_V_6912BPFO")
    private double lbv6912bpfo;

    @JsonProperty("L_B_V_6912BPFI")
    private double lbv6912bpfi;

    @JsonProperty("L_B_V_6912BSF")
    private double lbv6912bsf;

    @JsonProperty("L_B_V_6912FTF")
    private double lbv6912ftf;

    @JsonProperty("L_B_V_32924BPFO")
    private double lbv32924bpfo;

    @JsonProperty("L_B_V_32924BPFI")
    private double lbv32924bpfi;

    @JsonProperty("L_B_V_32924BSF")
    private double lbv32924bsf;

    @JsonProperty("L_B_V_32924FTF")
    private double lbv32924ftf;

    @JsonProperty("L_B_V_32922BPFO")
    private double lbv32922bpfo;

    @JsonProperty("L_B_V_32922BPFI")
    private double lbv32922bpfi;

    @JsonProperty("L_B_V_32922BSF")
    private double lbv32922bsf;

    @JsonProperty("L_B_V_32922FTF")
    private double lbv32922ftf;

    @JsonProperty("L_B_V_Crestfactor")
    private double lbvCrestfactor;

    @JsonProperty("L_B_V_Demodulation")
    private double lbvDemodulation;

    @JsonProperty("L_B_S_Fault1")
    private double lbsFault1;

    @JsonProperty("L_B_S_Fault2")
    private double lbsFault2;

    @JsonProperty("L_B_T_Temperature")
    private double lbtTemperature;

    @JsonProperty("R_B_V_OverallRMS")
    private double rbvOverallRMS;

    @JsonProperty("R_B_V_1X")
    private double rbv1x;

    @JsonProperty("R_B_V_6912BPFO")
    private double rbv6912bpfo;

    @JsonProperty("R_B_V_6912BPFI")
    private double rbv6912bpfi;

    @JsonProperty("R_B_V_6912BSF")
    private double rbv6912bsf;

    @JsonProperty("R_B_V_6912FTF")
    private double rbv6912ftf;

    @JsonProperty("R_B_V_32924BPFO")
    private double rbv32924bpfo;

    @JsonProperty("R_B_V_32924BPFI")
    private double rbv32924bpfi;

    @JsonProperty("R_B_V_32924BSF")
    private double rbv32924bsf;

    @JsonProperty("R_B_V_32924FTF")
    private double rbv32924ftf;

    @JsonProperty("R_B_V_32922BPFO")
    private double rbv32922bpfo;

    @JsonProperty("R_B_V_32922BPFI")
    private double rbv32922bpfi;

    @JsonProperty("R_B_V_32922BSF")
    private double rbv32922bsf;

    @JsonProperty("R_B_V_32922FTF")
    private double rbv32922ftf;

    @JsonProperty("R_B_V_Crestfactor")
    private double rbvCrestfactor;

    @JsonProperty("R_B_V_Demodulation")
    private double rbvDemodulation;

    @JsonProperty("R_B_S_Fault1")
    private double rbsFault1;

    @JsonProperty("R_B_S_Fault2")
    private double rbsFault2;

    @JsonProperty("R_B_T_Temperature")
    private double rbtTemperature;

    @JsonProperty("W_RPM")
    private double wRpm;

    @JsonProperty("FILENM")
    private String filenm;
}
