package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.EngineInput;
import kr.gaion.armoredVehicle.database.model.TrainingEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingEngineRepository extends JpaRepository<TrainingEngine, Long> {
    // Engine
    @Query(value = " SELECT e.AI_ENGINE, e.W_RPM, e.E_V_OverallRMS, e.E_V_1_2X, e.E_V_1X, e.E_V_Crestfactor, e.AC_h, e.AC_v, e.AC_a, e.`DATE` " +
            " FROM ENGTRNNG e ", nativeQuery = true)
    List<EngineInput> findEngine();
}