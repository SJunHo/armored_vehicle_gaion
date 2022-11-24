package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.model.SensorBearing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface SensorBearingRepository extends JpaRepository<SensorBearing, Long> {
    //get SDAID List
    @Query(value = "Select DISTINCT SDAID from BERDATA b where ?1 is Not NULL", nativeQuery = true)
    List<String> findDistinctByCarId(String targetColumn);

    //get BLB PredictedData
    @Query(value = "Select `BERDATA`.IDX, `BERDATA`.AI_LBSF, `BERDATA`.AI_LBSF_ALGO, `BERDATA`.AI_LBSF_MODEL, `BERDATA`.AI_LBSF_DATE, " +
            " `BERDATA`.USER_LBSF, `BERDATA`.USER_LBSF_ID, `BERDATA`.USER_LBSF_DATE, " +
            " `BERDATA`.W_RPM, `BERDATA`.L_B_V_1X, `BERDATA`.L_B_V_6912BSF, `BERDATA`.L_B_V_32924BSF, `BERDATA`.L_B_V_32922BSF, " +
            " `BERDATA`.L_B_V_Crestfactor, `BERDATA`.L_B_V_Demodulation, `BERDATA`.L_B_S_Fault1, `BERDATA`.L_B_S_Fault2, `BERDATA`.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `BERDATA`.`DATE` from `BERDATA` " +
            " LEFT JOIN `ENGDATA` ON `BERDATA`.`DATE` = `ENGDATA`.`DATE` AND `BERDATA`.SDAID = `ENGDATA`.SDAID " +
            " WHERE `BERDATA`.AI_LBSF IS NOT NULL AND `BERDATA`.SDAID = ?1 AND `BERDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<SensorBearingLeftBallInterface> getLeftBallAiLBSFPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BLO PredictedData
    @Query(value = "Select `BERDATA`.IDX, `BERDATA`.AI_LBPFO, `BERDATA`.AI_LBPFO_ALGO, `BERDATA`.AI_LBPFO_MODEL, `BERDATA`.AI_LBPFO_DATE, " +
            " `BERDATA`.USER_LBPFO, `BERDATA`.USER_LBPFO_ID, `BERDATA`.USER_LBPFO_DATE, " +
            " `BERDATA`.W_RPM, `BERDATA`.L_B_V_1X, `BERDATA`.L_B_V_6912BPFO, `BERDATA`.L_B_V_32924BPFO, `BERDATA`.L_B_V_32922BPFO, " +
            " `BERDATA`.L_B_V_Crestfactor, `BERDATA`.L_B_V_Demodulation, `BERDATA`.L_B_S_Fault1, `BERDATA`.L_B_S_Fault2, `BERDATA`.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `BERDATA`.`DATE` from `BERDATA` " +
            " LEFT JOIN `ENGDATA` ON `BERDATA`.`DATE` = `ENGDATA`.`DATE` AND `BERDATA`.SDAID = `ENGDATA`.SDAID " +
            " WHERE `BERDATA`.AI_LBPFO IS NOT NULL AND `BERDATA`.SDAID = ?1 AND `BERDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<SensorBearingLeftOutsideInterface> getLeftOutsideAiLBPFOPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BLI PredictedData
    @Query(value = "Select `BERDATA`.IDX, `BERDATA`.AI_LBPFI, `BERDATA`.AI_LBPFI_ALGO, `BERDATA`.AI_LBPFI_MODEL, `BERDATA`.AI_LBPFI_DATE, " +
            " `BERDATA`.USER_LBPFI, `BERDATA`.USER_LBPFI_ID, `BERDATA`.USER_LBPFI_DATE, " +
            " `BERDATA`.W_RPM, `BERDATA`.L_B_V_1X, `BERDATA`.L_B_V_6912BPFI, `BERDATA`.L_B_V_32924BPFI, `BERDATA`.L_B_V_32922BPFI, " +
            " `BERDATA`.L_B_V_Crestfactor, `BERDATA`.L_B_V_Demodulation, `BERDATA`.L_B_S_Fault1, `BERDATA`.L_B_S_Fault2, `BERDATA`.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `BERDATA`.`DATE` from `BERDATA` " +
            " LEFT JOIN `ENGDATA` ON `BERDATA`.`DATE` = `ENGDATA`.`DATE` AND `BERDATA`.SDAID = `ENGDATA`.SDAID " +
            " WHERE `BERDATA`.AI_LBPFI IS NOT NULL AND `BERDATA`.SDAID = ?1 AND `BERDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<SensorBearingLeftInsideInterface> getLeftInsideAiLBPFIPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BLR PredictedData
    @Query(value = "Select `BERDATA`.IDX, `BERDATA`.AI_LFTF, `BERDATA`.AI_LFTF_ALGO, `BERDATA`.AI_LFTF_MODEL, `BERDATA`.AI_LFTF_DATE, " +
            " `BERDATA`.USER_LFTF, `BERDATA`.USER_LFTF_ID, `BERDATA`.USER_LFTF_DATE, " +
            " `BERDATA`.W_RPM, `BERDATA`.L_B_V_1X, `BERDATA`.L_B_V_6912FTF, `BERDATA`.L_B_V_32924FTF, `BERDATA`.L_B_V_32922FTF, " +
            " `BERDATA`.L_B_V_Crestfactor, `BERDATA`.L_B_V_Demodulation, `BERDATA`.L_B_S_Fault1, `BERDATA`.L_B_S_Fault2, `BERDATA`.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `BERDATA`.`DATE` from `BERDATA` " +
            " LEFT JOIN `ENGDATA` ON `BERDATA`.`DATE` = `ENGDATA`.`DATE` AND `BERDATA`.SDAID = `ENGDATA`.SDAID " +
            " WHERE `BERDATA`.AI_LFTF IS NOT NULL AND `BERDATA`.SDAID = ?1 AND `BERDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<SensorBearingLeftRetainerInterface> getLeftRetainerAiLFTFPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BRB PredictedData
    @Query(value = "Select `BERDATA`.IDX, `BERDATA`.AI_RBSF, `BERDATA`.AI_RBSF_ALGO, `BERDATA`.AI_RBSF_MODEL, `BERDATA`.AI_RBSF_DATE, " +
            " `BERDATA`.USER_RBSF, `BERDATA`.USER_RBSF_ID, `BERDATA`.USER_RBSF_DATE, " +
            " `BERDATA`.W_RPM, `BERDATA`.R_B_V_1X, `BERDATA`.R_B_V_6912BSF, `BERDATA`.R_B_V_32924BSF, `BERDATA`.R_B_V_32922BSF, " +
            " `BERDATA`.R_B_V_Crestfactor, `BERDATA`.R_B_V_Demodulation, `BERDATA`.R_B_S_Fault1, `BERDATA`.R_B_S_Fault2, `BERDATA`.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `BERDATA`.`DATE` from `BERDATA` " +
            " LEFT JOIN `ENGDATA` ON `BERDATA`.`DATE` = `ENGDATA`.`DATE` AND `BERDATA`.SDAID = `ENGDATA`.SDAID " +
            " WHERE `BERDATA`.AI_RBSF IS NOT NULL AND `BERDATA`.SDAID = ?1 AND `BERDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<SensorBearingRightBallInterface> getRightBallAiRBSFPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BRO PredictedData
    @Query(value = "Select `BERDATA`.IDX, `BERDATA`.AI_RBPFO, `BERDATA`.AI_RBPFO_ALGO, `BERDATA`.AI_RBPFO_MODEL, `BERDATA`.AI_RBPFO_DATE, " +
            " `BERDATA`.USER_RBPFO, `BERDATA`.USER_RBPFO_ID, `BERDATA`.USER_RBPFO_DATE, " +
            " `BERDATA`.W_RPM, `BERDATA`.R_B_V_1X, `BERDATA`.R_B_V_6912BPFO, `BERDATA`.R_B_V_32924BPFO, `BERDATA`.R_B_V_32922BPFO, " +
            " `BERDATA`.R_B_V_Crestfactor, `BERDATA`.R_B_V_Demodulation, `BERDATA`.R_B_S_Fault1, `BERDATA`.R_B_S_Fault2, `BERDATA`.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `BERDATA`.`DATE`  from `BERDATA` " +
            " LEFT JOIN `ENGDATA` ON `BERDATA`.`DATE` = `ENGDATA`.`DATE` AND `BERDATA`.SDAID = `ENGDATA`.SDAID " +
            " WHERE `BERDATA`.AI_RBPFO IS NOT NULL AND `BERDATA`.SDAID = ?1 AND `BERDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<SensorBearingRightOutsideInterface> getRightOutsideAiRBPFOPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BRI PredictedData
    @Query(value = "Select `BERDATA`.IDX, `BERDATA`.AI_RBPFI, `BERDATA`.AI_RBPFI_ALGO, `BERDATA`.AI_RBPFI_MODEL, `BERDATA`.AI_RBPFI_DATE, " +
            " `BERDATA`.USER_RBPFI, `BERDATA`.USER_RBPFI_ID, `BERDATA`.USER_RBPFI_DATE, " +
            " `BERDATA`.W_RPM, `BERDATA`.R_B_V_1X, `BERDATA`.R_B_V_6912BPFI, `BERDATA`.R_B_V_32924BPFI, `BERDATA`.R_B_V_32922BPFI, " +
            " `BERDATA`.R_B_V_Crestfactor, `BERDATA`.R_B_V_Demodulation, `BERDATA`.R_B_S_Fault1, `BERDATA`.R_B_S_Fault2, `BERDATA`.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `BERDATA`.`DATE` from `BERDATA` " +
            " LEFT JOIN `ENGDATA` ON `BERDATA`.`DATE` = `ENGDATA`.`DATE` AND `BERDATA`.SDAID = `ENGDATA`.SDAID " +
            " WHERE `BERDATA`.AI_RBPFI IS NOT NULL AND `BERDATA`.SDAID = ?1 AND `BERDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<SensorBearingRightInsideInterface> getRightInsideAiRBPFIPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BRR PredictedData
    @Query(value = "Select `BERDATA`.IDX, `BERDATA`.AI_RFTF, `BERDATA`.AI_RFTF_ALGO, `BERDATA`.AI_RFTF_MODEL, `BERDATA`.AI_RFTF_DATE, " +
            " `BERDATA`.USER_RFTF, `BERDATA`.USER_RFTF_ID, `BERDATA`.USER_RFTF_DATE, " +
            " `BERDATA`.W_RPM, `BERDATA`.R_B_V_1X, `BERDATA`.R_B_V_6912FTF, `BERDATA`.R_B_V_32924FTF, `BERDATA`.R_B_V_32922FTF, " +
            " `BERDATA`.R_B_V_Crestfactor, `BERDATA`.R_B_V_Demodulation, `BERDATA`.R_B_S_Fault1, `BERDATA`.R_B_S_Fault2, `BERDATA`.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `BERDATA`.`DATE` from `BERDATA` " +
            " LEFT JOIN `ENGDATA` ON `BERDATA`.`DATE` = `ENGDATA`.`DATE` AND `BERDATA`.SDAID = `ENGDATA`.SDAID " +
            " WHERE `BERDATA`.AI_RFTF IS NOT NULL AND `BERDATA`.SDAID = ?1 AND `BERDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<SensorBearingRightRetainerInterface> getRightRetainerAiRFTFPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    // Bearing Left Ball
    @Query(value = " SELECT BERDATA.IDX, BERDATA.AI_LBSF, BERDATA.AI_LBSF_ALGO, BERDATA.AI_LBSF_MODEL, BERDATA.AI_LBSF_DATE, " +
            " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BSF, BERDATA.L_B_V_32924BSF, BERDATA.L_B_V_32922BSF, " +
            " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
            " FROM `BERDATA` " +
            " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE BERDATA.AI_LBSF IS NULL ", nativeQuery = true)
    Page<SensorBearingLeftBallInterface> findSensorBearingLeftBallAiLBSFIsNull(Pageable pageable);

    // Bearing Left Inside
    @Query(value = " SELECT BERDATA.IDX, BERDATA.AI_LBPFI, BERDATA.AI_LBPFI_ALGO, BERDATA.AI_LBPFI_MODEL, BERDATA.AI_LBPFI_DATE, " +
            " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BPFI, BERDATA.L_B_V_32924BPFI, BERDATA.L_B_V_32922BPFI, " +
            " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
            " FROM `BERDATA` " +
            " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE BERDATA.AI_LBPFI IS NULL ", nativeQuery = true)
    Page<SensorBearingLeftInsideInterface> findSensorBearingLeftInsideAiLBPFIIsNull(Pageable pageable);

    // Bearing Left Outside
    @Query(value = " SELECT BERDATA.IDX, BERDATA.AI_LBPFO, BERDATA.AI_LBPFO_ALGO, BERDATA.AI_LBPFO_MODEL, BERDATA.AI_LBPFO_DATE, " +
            " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912BPFO, BERDATA.L_B_V_32924BPFO, BERDATA.L_B_V_32922BPFO, " +
            " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
            " FROM `BERDATA` " +
            " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE BERDATA.AI_LBPFO IS NULL ", nativeQuery = true)
    Page<SensorBearingLeftOutsideInterface> findSensorBearingLeftOutsideAiLBPFOIsNull(Pageable pageable);

    // Bearing Left Retainer
    @Query(value = " SELECT BERDATA.IDX, BERDATA.AI_LFTF, BERDATA.AI_LFTF_ALGO, BERDATA.AI_LFTF_MODEL, BERDATA.AI_LFTF_DATE, " +
            " BERDATA.W_RPM, BERDATA.L_B_V_1X, BERDATA.L_B_V_6912FTF, BERDATA.L_B_V_32924FTF, BERDATA.L_B_V_32922FTF, " +
            " BERDATA.L_B_V_Crestfactor, BERDATA.L_B_V_Demodulation, BERDATA.L_B_S_Fault1, BERDATA.L_B_S_Fault2, BERDATA.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
            " FROM `BERDATA` " +
            " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE BERDATA.AI_LFTF IS NULL ", nativeQuery = true)
    Page<SensorBearingLeftRetainerInterface> findSensorBearingLeftRetainerAiLFTFIsNull(Pageable pageable);

    // Bearing Right Ball
    @Query(value = " SELECT BERDATA.IDX, BERDATA.AI_RBSF, BERDATA.AI_RBSF_ALGO, BERDATA.AI_RBSF_MODEL, BERDATA.AI_RBSF_DATE, " +
            " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BSF, BERDATA.R_B_V_32924BSF, BERDATA.R_B_V_32922BSF, " +
            " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
            " FROM `BERDATA` " +
            " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE BERDATA.AI_RBSF IS NULL ", nativeQuery = true)
    Page<SensorBearingRightBallInterface> findSensorBearingRightBallAiRBSFIsNull(Pageable pageable);

    // Bearing Right Inside
    @Query(value = " SELECT BERDATA.IDX, BERDATA.AI_RBPFI, BERDATA.AI_RBPFI_ALGO, BERDATA.AI_RBPFI_MODEL, BERDATA.AI_RBPFI_DATE, " +
            " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BPFI, BERDATA.R_B_V_32924BPFI, BERDATA.R_B_V_32922BPFI, " +
            " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
            " FROM `BERDATA` " +
            " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE BERDATA.AI_RBPFI IS NULL ", nativeQuery = true)
    Page<SensorBearingRightInsideInterface> findSensorBearingRightInsideAiRBPFIIsNull(Pageable pageable);

    // Bearing Right Outside
    @Query(value = " SELECT BERDATA.IDX, BERDATA.AI_RBPFO, BERDATA.AI_RBPFO_ALGO, BERDATA.AI_RBPFO_MODEL, BERDATA.AI_RBPFO_DATE, " +
            " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912BPFO, BERDATA.R_B_V_32924BPFO, BERDATA.R_B_V_32922BPFO, " +
            " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
            " FROM `BERDATA` " +
            " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE BERDATA.AI_RBPFO IS NULL ", nativeQuery = true)
    Page<SensorBearingRightOutsideInterface> findSensorBearingRightOutsideAiRBPFOIsNull(Pageable pageable);

    // Bearing Right Retainer
    @Query(value = " SELECT BERDATA.IDX, BERDATA.AI_RFTF, BERDATA.AI_RFTF_ALGO, BERDATA.AI_RFTF_MODEL, BERDATA.AI_RFTF_DATE, " +
            " BERDATA.W_RPM, BERDATA.R_B_V_1X, BERDATA.R_B_V_6912FTF, BERDATA.R_B_V_32924FTF, BERDATA.R_B_V_32922FTF, " +
            " BERDATA.R_B_V_Crestfactor, BERDATA.R_B_V_Demodulation, BERDATA.R_B_S_Fault1, BERDATA.R_B_S_Fault2, BERDATA.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, BERDATA.`DATE` " +
            " FROM `BERDATA` " +
            " INNER JOIN `ENGDATA` ON BERDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE BERDATA.AI_RFTF IS NULL ", nativeQuery = true)
    Page<SensorBearingRightRetainerInterface> findSensorBearingRightRetainerRFTFIsNull(Pageable pageable);
}
