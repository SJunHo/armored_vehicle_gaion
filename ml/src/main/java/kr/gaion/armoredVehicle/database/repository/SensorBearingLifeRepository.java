package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorBearingLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorBearingLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SensorBearingLifeRepository extends JpaRepository<SensorBearingLife, Long> {
    //get unlabeled bearing remaining life data
    @Query(value = " Select * FROM `BERLIFEDATA` WHERE `BERLIFEDATA`.AI_Trip IS NULL ", nativeQuery = true)
    Page<SensorBearingLifeInterface> findSensorBearingLifeAiTripIIsNull(Pageable pageable);
}
