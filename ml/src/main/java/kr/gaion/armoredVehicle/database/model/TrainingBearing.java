package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BERTRNNG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingBearing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "train_bearing_generator")
    @SequenceGenerator(name = "train_bearing_generator", sequenceName = "train_bearing_seq")
    @Column(name = "IDX")
    private long idx;

    @Column(name = "SDAID")
    private String carId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "OPERDATE")
    private Date operateDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "OPERTIME")
    private Date operateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "DATE")
    private Date operateDateTime;

    @Column(name = "TIME")
    private long timeIndex;

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
    private double wrpm;

    @Column(name = "TRNNGTYPE")
    private String trnngtype;

    @Column(name = "FILENM")
    private String fileNm;

    @Column(name = "AI_LBPFO")
    private int aiLbpfo;

    @Column(name = "AI_LBPFI")
    private int aiLbpfi;

    @Column(name = "AI_LBSF")
    private int aiLbsf;

    @Column(name = "AI_LFTF")
    private int aiLftf;

    @Column(name = "AI_RBPFO")
    private int aiRbpfo;

    @Column(name = "AI_RBPFI")
    private int aiRbpfi;

    @Column(name = "AI_RBSF")
    private int aiRbsf;

    @Column(name = "AI_RFTF")
    private int aiRftf;
}