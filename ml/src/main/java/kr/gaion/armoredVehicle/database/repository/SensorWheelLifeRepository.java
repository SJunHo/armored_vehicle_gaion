package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorWheelLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorWheelLife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SensorWheelLifeRepository extends JpaRepository<SensorWheelLife, Long> {

    @Query(value = "Select DISTINCT SDAID from `WHLLIFEDATA` ", nativeQuery = true)
    List<String> findDistinctByCarIdFromWheelLifeData();

    //get unlabeled wheel remaining life data
    @Query(value = " Select * FROM `WHLLIFEDATA` WHERE `WHLLIFEDATA`.SDAID = ?1 AND `WHLLIFEDATA`.AI_Trip IS NULL AND `WHLLIFEDATA`.DATE BETWEEN ?2 AND ?3 ", nativeQuery = true)
    List<SensorWheelLifeInterface> findSensorWheelLifeAiTripIIsNull(String carId, Date fromDate, Date toDate);
}
