package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.model.SensorBearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorBearingRepository extends JpaRepository<SensorBearing, Long> {
    // Bearing Left Ball
    @Query(value = " SELECT b.AI_LBSF, b.AI_LBSF_ALGO, b.AI_LBSF_MODEL, b.AI_LBSF_DATE, " +
            " b.USER_LBSF, b.USER_LBSF_ID, b.USER_LBSF_DATE, " +
            " b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BSF, b.L_B_V_32924BSF, b.L_B_V_32922BSF, " +
            " b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, " +
            " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
            " FROM BERDATA b, ENGDATA e " +
            " WHERE b.`DATE` = e.`DATE` AND b.AI_LBSF is Null ", nativeQuery = true)
    List<SensorBearingLeftBallInput> findSensorBearingLeftBallAiLBSFIsNull();

    // Bearing Left Inside
    @Query(value = " SELECT b.AI_LBPFI, b.AI_LBPFI_ALGO, b.AI_LBPFI_MODEL, b.AI_LBPFI_DATE, " +
            " b.USER_LBPFI, b.USER_LBPFI_ID, b.USER_LBPFI_DATE, " +
            " b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BPFI, b.L_B_V_32924BPFI, b.L_B_V_32922BPFI, " +
            " b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, " +
            " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
            " FROM BERDATA b, ENGDATA e " +
            " WHERE b.`DATE` = e.`DATE` AND b.AI_LBPFI is Null ", nativeQuery = true)
    List<SensorBearingLeftInsideInput> findSensorBearingLeftInsideAiLBPFIIsNull();

    // Bearing Left Outside
    @Query(value = " SELECT b.AI_LBPFO, b.AI_LBPFO_ALGO, b.AI_LBPFO_MODEL, b.AI_LBPFO_DATE, " +
            " b.USER_LBPFO, b.USER_LBPFO_ID, b.USER_LBPFO_DATE, " +
            " b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BPFO, b.L_B_V_32924BPFO, b.L_B_V_32922BPFO, " +
            " b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, " +
            " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
            " FROM BERDATA b, ENGDATA e ", nativeQuery = true)
    List<SensorBearingLeftOutsideInput> findSensorBearingLeftOutsideAiLBPFOIsNull();

    // Bearing Left Retainer
    @Query(value = " SELECT b.AI_LFTF, b.AI_LFTF_ALGO, b.AI_LFTF_MODEL, b.AI_LFTF_DATE, " +
            " b.USER_LFTF, b.USER_LFTF_ID, b.USER_LFTF_DATE, " +
            " b.W_RPM, b.L_B_V_1X, b.L_B_V_6912FTF, b.L_B_V_32924FTF, b.L_B_V_32922FTF, " +
            " b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, " +
            " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
            " FROM BERDATA b, ENGDATA e " +
            " WHERE b.`DATE` = e.`DATE` AND b.AI_LFTF is Null ", nativeQuery = true)
    List<SensorBearingLeftRetainerInput> findSensorBearingLeftRetainerAiLFTFIsNull();

    // Bearing Right Ball
    @Query(value = " SELECT b.AI_RBSF, b.AI_RBSF_ALGO, b.AI_RBSF_MODEL, b.AI_RBSF_DATE, " +
            " b.USER_RBSF, b.USER_RBPFO_ID, b.USER_RBSF_DATE, " +
            " b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BSF, b.R_B_V_32924BSF, b.R_B_V_32922BPFI, " +
            " b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, " +
            " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
            " FROM BERDATA b, ENGDATA e " +
            " WHERE b.`DATE` = e.`DATE` AND b.AI_RBSF is Null ", nativeQuery = true)
    List<SensorBearingRightBallInput> findSensorBearingRightBallAiRBSFIsNull();

    // Bearing Right Inside
    @Query(value = " SELECT b.AI_RBPFI, b.AI_RBPFI_ALGO, b.AI_RBPFI_MODEL, b.AI_RBPFI_DATE, " +
            " b.USER_RBPFI, b.USER_RBPFI_ID, b.USER_RBPFI_DATE, " +
            " b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BPFI, b.R_B_V_32924BPFI, b.R_B_V_32922BPFI, " +
            " b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, " +
            " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
            " FROM BERDATA b, ENGDATA e " +
            " WHERE b.`DATE` = e.`DATE` AND b.AI_RBPFI is Null ", nativeQuery = true)
    List<SensorBearingRightInsideInput> findSensorBearingRightInsideAiRBPFIIsNull();

    // Bearing Right Outside
    @Query(value = " SELECT b.AI_RBPFO, b.AI_RBPFO_ALGO, b.AI_RBPFO_MODEL, b.AI_RBPFO_DATE, " +
            " b.USER_RBPFO, b.USER_RBPFO_ID, b.USER_RBPFO_DATE, " +
            " b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BPFO, b.R_B_V_32924BPFO, b.R_B_V_32922BPFO, " +
            " b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, " +
            " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
            " FROM BERDATA b, ENGDATA e " +
            " WHERE b.`DATE` = e.`DATE` AND b.AI_RBPFO is Null ", nativeQuery = true)
    List<SensorBearingRightOutsideInput> findSensorBearingRightOutsideAiRBPFOIsNull();

    // Bearing Right Retainer
    @Query(value = " SELECT b.AI_RFTF, b.AI_RFTF_ALGO, b.AI_RFTF_MODEL, b.AI_RFTF_DATE, " +
            " b.USER_RFTF, b.USER_RFTF_ID, b.USER_RFTF_DATE, " +
            " b.W_RPM, b.R_B_V_1X, b.R_B_V_6912FTF, b.R_B_V_32924FTF, b.R_B_V_32922FTF, " +
            " b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, " +
            " e.AC_h, e.AC_v, e.AC_a, b.`DATE` " +
            " FROM BERDATA b, ENGDATA e " +
            " WHERE b.`DATE` = e.`DATE` AND b.AI_RFTF is Null ", nativeQuery = true)
    List<SensorBearingRightRetainerInput> findSensorBearingRightRetainerRFTFIsNull();
}
