package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorEngineLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorEngineLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorEngineLifeRepository extends JpaRepository<SensorEngineLife, Long> {
    Page<SensorEngineLifeInterface> findAiTripIsNull(Pageable pageable);
}
