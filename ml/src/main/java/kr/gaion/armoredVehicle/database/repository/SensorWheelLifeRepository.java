package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorWheelLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorWheelLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorWheelLifeRepository extends JpaRepository<SensorWheelLife, Long> {
    Page<SensorWheelLifeInterface> findAiTripIsNull(Pageable pageable);
}
