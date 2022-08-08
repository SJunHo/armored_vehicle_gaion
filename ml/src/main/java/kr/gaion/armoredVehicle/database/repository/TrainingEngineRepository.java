package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.TrainingEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingEngineRepository extends JpaRepository<TrainingEngine, Long> {
}