package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorWheelLeftInterface;
import kr.gaion.armoredVehicle.database.dto.SensorWheelRightInterface;
import kr.gaion.armoredVehicle.database.model.SensorWheel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SensorWheelRepository extends JpaRepository<SensorWheel, Long> {
    @Query(value = "Select DISTINCT SDAID from WHLDATA ", nativeQuery = true)
    List<String> findDistinctByCarId(String targetColumn);

    @Query(value = "Select W.IDX, W.SDAID, W.AI_LW, W.AI_LW_ALGO, W.AI_LW_MODEL, W.AI_LW_DATE, " +
            " W.USER_LW, W.USER_LW_ID, W.USER_LW_DATE, " +
            " W.W_RPM, W.L_W_V_2X, W.L_W_V_3X, W.L_W_S_Fault3, W.`DATE`, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a from (Select * from `WHLDATA` WHERE `WHLDATA`.AI_LW IS NOT NULL AND `WHLDATA`.SDAID = ?1 AND `WHLDATA`.DATE BETWEEN ?2 AND ?3) W " +
            " INNER JOIN `ENGDATA` ON W.`DATE` = `ENGDATA`.`DATE` AND W.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorWheelLeftInterface> getLeftWheelAiLWPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    @Query(value = "Select W.IDX, W.SDAID, W.AI_RW, W.AI_RW_ALGO, W.AI_RW_MODEL, W.AI_RW_DATE, " +
            " W.USER_RW, W.USER_RW_ID, W.USER_RW_DATE, " +
            " W.W_RPM, W.R_W_V_2X, W.R_W_V_3X, W.R_W_S_Fault3, W.`DATE`, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a from (Select * from `WHLDATA` WHERE `WHLDATA`.AI_RW IS NOT NULL AND `WHLDATA`.SDAID = ?1 AND `WHLDATA`.DATE BETWEEN ?2 AND ?3) W " +
            " INNER JOIN `ENGDATA` ON W.`DATE` = `ENGDATA`.`DATE` AND W.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    Page<SensorWheelRightInterface> getRightWheelAiRWPredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    // Wheel Left
    @Query(value = " SELECT W.IDX, W.SDAID, W.AI_LW, W.AI_LW_ALGO, W.AI_LW_MODEL, W.AI_LW_DATE, " +
            " W.W_RPM, W.L_W_V_2X, W.L_W_V_3X, W.L_W_S_Fault3, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, W.`DATE` " +
            " FROM (Select * from `WHLDATA` WHERE `WHLDATA`.SDAID = ?1 AND `WHLDATA`.AI_LW IS NULL AND `WHLDATA`.DATE BETWEEN ?2 AND ?3) W " +
            " INNER JOIN `ENGDATA` ON W.`DATE` = ENGDATA.`DATE` AND W.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorWheelLeftInterface> findSensorWheelLeftAiLWIsNull(String carId, Date fromDate, Date toDate);

    // Wheel Right
    @Query(value = " SELECT W.IDX, W.SDAID, W.AI_RW, W.AI_RW_ALGO, W.AI_RW_MODEL, W.AI_RW_DATE, " +
            " W.W_RPM, W.R_W_V_2X, W.R_W_V_3X, W.R_W_S_Fault3, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, W.`DATE` " +
            " FROM (Select * from `WHLDATA` WHERE `WHLDATA`.SDAID = ?1 AND `WHLDATA`.AI_RW IS NULL AND `WHLDATA`.DATE BETWEEN ?2 AND ?3) W " +
            " INNER JOIN `ENGDATA` ON W.`DATE` = ENGDATA.`DATE` AND W.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorWheelRightInterface> findSensorWheelRightAiRWIsNull(String carId, Date fromDate, Date toDate);

    // get WL's User judgement values are not Null data
    @Query(value = "Select W.IDX, W.SDAID, W.USER_LW, W.USER_LW_ID, W.USER_LW_DATE, " +
            " W.W_RPM, W.L_W_V_2X, W.L_W_V_3X, W.L_W_S_Fault3, W.`DATE`, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a from (Select * from `WHLDATA` WHERE `WHLDATA`.USER_LW IS NOT NULL AND `WHLDATA`.SDAID = ?1 AND `WHLDATA`.DATE BETWEEN ?2 AND ?3) W " +
            " INNER JOIN `ENGDATA` ON W.`DATE` = `ENGDATA`.`DATE` AND W.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorWheelLeftInterface> getLeftWheelUserLW(String carId, Date fromDate, Date toDate);

    // get WR's User judgement values are not Null data
    @Query(value = "Select W.IDX, W.SDAID, W.USER_RW, W.USER_RW_ID, W.USER_RW_DATE, " +
            " W.W_RPM, W.R_W_V_2X, W.R_W_V_3X, W.R_W_S_Fault3, W.`DATE`, " +
            " `ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a from (Select * from `WHLDATA` WHERE `WHLDATA`.USER_RW IS NOT NULL AND `WHLDATA`.SDAID = ?1 AND `WHLDATA`.DATE BETWEEN ?2 AND ?3) W  " +
            " INNER JOIN `ENGDATA` ON W.`DATE` = `ENGDATA`.`DATE` AND W.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorWheelRightInterface> getRightWheelUserRW(String carId, Date fromDate, Date toDate);
}
