package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.TrainingBearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingBearingRepository extends JpaRepository<TrainingBearing, Long> {
}
