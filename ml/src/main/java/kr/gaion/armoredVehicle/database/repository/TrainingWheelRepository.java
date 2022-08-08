package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.TrainingWheel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingWheelRepository extends JpaRepository<TrainingWheel, Long> {
}
