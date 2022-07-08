package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.AlgorithmResponseDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgorithmResponseDBRepository extends JpaRepository<AlgorithmResponseDB,Long> {
}
