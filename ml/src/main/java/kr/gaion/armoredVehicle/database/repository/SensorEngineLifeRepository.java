package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorEngineLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorEngineLife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SensorEngineLifeRepository extends JpaRepository<SensorEngineLife, Long> {

    @Query(value = "Select DISTINCT SDAID from `ENGLIFEDATA` ", nativeQuery = true)
    List<String> findDistinctByCarIdFromEngineLifeData();

    //get unlabeled engine remaining life data
    @Query(value = " Select * FROM `ENGLIFEDATA` WHERE `ENGLIFEDATA`.SDAID = ?1 AND `ENGLIFEDATA`.AI_Trip IS NULL AND `ENGLIFEDATA`.DATE BETWEEN ?2 AND ?3", nativeQuery = true)
    List<SensorEngineLifeInterface> findSensorEngineLifeAiTripIIsNull(String carId, Date fromDate, Date toDate);
}
