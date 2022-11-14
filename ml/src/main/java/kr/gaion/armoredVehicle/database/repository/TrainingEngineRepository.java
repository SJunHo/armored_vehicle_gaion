package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.EngineInterface;
import kr.gaion.armoredVehicle.database.model.TrainingEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingEngineRepository extends JpaRepository<TrainingEngine, Long> {
    // Engine
    @Query(value = " SELECT ENGTRNNG.IDX, ENGTRNNG.AI_ENGINE, ENGTRNNG.W_RPM, ENGTRNNG.E_V_OverallRMS, " +
            " ENGTRNNG.E_V_1_2X, ENGTRNNG.E_V_1X, ENGTRNNG.E_V_Crestfactor, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, ENGTRNNG.`DATE` " +
            " FROM `ENGTRNNG` ", nativeQuery = true)
    List<EngineInterface> findEngine();
}