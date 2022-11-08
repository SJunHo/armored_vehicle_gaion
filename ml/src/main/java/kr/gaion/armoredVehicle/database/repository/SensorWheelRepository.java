package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorWheelLeftInput;
import kr.gaion.armoredVehicle.database.dto.SensorWheelRightInput;
import kr.gaion.armoredVehicle.database.model.SensorWheel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorWheelRepository extends JpaRepository<SensorWheel, Long> {
    // Wheel Left
    @Query(value = " SELECT w.IDX, w.AI_LW, w.AI_LW_ALGO, w.AI_LW_MODEL, w.AI_LW_DATE, " +
            " w.USER_LW, w.USER_LW_ID, w.USER_LW_DATE, " +
            " w.W_RPM, w.L_W_V_2X, w.L_W_V_3X, w.L_W_S_Fault3, " +
            " e.AC_h, e.AC_v, e.AC_a, w.`DATE` " +
            " FROM WHLDATA w, ENGDATA e " +
            " WHERE w.`DATE` = e.`DATE` AND w.AI_LW is Null ", nativeQuery = true)
    List<SensorWheelLeftInput> findSensorWheelLeftAiLWIsNull();

    // Wheel Right
    @Query(value = " SELECT w.IDX, w.AI_RW, w.AI_RW_ALGO, w.AI_RW_MODEL, w.AI_RW_DATE, " +
            " w.USER_RW, w.USER_RW_ID, w.USER_RW_DATE, " +
            " w.W_RPM, w.R_W_V_2X, w.R_W_V_3X, w.R_W_S_Fault3, " +
            " e.AC_h, e.AC_v, e.AC_a, w.`DATE` " +
            " FROM WHLDATA w, ENGDATA e " +
            " WHERE w.`DATE` = e.`DATE` AND w.AI_RW is Null ", nativeQuery = true)
    List<SensorWheelRightInput> findSensorWheelRightAiRWIsNull();
}
