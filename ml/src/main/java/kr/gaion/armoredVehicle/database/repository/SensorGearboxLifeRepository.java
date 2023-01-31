package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorGearboxLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorGearboxLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorGearboxLifeRepository extends JpaRepository<SensorGearboxLife, Long> {
    Page<SensorGearboxLifeInterface> findByAiTripIsNull(Pageable pageable);
}
