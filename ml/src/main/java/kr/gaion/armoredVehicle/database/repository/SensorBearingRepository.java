package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.model.SensorBearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

// get *** predictedData => 작업자 판정 및 결과 조회 페이지에서 사용
// get USER *** DATA => CSV 다운로드 하는 데이터 불러올 때 사용
// find *** isNull => 모델 예측 수행 페이지에서 예측 안된 데이터 불러올 때 사용

@Repository
public interface SensorBearingRepository extends JpaRepository<SensorBearing, Long> {
    //get SDAID List
    @Query(value = "Select DISTINCT SDAID from BERDATA ", nativeQuery = true)
    List<String> findDistinctByCarId(String targetColumn);

    @Query(value = "Select DISTINCT AI_LBSF_MODEL from BERDATA where AI_LBSF_MODEL Is Not Null", nativeQuery = true)
    List<String> findDistinctByLBSFModel();

    @Query(value = "Select DISTINCT AI_LBPFO_MODEL from BERDATA where AI_LBPFO_MODEL Is Not Null", nativeQuery = true)
    List<String> findDistinctByLBPFOModel();

    @Query(value = "Select DISTINCT AI_LBPFI_MODEL from BERDATA where AI_LBPFI_MODEL Is Not Null", nativeQuery = true)
    List<String> findDistinctByLBSFIModel();

    @Query(value = "Select DISTINCT AI_LFTF_MODEL from BERDATA where AI_LFTF_MODEL Is Not Null", nativeQuery = true)
    List<String> findDistinctByLFTFModel();

    @Query(value = "Select DISTINCT AI_RBSF_MODEL from BERDATA where AI_RBSF_MODEL Is Not Null", nativeQuery = true)
    List<String> findDistinctByRBSFModel();

    @Query(value = "Select DISTINCT AI_RBPFO_MODEL from BERDATA where AI_RBPFO_MODEL Is Not Null", nativeQuery = true)
    List<String> findDistinctByRBPFOModel();

    @Query(value = "Select DISTINCT AI_RBPFI_MODEL from BERDATA where AI_RBPFI_MODEL Is Not Null", nativeQuery = true)
    List<String> findDistinctByRBSFIModel();

    @Query(value = "Select DISTINCT AI_RFTF_MODEL from BERDATA where AI_RFTF_MODEL Is Not Null", nativeQuery = true)
    List<String> findDistinctByRFTFModel();


