package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorGearboxLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorGearboxLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface SensorGearboxLifeRepository extends JpaRepository<SensorGearboxLife, Long> {
    //get unlabeled gearbox remaining life data
    @Query(value = " Select * FROM `GRBLIFEDATA` WHERE `GRBLIFEDATA`.AI_Trip IS NULL AND `GRBLIFEDATA`.DATE BETWEEN ?1 AND ?2 ", nativeQuery = true)
    Page<SensorGearboxLifeInterface> findSensorGearboxLifeAiTripIIsNull(Date fromDate, Date toDate, Pageable pageable);
}