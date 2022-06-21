package kr.gaion.armoredVehicle.dataset.repository;

import kr.gaion.armoredVehicle.dataset.model.Bearing;
import kr.gaion.armoredVehicle.dataset.model.DataLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BearingRepository extends JpaRepository<Bearing, Long> {
}
