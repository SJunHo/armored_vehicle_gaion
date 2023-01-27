package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorBearingLifeInterface;
import kr.gaion.armoredVehicle.database.model.SensorBearingLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorBearingLifeRepository extends JpaRepository<SensorBearingLife, Long> {
    Page<SensorBearingLifeInterface> findAiTripIsNull(Pageable pageable);
}
