package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorEngineInterface;
import kr.gaion.armoredVehicle.database.model.SensorEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorEngineRepository extends JpaRepository<SensorEngine, Long> {
    @Query(value = " SELECT e.IDX, e.AI_ENGINE, e.AI_ENGINE_ALGO, e.AI_ENGINE_MODEL, e.AI_ENGINE_DATE, " +
            " e.USER_ENGINE, e.USER_ENGINE_ID, e.USER_ENGINE_DATE, " +
            " e.W_RPM, e.E_V_OverallRMS, e.E_V_1_2X, e.E_V_1X, e.E_V_Crestfactor, e.AC_h, e.AC_v, e.AC_a, e.`DATE` " +
            " FROM ENGDATA e " +
            " WHERE e.AI_ENGINE is Null ", nativeQuery = true)
    List<SensorEngineInterface> findSensorEngineAiENGINEIsNull();
}