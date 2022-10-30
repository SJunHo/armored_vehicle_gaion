package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorGearbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorGearboxRepository extends JpaRepository<SensorGearbox, Long> {
  @Query(value = "Select * from GRBDATA f where f.AI_Predict is Null", nativeQuery = true)
  List<SensorGearbox> findSensorGearboxByAiPredictIsNull();

  @Query(value = "Select * from GRBDATA f where f.AI_Predict is not Null and DATE(f.`DATE`) >= ?1 and DATE(f.`DATE`) <= ?2 ", nativeQuery = true)
  Page<SensorGearbox> findSensorGearboxByAiPredictIsNotNull(String fromDate, String toDate, Pageable pageable);
}