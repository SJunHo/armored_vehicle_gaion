package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.model.TrainingBearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingBearingRepository extends JpaRepository<TrainingBearing, Long> {
    // Bearing Left Ball
    @Query(value = " SELECT BERTRNNG.IDX, BERTRNNG.AI_LBSF, BERTRNNG.W_RPM, BERTRNNG.L_B_V_1X, BERTRNNG.L_B_V_6912BSF, BERTRNNG.L_B_V_32924BSF, BERTRNNG.L_B_V_32922BSF, " +
            " BERTRNNG.L_B_V_Crestfactor, BERTRNNG.L_B_V_Demodulation, BERTRNNG.L_B_S_Fault1, BERTRNNG.L_B_S_Fault2, BERTRNNG.L_B_T_Temperature, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
            " FROM `BERTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` ", nativeQuery = true)
    List<BearingLeftBallInterface> findBearingLeftBall();

    // Bearing Left Inside
    @Query(value = " SELECT BERTRNNG.IDX, BERTRNNG.AI_LBPFI, BERTRNNG.W_RPM, BERTRNNG.L_B_V_1X, BERTRNNG.L_B_V_6912BPFI, BERTRNNG.L_B_V_32924BPFI, BERTRNNG.L_B_V_32922BPFI, " +
            " BERTRNNG.L_B_V_Crestfactor, BERTRNNG.L_B_V_Demodulation, BERTRNNG.L_B_S_Fault1, BERTRNNG.L_B_S_Fault2, BERTRNNG.L_B_T_Temperature, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
            " FROM `BERTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` ", nativeQuery = true)
    List<BearingLeftInsideInterface> findBearingLeftInside();

    // Bearing Left Outside
    @Query(value = " SELECT BERTRNNG.IDX, BERTRNNG.AI_LBPFO, BERTRNNG.W_RPM, BERTRNNG.L_B_V_1X, BERTRNNG.L_B_V_6912BPFO, BERTRNNG.L_B_V_32924BPFO, BERTRNNG.L_B_V_32922BPFO, " +
            " BERTRNNG.L_B_V_Crestfactor, BERTRNNG.L_B_V_Demodulation, BERTRNNG.L_B_S_Fault1, BERTRNNG.L_B_S_Fault2, BERTRNNG.L_B_T_Temperature, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
            " FROM `BERTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` ", nativeQuery = true)
    List<BearingLeftOutsideInterface> findBearingLeftOutside();

    // Bearing Left Retainer
    @Query(value = " SELECT BERTRNNG.IDX, BERTRNNG.AI_LFTF, BERTRNNG.W_RPM, BERTRNNG.L_B_V_1X, BERTRNNG.L_B_V_6912FTF, BERTRNNG.L_B_V_32924FTF, BERTRNNG.L_B_V_32922FTF, " +
            " BERTRNNG.L_B_V_Crestfactor, BERTRNNG.L_B_V_Demodulation, BERTRNNG.L_B_S_Fault1, BERTRNNG.L_B_S_Fault2, BERTRNNG.L_B_T_Temperature, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
            " FROM `BERTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` ", nativeQuery = true)
    List<BearingLeftRetainerInterface> findBearingLeftRetainer();

    // Bearing Right Ball
    @Query(value = " SELECT BERTRNNG.IDX, BERTRNNG.AI_RBSF, BERTRNNG.W_RPM, BERTRNNG.R_B_V_1X, BERTRNNG.R_B_V_6912BSF, BERTRNNG.R_B_V_32924BSF, BERTRNNG.R_B_V_32922BSF, " +
            " BERTRNNG.R_B_V_Crestfactor, BERTRNNG.R_B_V_Demodulation, BERTRNNG.R_B_S_Fault1, BERTRNNG.R_B_S_Fault2, BERTRNNG.R_B_T_Temperature, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
            " FROM `BERTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
            " WHERE BERTRNNG.FILENM = '%s' ", nativeQuery = true)
    List<BearingRightBallInterface> findBearingRightBall();

    // Bearing Right Inside
    @Query(value = " SELECT BERTRNNG.IDX, BERTRNNG.AI_RBPFI, BERTRNNG.W_RPM, BERTRNNG.R_B_V_1X, BERTRNNG.R_B_V_6912BPFI, BERTRNNG.R_B_V_32924BPFI, BERTRNNG.R_B_V_32922BPFI, " +
            " BERTRNNG.R_B_V_Crestfactor, BERTRNNG.R_B_V_Demodulation, BERTRNNG.R_B_S_Fault1, BERTRNNG.R_B_S_Fault2, BERTRNNG.R_B_T_Temperature, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
            " FROM `BERTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` " +
            " WHERE BERTRNNG.FILENM = '%s' ", nativeQuery = true)
    List<BearingRightInsideInterface> findBearingRightInside();

    // Bearing Right Outside
    @Query(value = " SELECT BERTRNNG.IDX, BERTRNNG.AI_RBPFO, BERTRNNG.W_RPM, BERTRNNG.R_B_V_1X, BERTRNNG.R_B_V_6912BPFO, BERTRNNG.R_B_V_32924BPFO, BERTRNNG.R_B_V_32922BPFO, " +
            " BERTRNNG.R_B_V_Crestfactor, BERTRNNG.R_B_V_Demodulation, BERTRNNG.R_B_S_Fault1, BERTRNNG.R_B_S_Fault2, BERTRNNG.R_B_T_Temperature, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
            " FROM `BERTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` ", nativeQuery = true)
    List<BearingRightOutsideInterface> findBearingRightOutside();

    // Bearing Right Retainer
    @Query(value = " SELECT BERTRNNG.IDX, BERTRNNG.AI_RFTF, BERTRNNG.W_RPM, BERTRNNG.R_B_V_1X, BERTRNNG.R_B_V_6912FTF, BERTRNNG.R_B_V_32924FTF, BERTRNNG.R_B_V_32922FTF, " +
            " BERTRNNG.R_B_V_Crestfactor, BERTRNNG.R_B_V_Demodulation, BERTRNNG.R_B_S_Fault1, BERTRNNG.R_B_S_Fault2, BERTRNNG.R_B_T_Temperature, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, BERTRNNG.`DATE` " +
            " FROM `BERTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON BERTRNNG.`DATE` = ENGTRNNG.`DATE` ", nativeQuery = true)
    List<BearingRightRetainerInterface> findBearingRightRetainer();
}
