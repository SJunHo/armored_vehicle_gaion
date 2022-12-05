package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "BERDATA")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorBearing {
    @Id
    @GeneratedValue
    @Column(name = "IDX")
    private long idx;

    @Column(name = "SDAID")
    private String carId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "DATE")
    private Date operateDateTime;

    @Column(name = "TIME")
    private long timeIndex;

    // Ai Predict Values
    @Column(name = "AI_LBSF")
    private Integer aiLbsf;

    @Column(name = "AI_LBPFI")
    private Integer aiLbpfi;

    @Column(name = "AI_LBPFO")
    private Integer aiLbpfo;

    @Column(name = "AI_LFTF")
    private Integer aiLftf;

    @Column(name = "AI_RBSF")
    private Integer aiRbsf;

    @Column(name = "AI_RBPFI")
    private Integer aiRbpfi;

    @Column(name = "AI_RBPFO")
    private Integer aiRbpfo;

    @Column(name = "AI_RFTF")
    private Integer aiRftf;

    // Ai Predict Algorithm
    @Column(name = "AI_LBSF_ALGO")
    private String aiLbsfAlgorithm;

    @Column(name = "AI_LBPFI_ALGO")
    private String aiLbpfiAlgorithm;

    @Column(name = "AI_LBPFO_ALGO")
    private String aiLbpfoAlgorithm;

    @Column(name = "AI_LFTF_ALGO")
    private String aiLftfAlgorithm;

    @Column(name = "AI_RBSF_ALGO")
    private String aiRbsfAlgorithm;

    @Column(name = "AI_RBPFI_ALGO")
    private String aiRbpfiAlgorithm;

    @Column(name = "AI_RBPFO_ALGO")
    private String aiRbpfoAlgorithm;

    @Column(name = "AI_RFTF_ALGO")
    private String aiRftfAlgorithm;

    // Ai Predict Model
    @Column(name = "AI_LBSF_MODEL")
    private String aiLbsfModel;

    @Column(name = "AI_LBPFI_MODEL")
    private String aiLbpfiModel;

    @Column(name = "AI_LBPFO_MODEL")
    private String aiLbpfoModel;

    @Column(name = "AI_LFTF_MODEL")
    private String aiLftfModel;

    @Column(name = "AI_RBSF_MODEL")
    private String aiRbsfModel;

    @Column(name = "AI_RBPFI_MODEL")
    private String aiRbpfiModel;

    @Column(name = "AI_RBPFO_MODEL")
    private String aiRbpfoModel;

    @Column(name = "AI_RFTF_MODEL")
    private String aiRftfModel;

    // Ai Predict Date
    @Column(name = "AI_LBSF_DATE")
    private Date aiLbsfDate;

    @Column(name = "AI_LBPFI_DATE")
    private Date aiLbpfiDate;

    @Column(name = "AI_LBPFO_DATE")
    private Date aiLbpfoDate;

    @Column(name = "AI_LFTF_DATE")
    private Date aiLftfDate;

    @Column(name = "AI_RBSF_DATE")
    private Date aiRbsfDate;

    @Column(name = "AI_RBPFI_DATE")
    private Date aiRbpfiDate;

    @Column(name = "AI_RBPFO_DATE")
    private Date aiRbpfoDate;

    @Column(name = "AI_RFTF_DATE")
    private Date aiRftfDate;

    // User Judgement Values
    @Column(name = "USER_LBSF")
    private Integer userLbsf;

    @Column(name = "USER_LBPFI")
    private Integer userLbpfi;

    @Column(name = "USER_LBPFO")
    private Integer userLbpfo;

    @Column(name = "USER_LFTF")
    private Integer userLftf;

    @Column(name = "USER_RBSF")
    private Integer userRbsf;

    @Column(name = "USER_RBPFI")
    private Integer userRbpfi;

    @Column(name = "USER_RBPFO")
    private Integer userRbpfo;

    @Column(name = "USER_RFTF")
    private Integer userRftf;

    // User Judgement ID
    @Column(name = "USER_LBSF_ID")
    private String userLbsfId;

    @Column(name = "USER_LBPFI_ID")
    private String userLbpfiId;

    @Column(name = "USER_LBPFO_ID")
    private String userLbpfoId;

    @Column(name = "USER_LFTF_ID")
    private String userLftfId;

    @Column(name = "USER_RBSF_ID")
    private String userRbsfId;

    @Column(name = "USER_RBPFI_ID")
    private String userRbpfiId;

    @Column(name = "USER_RBPFO_ID")
    private String userRbpfoId;

    @Column(name = "USER_RFTF_ID")
    private String userRftfId;

    // User Judgement Date
    @Column(name = "USER_LBSF_DATE")
    private Date userLbsfDate;

    @Column(name = "USER_LBPFI_DATE")
    private Date userLbpfiDate;

    @Column(name = "USER_LBPFO_DATE")
    private Date userLbpfoDate;

    @Column(name = "USER_LFTF_DATE")
    private Date userLftfDate;

    @Column(name = "USER_RBSF_DATE")
    private Date userRbsfDate;

    @Column(name = "USER_RBPFI_DATE")
    private Date userRbpfiDate;

    @Column(name = "USER_RBPFO_DATE")
    private Date userRbpfoDate;

    @Column(name = "USER_RFTF_DATE")
    private Date userRftfDate;

    // Sensor Values
    @Column(name = "L_B_V_OverallRMS")
    private double lbvOverallRMS;

    @Column(name = "L_B_V_1X")
    private double lbv1x;

    @Column(name = "L_B_V_6912BPFO")
    private double lbv6912bpfo;

    @Column(name = "L_B_V_6912BPFI")
    private double lbv6912bpfi;

    @Column(name = "L_B_V_6912BSF")
    private double lbv6912bsf;

    @Column(name = "L_B_V_6912FTF")
    private double lbv6912ftf;

    @Column(name = "L_B_V_32924BPFO")
    private double lbv32924bpfo;

    @Column(name = "L_B_V_32924BPFI")
    private double lbv32924bpfi;

    @Column(name = "L_B_V_32924BSF")
    private double lbv32924bsf;

    @Column(name = "L_B_V_32924FTF")
    private double lbv32924ftf;

    @Column(name = "L_B_V_32922BPFO")
    private double lbv32922bpfo;

    @Column(name = "L_B_V_32922BPFI")
    private double lbv32922bpfi;

    @Column(name = "L_B_V_32922BSF")
    private double lbv32922bsf;

    @Column(name = "L_B_V_32922FTF")
    private double lbv32922ftf;

    @Column(name = "L_B_V_Crestfactor")
    private double lbvCrestfactor;

    @Column(name = "L_B_V_Demodulation")
    private double lbvDemodulation;

    @Column(name = "L_B_S_Fault1")
    private double lbsFault1;

    @Column(name = "L_B_S_Fault2")
    private double lbsFault2;

    @Column(name = "L_B_T_Temperature")
    private double lbtTemperature;

    @Column(name = "R_B_V_OverallRMS")
    private double rbvOverallRMS;

    @Column(name = "R_B_V_1X")
    private double rbv1x;

    @Column(name = "R_B_V_6912BPFO")
    private double rbv6912bpfo;

    @Column(name = "R_B_V_6912BPFI")
    private double rbv6912bpfi;

    @Column(name = "R_B_V_6912BSF")
    private double rbv6912bsf;
    @Column(name = "R_B_V_6912FTF")

    private double rbv6912ftf;
    @Column(name = "R_B_V_32924BPFO")

    private double rbv32924bpfo;
    @Column(name = "R_B_V_32924BPFI")

    private double rbv32924bpfi;
    @Column(name = "R_B_V_32924BSF")

    private double rbv32924bsf;
    @Column(name = "R_B_V_32924FTF")

    private double rbv32924ftf;
    @Column(name = "R_B_V_32922BPFO")
    private double rbv32922bpfo;

    @Column(name = "R_B_V_32922BPFI")
    private double rbv32922bpfi;

    @Column(name = "R_B_V_32922BSF")
    private double rbv32922bsf;

    @Column(name = "R_B_V_32922FTF")
    private double rbv32922ftf;

    @Column(name = "R_B_V_Crestfactor")
    private double rbvCrestfactor;

    @Column(name = "R_B_V_Demodulation")
    private double rbvDemodulation;

    @Column(name = "R_B_S_Fault1")
    private double rbsFault1;

    @Column(name = "R_B_S_Fault2")
    private double rbsFault2;

    @Column(name = "R_B_T_Temperature")
    private double rbtTemperature;

    @Column(name = "W_RPM")
    private double wRpm;

    @Column(name = "FILENM")
    private String filenm;
}
