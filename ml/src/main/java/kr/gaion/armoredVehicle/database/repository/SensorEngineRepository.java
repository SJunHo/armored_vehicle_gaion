package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorEngineInterface;
import kr.gaion.armoredVehicle.database.model.SensorEngine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SensorEngineRepository extends JpaRepository<SensorEngine, Long> {
    @Query(value = "Select `ENGDATA`.IDX, `ENGDATA`.AI_ENGINE, `ENGDATA`.AI_ENGINE_ALGO, `ENGDATA`.AI_ENGINE_MODEL, `ENGDATA`.AI_ENGINE_DATE, " +
            "`ENGDATA`.USER_ENGINE, `ENGDATA`.USER_ENGINE_ID, `ENGDATA`.USER_ENGINE_DATE, " +
            "`ENGDATA`.W_RPM, `ENGDATA`.E_V_OverallRMS, `ENGDATA`.E_V_1_2X, `ENGDATA`.E_V_1X, `ENGDATA`.E_V_Crestfactor, " +
            "`ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `ENGDATA`.`DATE`  from `ENGDATA` " +
            "WHERE `ENGDATA`.AI_ENGINE IS NOT NULL AND `ENGDATA`.SDAID = ?1 AND `ENGDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    Page<SensorEngineInterface> getEngineAiEnginePredictedData(String carId, Date fromDate, Date toDate, Pageable pageable);

    @Query(value = "Select DISTINCT SDAID from ENGDATA b where ?1 is Not NULL", nativeQuery = true)
    List<String> findDistinctByCarId(String targetColumn);

    @Query(value = " SELECT ENGDATA.IDX, ENGDATA.AI_ENGINE, ENGDATA.AI_ENGINE_ALGO, ENGDATA.AI_ENGINE_MODEL, ENGDATA.AI_ENGINE_DATE, " +
            " ENGDATA.W_RPM, ENGDATA.E_V_OverallRMS, ENGDATA.E_V_1_2X, ENGDATA.E_V_1X, ENGDATA.E_V_Crestfactor, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, ENGDATA.`DATE` " +
            " FROM `ENGDATA` " +
            " WHERE ENGDATA.AI_ENGINE IS NULL ", nativeQuery = true)
    Page<SensorEngineInterface> findSensorEngineAiENGINEIsNull(Pageable pageable);

    // get E's User judgement values are not Null data
    @Query(value = "Select `ENGDATA`.IDX, `ENGDATA`.USER_ENGINE, `ENGDATA`.USER_ENGINE_ID, `ENGDATA`.USER_ENGINE_DATE, " +
            "`ENGDATA`.W_RPM, `ENGDATA`.E_V_OverallRMS, `ENGDATA`.E_V_1_2X, `ENGDATA`.E_V_1X, `ENGDATA`.E_V_Crestfactor, " +
            "`ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, `ENGDATA`.`DATE`  from `ENGDATA` " +
            "WHERE `ENGDATA`.USER_ENGINE IS NOT NULL AND `ENGDATA`.SDAID = ?1 AND `ENGDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    List<SensorEngineInterface> getEngineUserEngineData(String carId, Date fromDate, Date toDate);
}