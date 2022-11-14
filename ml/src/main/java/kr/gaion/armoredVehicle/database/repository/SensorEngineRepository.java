package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorEngineInterface;
import kr.gaion.armoredVehicle.database.model.SensorEngine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorEngineRepository extends JpaRepository<SensorEngine, Long> {
    @Query(value = " SELECT ENGDATA.IDX, ENGDATA.AI_ENGINE, ENGDATA.AI_ENGINE_ALGO, ENGDATA.AI_ENGINE_MODEL, ENGDATA.AI_ENGINE_DATE, " +
            " ENGDATA.W_RPM, ENGDATA.E_V_OverallRMS, ENGDATA.E_V_1_2X, ENGDATA.E_V_1X, ENGDATA.E_V_Crestfactor, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, ENGDATA.`DATE` " +
            " FROM `ENGDATA` " +
            " WHERE ENGDATA.AI_ENGINE IS NULL ", nativeQuery = true)
    Page<SensorEngineInterface> findSensorEngineAiENGINEIsNull(Pageable pageable);
}