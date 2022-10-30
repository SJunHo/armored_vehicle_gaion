package kr.gaion.armoredVehicle.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Table(name = "BERDATA")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorBearing {
    @Id
    @GeneratedValue
    @Column(name="IDX")
    private long idx;

    @Column(name="SDAID")
    private String carId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="OPERDATE")
    private Date operateDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name="OPERTIME")
    private Time operateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="DATE")
    private Date operateDateTime;

    @Column(name="TIME")
    private Long timeIndex;

    @Column(name="AI_Predict")
    private Double aiPredict;

    @Column(name="AI_Algorithm")
    private String aiAlgorithm;

    @Column(name="AI_Model")
    private String aiModel;

    @Column(name="L_B_V_OverallRMS")
    private Double lbvOverallRMS;

    @Column(name="L_B_V_1X")
    private Double lbv1x;

    @Column(name="L_B_V_6912BPFO")
    private Double lbv6912bpfo;

    @Column(name="L_B_V_6912BPFI")
    private Double lbv6912bpfi;

    @Column(name="L_B_V_6912BSF")
    private Double lbv6912bsf;

    @Column(name="L_B_V_6912FTF")
    private Double lbv6912ftf;

    @Column(name="L_B_V_32924BPFO")
    private Double lbv32924bpfo;

    @Column(name="L_B_V_32924BPFI")
    private Double lbv32924bpfi;

    @Column(name="L_B_V_32924BSF")
    private Double lbv32924bsf;

    @Column(name="L_B_V_32924FTF")
    private Double lbv32924ftf;

    @Column(name="L_B_V_32922BPFO")
    private Double lbv32922bpfo;

    @Column(name="L_B_V_32922BPFI")
    private Double lbv32922bpfi;

    @Column(name="L_B_V_32922BSF")
    private Double lbv32922bsf;

    @Column(name="L_B_V_32922FTF")
    private Double lbv32922ftf;

    @Column(name="L_B_V_Crestfactor")
    private Double lbvCrestfactor;

    @Column(name="L_B_V_Demodulation")
    private Double lbvDemodulation;

    @Column(name="L_B_S_Fault1")
    private Double lbsFault1;

    @Column(name="L_B_S_Fault2")
    private Double lbsFault2;

    @Column(name="L_B_T_Temperature")
    private Double lbtTemperature;

    @Column(name="R_B_V_OverallRMS")
    private Double rbvOverallRMS;

    @Column(name="R_B_V_1X")
    private Double rbv1x;

    @Column(name="R_B_V_6912BPFO")
    private Double rbv6912bpfo;

    @Column(name="R_B_V_6912BPFI")
    private Double rbv6912bpfi;

    @Column(name="R_B_V_6912BSF")
    private Double rbv6912bsf;

    @Column(name="R_B_V_6912FTF")
    private Double rbv6912ftf;

    @Column(name="R_B_V_32924BPFO")
    private Double rbv32924bpfo;

    @Column(name="R_B_V_32924BPFI")
    private Double rbv32924bpfi;

    @Column(name="R_B_V_32924BSF")
    private Double rbv32924bsf;

    @Column(name="R_B_V_32924FTF")
    private Double rbv32924ftf;

    @Column(name="R_B_V_32922BPFO")
    private Double rbv32922bpfo;

    @Column(name="R_B_V_32922BPFI")
    private Double rbv32922bpfi;

    @Column(name="R_B_V_32922BSF")
    private Double rbv32922bsf;

    @Column(name="R_B_V_32922FTF")
    private Double rbv32922ftf;

    @Column(name="R_B_V_Crestfactor")
    private Double rbvCrestfactor;

    @Column(name="R_B_V_Demodulation")
    private Double rbvDemodulation;

    @Column(name="R_B_S_Fault1")
    private Double rbsFault1;

    @Column(name="R_B_S_Fault2")
    private Double rbsFault2;

    @Column(name="R_B_T_Temperature")
    private Double rbtTemperature;

    @Column(name="W_RPM")
    private Double wRpm;

    @Column(name="FILENM")
    private String filenm;
}
