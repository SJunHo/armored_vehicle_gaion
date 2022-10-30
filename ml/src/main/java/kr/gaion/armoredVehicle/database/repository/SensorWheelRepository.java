package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorWheel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorWheelRepository extends JpaRepository<SensorWheel, Long> {
  @Query(value = "Select * from WHLDATA f where f.AI_Predict is Null", nativeQuery = true)
  List<SensorWheel> findSensorWheelByAiPredictIsNull();

  @Query(value = "Select * from WHLDATA f where f.AI_Predict is not Null and DATE(f.`DATE`) >= ?1 and DATE(f.`DATE`) <= ?2 ", nativeQuery = true)
  Page<SensorWheel> findSensorWheelByAiPredictIsNotNull(String fromDate, String toDate, Pageable pageable);
}
