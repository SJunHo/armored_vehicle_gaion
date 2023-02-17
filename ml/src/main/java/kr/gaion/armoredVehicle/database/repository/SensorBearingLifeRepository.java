package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorBearingLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorBearingLife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SensorBearingLifeRepository extends JpaRepository<SensorBearingLife, Long> {

    @Query(value = "Select DISTINCT SDAID from `BERLIFEDATA` ", nativeQuery = true)
    List<String> findDistinctByCarIdFromBearingLifeData();

    //get unlabeled bearing remaining life data
    @Query(value = " Select * FROM `BERLIFEDATA` WHERE `BERLIFEDATA`.SDAID = ?1 AND `BERLIFEDATA`.AI_Trip IS NULL AND `BERLIFEDATA`.DATE BETWEEN ?2 AND ?3 ", nativeQuery = true)
    List<SensorBearingLifeInterface> findSensorBearingLifeAiTripIIsNull(String carId, Date fromDate, Date toDate);
}
