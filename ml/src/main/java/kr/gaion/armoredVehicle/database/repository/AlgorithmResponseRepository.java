package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.AlgorithmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgorithmResponseRepository extends JpaRepository<AlgorithmResponse,Long> {
}
