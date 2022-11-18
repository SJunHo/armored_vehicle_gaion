package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorWheelLeftInterface;
import kr.gaion.armoredVehicle.database.dto.SensorWheelRightInterface;
import kr.gaion.armoredVehicle.database.model.SensorWheel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface SensorWheelRepository extends JpaRepository<SensorWheel, Long> {
    @Query(value = "Select DISTINCT SDAID from WHLDATA b where ?1 is Not NULL", nativeQuery = true)
    List<String> findDistinctByCarId(String targetColumn);

    @Query(value = "Select WHLDATA.IDX, WHLDATA.AI_LW, WHLDATA.AI_LW_ALGO, WHLDATA.AI_LW_MODEL, WHLDATA.AI_LW_DATE, " +
            " WHLDATA.W_RPM, WHLDATA.L_W_V_2X, WHLDATA.L_W_V_3X, WHLDATA.L_W_S_Fault3, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` from WHLDATA " +
            "LEFT JOIN `ENGDATA` ON WHLDATA.`DATE` = `ENGDATA`.`DATE` AND WHLDATA.SDAID = `ENGDATA`.SDAID " +
            "WHERE (WHLDATA.AI_LW IS NOT NULL) AND (WHLDATA.SDAID = ?1) AND (WHLDATA.DATE BETWEEN ?2 AND ?3)", nativeQuery = true)
    List<Map<String, String>> getLeftWheelAiLWPredictedData(String carId, LocalDate fromDate, LocalDate toDate);

    @Query(value = "Select WHLDATA.IDX, WHLDATA.AI_RW, WHLDATA.AI_RW_ALGO, WHLDATA.AI_RW_MODEL, WHLDATA.AI_RW_DATE, " +
            "WHLDATA.W_RPM, WHLDATA.R_W_V_2X, WHLDATA.R_W_V_3X, WHLDATA.R_W_S_Fault3, " +
            "ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` from WHLDATA " +
            "LEFT JOIN `ENGDATA` ON WHLDATA.`DATE` = `ENGDATA`.`DATE` AND WHLDATA.SDAID = `ENGDATA`.SDAID " +
            "WHERE (WHLDATA.AI_RW IS NOT NULL) AND (WHLDATA.SDAID = ?1) AND (WHLDATA.DATE BETWEEN ?2 AND ?3)", nativeQuery = true)
    List<Map<String, String>> getRightWheelAiRWPredictedData(String carId, LocalDate fromDate, LocalDate toDate);

    // Wheel Left
    @Query(value = " SELECT WHLDATA.IDX, WHLDATA.AI_LW, WHLDATA.AI_LW_ALGO, WHLDATA.AI_LW_MODEL, WHLDATA.AI_LW_DATE, " +
            " WHLDATA.W_RPM, WHLDATA.L_W_V_2X, WHLDATA.L_W_V_3X, WHLDATA.L_W_S_Fault3, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` " +
            " FROM `WHLDATA` " +
            " INNER JOIN `ENGDATA` ON WHLDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE WHLDATA.AI_LW IS NULL ", nativeQuery = true)
    Page<SensorWheelLeftInterface> findSensorWheelLeftAiLWIsNull(Pageable pageable);

    // Wheel Right
    @Query(value = " SELECT WHLDATA.IDX, WHLDATA.AI_RW, WHLDATA.AI_RW_ALGO, WHLDATA.AI_RW_MODEL, WHLDATA.AI_RW_DATE, " +
            " WHLDATA.W_RPM, WHLDATA.R_W_V_2X, WHLDATA.R_W_V_3X, WHLDATA.R_W_S_Fault3, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` " +
            " FROM `WHLDATA` " +
            " INNER JOIN `ENGDATA` ON WHLDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE WHLDATA.AI_RW IS NULL ", nativeQuery = true)
    Page<SensorWheelRightInterface> findSensorWheelRightAiRWIsNull(Pageable pageable);
}
