package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorBearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorBearingRepository extends JpaRepository<SensorBearing, Long> {
    @Query(value = "Select * from BERDATA f where f.AI_Predict is Null", nativeQuery = true)
    List<SensorBearing> findSensorBearingByAiPredictIsNull();
}