    //get BLB PredictedData
    @Query(value = " Select B.IDX, B.SDAID, B.AI_LBSF, B.AI_LBSF_ALGO, B.AI_LBSF_MODEL, B.AI_LBSF_DATE, " +
            " B.USER_LBSF, B.USER_LBSF_ID, B.USER_LBSF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BSF, B.L_B_V_32924BSF, B.L_B_V_32922BSF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LBSF IS NOT NULL " +
            " AND `BERDATA`.AI_LBSF_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftBallInterface> getLeftBallAiLBSFPredictedData(String carId, String modelName, Date fromDate, Date toDate);

    //get BLO PredictedData
    @Query(value = "Select B.IDX, B.SDAID, B.AI_LBPFO, B.AI_LBPFO_ALGO, B.AI_LBPFO_MODEL, B.AI_LBPFO_DATE, " +
            " B.USER_LBPFO, B.USER_LBPFO_ID, B.USER_LBPFO_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFO, B.L_B_V_32924BPFO, B.L_B_V_32922BPFO, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LBPFO IS NOT NULL " +
            " AND `BERDATA`.AI_LBPFO_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftOutsideInterface> getLeftOutsideAiLBPFOPredictedData(String carId, String modelName, Date fromDate, Date toDate);

    //get BLI PredictedData
    @Query(value = "Select B.IDX, B.SDAID, B.AI_LBPFI, B.AI_LBPFI_ALGO, B.AI_LBPFI_MODEL, B.AI_LBPFI_DATE, " +
            " B.USER_LBPFI, B.USER_LBPFI_ID, B.USER_LBPFI_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFI, B.L_B_V_32924BPFI, B.L_B_V_32922BPFI, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LBPFI IS NOT NULL " +
            " AND `BERDATA`.AI_LBPFI_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftInsideInterface> getLeftInsideAiLBPFIPredictedData(String carId, String modelName, Date fromDate, Date toDate);

    //get BLR PredictedData
    @Query(value = "Select B.IDX, B.SDAID, B.AI_LFTF, B.AI_LFTF_ALGO, B.AI_LFTF_MODEL, B.AI_LFTF_DATE, " +
            " B.USER_LFTF, B.USER_LFTF_ID, B.USER_LFTF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912FTF, B.L_B_V_32924FTF, B.L_B_V_32922FTF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LFTF IS NOT NULL " +
            " AND `BERDATA`.AI_LFTF_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftRetainerInterface> getLeftRetainerAiLFTFPredictedData(String carId, String modelName, Date fromDate, Date toDate);

    //get BRB PredictedData
    @Query(value = "Select B.IDX, B.SDAID, B.AI_RBSF, B.AI_RBSF_ALGO, B.AI_RBSF_MODEL, B.AI_RBSF_DATE, " +
            " B.USER_RBSF, B.USER_RBSF_ID, B.USER_RBSF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BSF, B.R_B_V_32924BSF, B.R_B_V_32922BSF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RBSF IS NOT NULL " +
            " AND `BERDATA`.AI_RBSF_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightBallInterface> getRightBallAiRBSFPredictedData(String carId, String modelName, Date fromDate, Date toDate);

    //get BRO PredictedData
    @Query(value = "Select B.IDX, B.SDAID, B.AI_RBPFO, B.AI_RBPFO_ALGO, B.AI_RBPFO_MODEL, B.AI_RBPFO_DATE, " +
            " B.USER_RBPFO, B.USER_RBPFO_ID, B.USER_RBPFO_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFO, B.R_B_V_32924BPFO, B.R_B_V_32922BPFO, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE`  from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RBPFO IS NOT NULL " +
            " AND `BERDATA`.AI_RBPFO_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightOutsideInterface> getRightOutsideAiRBPFOPredictedData(String carId, String modelName, Date fromDate, Date toDate);

    //get BRI PredictedData
    @Query(value = "Select B.IDX, B.SDAID, B.AI_RBPFI, B.AI_RBPFI_ALGO, B.AI_RBPFI_MODEL, B.AI_RBPFI_DATE, " +
            " B.USER_RBPFI, B.USER_RBPFI_ID, B.USER_RBPFI_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFI, B.R_B_V_32924BPFI, B.R_B_V_32922BPFI, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RBPFI IS NOT NULL " +
            " AND `BERDATA`.AI_RBPFI_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightInsideInterface> getRightInsideAiRBPFIPredictedData(String carId, String modelName, Date fromDate, Date toDate);

    //get BRR PredictedData
    @Query(value = "Select B.IDX, B.SDAID, B.AI_RFTF, B.AI_RFTF_ALGO, B.AI_RFTF_MODEL, B.AI_RFTF_DATE, " +
            " B.USER_RFTF, B.USER_RFTF_ID, B.USER_RFTF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912FTF, B.R_B_V_32924FTF, B.R_B_V_32922FTF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RFTF IS NOT NULL " +
            " AND `BERDATA`.AI_RFTF_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B  " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightRetainerInterface> getRightRetainerAiRFTFPredictedData(String carId, String modelName, Date fromDate, Date toDate);

    // Bearing Left Ball
    @Query(value = " SELECT B.IDX, B.SDAID, B.AI_LBSF, B.AI_LBSF_ALGO, B.AI_LBSF_MODEL, B.AI_LBSF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BSF, B.L_B_V_32924BSF, B.L_B_V_32922BSF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LBSF IS NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftBallInterface> findSensorBearingLeftBallAiLBSFIsNull(String carId, Date fromDate, Date toDate);

    // Bearing Left Inside
    @Query(value = " SELECT B.IDX, B.SDAID, B.AI_LBPFI, B.AI_LBPFI_ALGO, B.AI_LBPFI_MODEL, B.AI_LBPFI_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFI, B.L_B_V_32924BPFI, B.L_B_V_32922BPFI, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LBPFI IS NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftInsideInterface> findSensorBearingLeftInsideAiLBPFIIsNull(String carId, Date fromDate, Date toDate);

    // Bearing Left Outside
    @Query(value = " SELECT B.IDX, B.SDAID, B.AI_LBPFO, B.AI_LBPFO_ALGO, B.AI_LBPFO_MODEL, B.AI_LBPFO_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFO, B.L_B_V_32924BPFO, B.L_B_V_32922BPFO, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LBPFO IS NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftOutsideInterface> findSensorBearingLeftOutsideAiLBPFOIsNull(String carId, Date fromDate, Date toDate);

    // Bearing Left Retainer
    @Query(value = " SELECT B.IDX, B.SDAID, B.AI_LFTF, B.AI_LFTF_ALGO, B.AI_LFTF_MODEL, B.AI_LFTF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912FTF, B.L_B_V_32924FTF, B.L_B_V_32922FTF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_LFTF IS NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftRetainerInterface> findSensorBearingLeftRetainerAiLFTFIsNull(String carId, Date fromDate, Date toDate);

    // Bearing Right Ball
    @Query(value = " SELECT B.IDX, B.SDAID, B.AI_RBSF, B.AI_RBSF_ALGO, B.AI_RBSF_MODEL, B.AI_RBSF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BSF, B.R_B_V_32924BSF, B.R_B_V_32922BSF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RBSF IS NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightBallInterface> findSensorBearingRightBallAiRBSFIsNull(String carId, Date fromDate, Date toDate);

    // Bearing Right Inside
    @Query(value = " SELECT B.IDX, B.SDAID, B.AI_RBPFI, B.AI_RBPFI_ALGO, B.AI_RBPFI_MODEL, B.AI_RBPFI_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFI, B.R_B_V_32924BPFI, B.R_B_V_32922BPFI, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RBPFI IS NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightInsideInterface> findSensorBearingRightInsideAiRBPFIIsNull(String carId, Date fromDate, Date toDate);

    // Bearing Right Outside
    @Query(value = " SELECT B.IDX, B.SDAID, B.AI_RBPFO, B.AI_RBPFO_ALGO, B.AI_RBPFO_MODEL, B.AI_RBPFO_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFO, B.R_B_V_32924BPFO, B.R_B_V_32922BPFO, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RBPFO IS NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightOutsideInterface> findSensorBearingRightOutsideAiRBPFOIsNull(String carId, Date fromDate, Date toDate);

    // Bearing Right Retainer
    @Query(value = " SELECT B.IDX, B.SDAID, B.AI_RFTF, B.AI_RFTF_ALGO, B.AI_RFTF_MODEL, B.AI_RFTF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912FTF, B.R_B_V_32924FTF, B.R_B_V_32922FTF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, B.`DATE` " +
            " FROM (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.AI_RFTF IS NULL AND `BERDATA`.DATE BETWEEN ?2 AND ?3) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = ENGDATA.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightRetainerInterface> findSensorBearingRightRetainerRFTFIsNull(String carId, Date fromDate, Date toDate);

    // get BLB's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.SDAID, B.USER_LBSF, B.USER_LBSF_ID, B.USER_LBSF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BSF, B.L_B_V_32924BSF, B.L_B_V_32922BSF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_LBSF IS NOT NULL " +
            " AND `BERDATA`.AI_LBSF_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftBallInterface> getLeftBallUserLBSFData(String carId, String modelName, Date fromDate, Date toDate);

    // get BLO's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.SDAID, B.USER_LBPFO, B.USER_LBPFO_ID, B.USER_LBPFO_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFO, B.L_B_V_32924BPFO, B.L_B_V_32922BPFO, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_LBPFO IS NOT NULL " +
            " AND `BERDATA`.AI_LBPFO_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftOutsideInterface> getLeftOutsideUserLBPFOData(String carId, String modelName, Date fromDate, Date toDate);

    // get BLI's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.SDAID, B.USER_LBPFI, B.USER_LBPFI_ID, B.USER_LBPFI_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912BPFI, B.L_B_V_32924BPFI, B.L_B_V_32922BPFI, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_LBPFI IS NOT NULL " +
            " AND `BERDATA`.AI_LBPFI_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftInsideInterface> getLeftInsideUserLBPFIData(String carId, String modelName, Date fromDate, Date toDate);

    // get BLR's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.SDAID, B.USER_LFTF, B.USER_LFTF_ID, B.USER_LFTF_DATE, " +
            " B.W_RPM, B.L_B_V_1X, B.L_B_V_6912FTF, B.L_B_V_32924FTF, B.L_B_V_32922FTF, " +
            " B.L_B_V_Crestfactor, B.L_B_V_Demodulation, B.L_B_S_Fault1, B.L_B_S_Fault2, B.L_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_LFTF IS NOT NULL " +
            " AND `BERDATA`.AI_LFTF_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingLeftRetainerInterface> getLeftRetainerUserLFTFData(String carId, String modelName, Date fromDate, Date toDate);

    // get BRB's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.SDAID, B.USER_RBSF, B.USER_RBSF_ID, B.USER_RBSF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BSF, B.R_B_V_32924BSF, B.R_B_V_32922BSF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_RBSF IS NOT NULL " +
            " AND `BERDATA`.AI_RBSF_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightBallInterface> getRightBallUserRBSFData(String carId, String modelName, Date fromDate, Date toDate);

    // get BRO's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.SDAID, B.USER_RBPFO, B.USER_RBPFO_ID, B.USER_RBPFO_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFO, B.R_B_V_32924BPFO, B.R_B_V_32922BPFO, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_RBPFO IS NOT NULL " +
            " AND `BERDATA`.AI_RBPFO_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightOutsideInterface> getRightOutsideUserRBPFOData(String carId, String modelName, Date fromDate, Date toDate);

    // get BRI's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.SDAID, B.USER_RBPFI, B.USER_RBPFI_ID, B.USER_RBPFI_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912BPFI, B.R_B_V_32924BPFI, B.R_B_V_32922BPFI, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_RBPFI IS NOT NULL " +
            " AND `BERDATA`.AI_RBPFI_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightInsideInterface> getRightInsideUserRBPFIData(String carId, String modelName, Date fromDate, Date toDate);

    // get BRR's User judgement values are not Null data
    @Query(value = "Select B.IDX, B.SDAID, B.USER_RFTF, B.USER_RFTF_ID, B.USER_RFTF_DATE, " +
            " B.W_RPM, B.R_B_V_1X, B.R_B_V_6912FTF, B.R_B_V_32924FTF, B.R_B_V_32922FTF, " +
            " B.R_B_V_Crestfactor, B.R_B_V_Demodulation, B.R_B_S_Fault1, B.R_B_S_Fault2, B.R_B_T_Temperature, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, B.`DATE` " +
            " from (Select * from `BERDATA` where `BERDATA`.SDAID = ?1 AND `BERDATA`.USER_RFTF IS NOT NULL " +
            " AND `BERDATA`.AI_RFTF_MODEL = ?2 AND `BERDATA`.DATE BETWEEN ?3 AND ?4) B " +
            " INNER JOIN `ENGDATA` ON B.`DATE` = `ENGDATA`.`DATE` AND B.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorBearingRightRetainerInterface> getRightRetainerUserRFTFData(String carId, String modelName, Date fromDate, Date toDate);
}
