package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.TrainingGearbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingGearboxRepository extends JpaRepository<TrainingGearbox, Long> {
}