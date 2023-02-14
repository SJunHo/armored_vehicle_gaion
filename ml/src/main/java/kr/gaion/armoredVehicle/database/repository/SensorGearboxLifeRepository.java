package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorGearboxLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorGearboxLife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SensorGearboxLifeRepository extends JpaRepository<SensorGearboxLife, Long> {

    @Query(value = "Select DISTINCT SDAID from `GRBLIFEDATA` ", nativeQuery = true)
    List<String> findDistinctByCarIdFromGearboxLifeData();

    //get unlabeled gearbox remaining life data
    @Query(value = " Select * FROM `GRBLIFEDATA` WHERE `GRBLIFEDATA`.SDAID = ?1 AND `GRBLIFEDATA`.AI_Trip IS NULL AND `GRBLIFEDATA`.DATE BETWEEN ?2 AND ?3 ", nativeQuery = true)
    List<SensorGearboxLifeInterface> findSensorGearboxLifeAiTripIIsNull(String carId, Date fromDate, Date toDate);
}
