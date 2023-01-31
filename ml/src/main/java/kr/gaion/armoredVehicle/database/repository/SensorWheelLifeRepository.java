package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorWheelLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorWheelLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SensorWheelLifeRepository extends JpaRepository<SensorWheelLife, Long> {
    //get unlabeled wheel remaining life data
    @Query(value = " Select * FROM `WHLLIFEDATA` WHERE `WHLLIFEDATA`.AI_Trip IS NULL ", nativeQuery = true)
    Page<SensorWheelLifeInterface> findSensorWheelLifeAiTripIIsNull(Pageable pageable);
}
