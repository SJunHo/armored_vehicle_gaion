package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorEngineLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorEngineLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SensorEngineLifeRepository extends JpaRepository<SensorEngineLife, Long> {
    //get unlabeled engine remaining life data
    @Query(value = " Select * FROM `ENGLIFEDATA` WHERE `ENGLIFEDATA`.AI_Trip IS NULL ", nativeQuery = true)
    Page<SensorEngineLifeInterface> findSensorEngineLifeAiTripIIsNull(Pageable pageable);
}
