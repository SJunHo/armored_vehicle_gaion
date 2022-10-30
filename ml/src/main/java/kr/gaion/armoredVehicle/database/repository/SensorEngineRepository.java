package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorEngine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorEngineRepository extends JpaRepository<SensorEngine, Long> {
  @Query(value = "Select * from ENGDATA f where f.AI_Predict is Null", nativeQuery = true)
  List<SensorEngine> findSensorEngineByAiPredictIsNull();

  @Query(value = "Select * from ENGDATA f where f.AI_Predict is not Null and DATE(f.`DATE`) >= ?1 and DATE(f.`DATE`) <= ?2 ", nativeQuery = true)
  Page<SensorEngine> findSensorEngineByAiPredictIsNotNull(String fromDate, String toDate, Pageable pageable);
}