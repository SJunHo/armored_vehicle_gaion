package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorWheelLeftInterface;
import kr.gaion.armoredVehicle.database.dto.SensorWheelRightInterface;
import kr.gaion.armoredVehicle.database.model.SensorWheel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorWheelRepository extends JpaRepository<SensorWheel, Long> {
    // Wheel Left
    @Query(value = " SELECT WHLDATA.IDX, WHLDATA.AI_LW, WHLDATA.AI_LW_ALGO, WHLDATA.AI_LW_MODEL, WHLDATA.AI_LW_DATE, " +
            " WHLDATA.USER_LW, WHLDATA.USER_LW_ID, WHLDATA.USER_LW_DATE, " +
            " WHLDATA.W_RPM, WHLDATA.L_W_V_2X, WHLDATA.L_W_V_3X, WHLDATA.L_W_S_Fault3, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` " +
            " FROM WHLDATA, ENGDATA " +
            " WHERE WHLDATA.`DATE` = ENGDATA.`DATE` AND WHLDATA.AI_LW IS NULL ", nativeQuery = true)
    Page<SensorWheelLeftInterface> findSensorWheelLeftAiLWIsNull(Pageable pageable);

    // Wheel Right
    @Query(value = " SELECT WHLDATA.IDX, WHLDATA.AI_RW, WHLDATA.AI_RW_ALGO, WHLDATA.AI_RW_MODEL, WHLDATA.AI_RW_DATE, " +
            " WHLDATA.USER_RW, WHLDATA.USER_RW_ID, WHLDATA.USER_RW_DATE, " +
            " WHLDATA.W_RPM, WHLDATA.R_W_V_2X, WHLDATA.R_W_V_3X, WHLDATA.R_W_S_Fault3, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, WHLDATA.`DATE` " +
            " FROM WHLDATA, ENGDATA " +
            " WHERE WHLDATA.`DATE` = ENGDATA.`DATE` AND WHLDATA.AI_RW IS NULL ", nativeQuery = true)
    Page<SensorWheelRightInterface> findSensorWheelRightAiRWIsNull(Pageable pageable);
}
