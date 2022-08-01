package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorEngine;
import kr.gaion.armoredVehicle.database.model.SensorGearbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorEngineRepository extends JpaRepository<SensorEngine, Long> {
  @Query(value = "Select * from ENGDATA f where f.AI_Predict is Null", nativeQuery = true)
  List<SensorEngine> findSensorEngineByAiPredictIsNull();
}