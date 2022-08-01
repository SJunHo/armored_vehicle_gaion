package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorBearing;
import kr.gaion.armoredVehicle.database.model.SensorGearbox;
import kr.gaion.armoredVehicle.database.model.SensorWheel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SensorGearboxRepository extends JpaRepository<SensorGearbox, Long> {
  @Query(value = "Select * from GRBDATA f where f.AI_Predict is Null", nativeQuery = true)
  List<SensorGearbox> findSensorGearboxByAiPredictIsNull();
}