package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.TrainingTempLife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTempLifeRepository extends JpaRepository<TrainingTempLife, Long> {
}
