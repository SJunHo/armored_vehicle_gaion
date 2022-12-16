package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.model.SensorBearing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SensorBearingRepository extends JpaRepository<SensorBearing, Long> {
    //get SDAID List
    @Query(value = "Select DISTINCT SDAID from BERDATA b where ?1 is Not NULL", nativeQuery = true)
    List<String> findDistinctByCarId(String targetColumn);

    //    //get BLB PredictedData
    @Query(value = " Select B.IDX, B.AI_LBSF, B.AI_LBSF_ALGO, B.AI_LBSF_MODEL, B.AI_LBSF_DATE, " +
            " B.USER_LBSF, B.USER_LBSF_ID, B.USER_LBSF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BSF, B.L_B_V_32924BSF, B.L_B_V_32922BSF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LBSF IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorBearingLeftBallInterface> getLeftBallAiLBSFPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BLO PredictedData
    @Query(value = "Select B.IDX, B.AI_LBPFO, B.AI_LBPFO_ALGO, B.AI_LBPFO_MODEL, B.AI_LBPFO_DATE, " +
            " B.USER_LBPFO, B.USER_LBPFO_ID, B.USER_LBPFO_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFO, B.L_B_V_32924BPFO, B.L_B_V_32922BPFO, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LBPFO IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorBearingLeftOutsideInterface> getLeftOutsideAiLBPFOPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BLI PredictedData
    @Query(value = "Select B.IDX, B.AI_LBPFI, B.AI_LBPFI_ALGO, B.AI_LBPFI_MODEL, B.AI_LBPFI_DATE, " +
            " B.USER_LBPFI, B.USER_LBPFI_ID, B.USER_LBPFI_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFI, B.L_B_V_32924BPFI, B.L_B_V_32922BPFI, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LBPFI IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorBearingLeftInsideInterface> getLeftInsideAiLBPFIPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BLR PredictedData
    @Query(value = "Select B.IDX, B.AI_LFTF, B.AI_LFTF_ALGO, B.AI_LFTF_MODEL, B.AI_LFTF_DATE, " +
            " B.USER_LFTF, B.USER_LFTF_ID, B.USER_LFTF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912FTF, B.L_B_V_32924FTF, B.L_B_V_32922FTF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LFTF IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorBearingLeftRetainerInterface> getLeftRetainerAiLFTFPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BRB PredictedData
    @Query(value = "Select B.IDX, B.AI_RBSF, B.AI_RBSF_ALGO, B.AI_RBSF_MODEL, B.AI_RBSF_DATE, " +
            " B.USER_RBSF, B.USER_RBSF_ID, B.USER_RBSF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BSF, B.R_B_V_32924BSF, B.R_B_V_32922BSF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RBSF IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorBearingRightBallInterface> getRightBallAiRBSFPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BRO PredictedData
    @Query(value = "Select B.IDX, B.AI_RBPFO, B.AI_RBPFO_ALGO, B.AI_RBPFO_MODEL, B.AI_RBPFO_DATE, " +
            " B.USER_RBPFO, B.USER_RBPFO_ID, B.USER_RBPFO_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFO, B.R_B_V_32924BPFO, B.R_B_V_32922BPFO, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE`  from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RBPFO IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorBearingRightOutsideInterface> getRightOutsideAiRBPFOPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BRI PredictedData
    @Query(value = "Select B.IDX, B.AI_RBPFI, B.AI_RBPFI_ALGO, B.AI_RBPFI_MODEL, B.AI_RBPFI_DATE, " +
            " B.USER_RBPFI, B.USER_RBPFI_ID, B.USER_RBPFI_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFI, B.R_B_V_32924BPFI, B.R_B_V_32922BPFI, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RBPFI IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorBearingRightInsideInterface> getRightInsideAiRBPFIPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    //get BRR PredictedData
    @Query(value = "Select B.IDX, B.AI_RFTF, B.AI_RFTF_ALGO, B.AI_RFTF_MODEL, B.AI_RFTF_DATE, " +
            " B.USER_RFTF, B.USER_RFTF_ID, B.USER_RFTF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912FTF, B.R_B_V_32924FTF, B.R_B_V_32922FTF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA`. where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RFTF IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorBearingRightRetainerInterface> getRightRetainerAiRFTFPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    // Bearing Left Ball
    @Query(value = " SELECT B.IDX, B.AI_LBSF, B.AI_LBSF_ALGO, B.AI_LBSF_MODEL, B.AI_LBSF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BSF, B.L_B_V_32924BSF, B.L_B_V_32922BSF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from BERDATA. where BERDATA.AI_LBSF IS NULL) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` ", nativeQuery = true)
    Page<SensorBearingLeftBallInterface> findSensorBearingLeftBallAiLBSFIsNull(Pageable pageable);

    // Bearing Left Inside
    @Query(value = " SELECT B.IDX, B.AI_LBPFI, B.AI_LBPFI_ALGO, B.AI_LBPFI_MODEL, B.AI_LBPFI_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFI, B.L_B_V_32924BPFI, B.L_B_V_32922BPFI, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from BERDATA. where BERDATA.AI_LBPFI IS NULL) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` ", nativeQuery = true)
    Page<SensorBearingLeftInsideInterface> findSensorBearingLeftInsideAiLBPFIIsNull(Pageable pageable);

    // Bearing Left Outside
    @Query(value = " SELECT B.IDX, B.AI_LBPFO, B.AI_LBPFO_ALGO, B.AI_LBPFO_MODEL, B.AI_LBPFO_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFO, B.L_B_V_32924BPFO, B.L_B_V_32922BPFO, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from BERDATA. where BERDATA.AI_LBPFO IS NULL) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` ", nativeQuery = true)
    Page<SensorBearingLeftOutsideInterface> findSensorBearingLeftOutsideAiLBPFOIsNull(Pageable pageable);

    // Bearing Left Retainer
    @Query(value = " SELECT B.IDX, B.AI_LFTF, B.AI_LFTF_ALGO, B.AI_LFTF_MODEL, B.AI_LFTF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912FTF, B.L_B_V_32924FTF, B.L_B_V_32922FTF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from BERDATA. where BERDATA.AI_LFTF IS NULL) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` ", nativeQuery = true)
    Page<SensorBearingLeftRetainerInterface> findSensorBearingLeftRetainerAiLFTFIsNull(Pageable pageable);

    // Bearing Right Ball
    @Query(value = " SELECT B.IDX, B.AI_RBSF, B.AI_RBSF_ALGO, B.AI_RBSF_MODEL, B.AI_RBSF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BSF, B.R_B_V_32924BSF, B.R_B_V_32922BSF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from BERDATA. where BERDATA.AI_RBSF IS NULL) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` ", nativeQuery = true)
    Page<SensorBearingRightBallInterface> findSensorBearingRightBallAiRBSFIsNull(Pageable pageable);

    // Bearing Right Inside
    @Query(value = " SELECT B.IDX, B.AI_RBPFI, B.AI_RBPFI_ALGO, B.AI_RBPFI_MODEL, B.AI_RBPFI_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFI, B.R_B_V_32924BPFI, B.R_B_V_32922BPFI, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from BERDATA. where BERDATA.AI_RBPFI IS NULL) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` ", nativeQuery = true)
    Page<SensorBearingRightInsideInterface> findSensorBearingRightInsideAiRBPFIIsNull(Pageable pageable);

    // Bearing Right Outside
    @Query(value = " SELECT B.IDX, B.AI_RBPFO, B.AI_RBPFO_ALGO, B.AI_RBPFO_MODEL, B.AI_RBPFO_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFO, B.R_B_V_32924BPFO, B.R_B_V_32922BPFO, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from BERDATA. where BERDATA.AI_RBPFO IS NULL) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` ", nativeQuery = true)
    Page<SensorBearingRightOutsideInterface> findSensorBearingRightOutsideAiRBPFOIsNull(Pageable pageable);

    // Bearing Right Retainer
    @Query(value = " SELECT B.IDX, B.AI_RFTF, B.AI_RFTF_ALGO, B.AI_RFTF_MODEL, B.AI_RFTF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912FTF, B.R_B_V_32924FTF, B.R_B_V_32922FTF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from BERDATA. where BERDATA.AI_RFTF IS NULL) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` ", nativeQuery = true)
    Page<SensorBearingRightRetainerInterface> findSensorBearingRightRetainerRFTFIsNull(Pageable pageable);

    // get BLB's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.USER_LBSF, B.USER_LBSF_ID, B.USER_LBSF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BSF, B.L_B_V_32924BSF, B.L_B_V_32922BSF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_LBSF IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftBallInterface> getLeftBallUserLBSFData(String carId, Date fromDate, Date toDate);

    // get BLO's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.USER_LBPFO, B.USER_LBPFO_ID, B.USER_LBPFO_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFO, B.L_B_V_32924BPFO, B.L_B_V_32922BPFO, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_LBPFO IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftOutsideInterface> getLeftOutsideUserLBPFOData(String carId, Date fromDate, Date toDate);

    // get BLI's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.USER_LBPFI, B.USER_LBPFI_ID, B.USER_LBPFI_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFI, B.L_B_V_32924BPFI, B.L_B_V_32922BPFI, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_LBPFI IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftInsideInterface> getLeftInsideUserLBPFIData(String carId, Date fromDate, Date toDate);

    // get BLR's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.USER_LFTF, B.USER_LFTF_ID, B.USER_LFTF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912FTF, B.L_B_V_32924FTF, B.L_B_V_32922FTF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_LFTF IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftRetainerInterface> getLeftRetainerUserLFTFData(String carId, Date fromDate, Date toDate);

    // get BRB's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.USER_RBSF, B.USER_RBSF_ID, B.USER_RBSF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BSF, B.R_B_V_32924BSF, B.R_B_V_32922BSF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_RBSF IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightBallInterface> getRightBallUserRBSFData(String carId, Date fromDate, Date toDate);

    // get BRO's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.USER_RBPFO, B.USER_RBPFO_ID, B.USER_RBPFO_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFO, B.R_B_V_32924BPFO, B.R_B_V_32922BPFO, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_RBPFO IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightOutsideInterface> getRightOutsideUserRBPFOData(String carId, Date fromDate, Date toDate);

    // get BRI's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.USER_RBPFI, B.USER_RBPFI_ID, B.USER_RBPFI_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFI, B.R_B_V_32924BPFI, B.R_B_V_32922BPFI, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_RBPFI IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightInsideInterface> getRightInsideUserRBPFIData(String carId, Date fromDate, Date toDate);

    // get BRR's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.USER_RFTF, B.USER_RFTF_ID, B.USER_RFTF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912FTF, B.R_B_V_32924FTF, B.R_B_V_32922FTF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_RFTF IS NOT NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightRetainerInterface> getRightRetainerUserRFTFData(String carId, Date fromDate, Date toDate);
}
