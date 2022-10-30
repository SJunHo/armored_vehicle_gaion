package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorTempLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorTempLifeRepository extends JpaRepository<SensorTempLife, Long> {
    @Query(value = "Select * from TEMPLIFEDATA t where t.AI_Predict is Null", nativeQuery = true)
    List<SensorTempLife> findSensorTempLifeByAiPredictIsNull();

    @Query(value = "Select * from TEMPLIFEDATA t where t.AI_Predict is not Null and DATE(t.`TIME`) >= ?1 and DATE(t.`TIME`) <= ?2 ", nativeQuery = true)
    Page<SensorTempLife> findSensorTempLifeByAiPredictIsNotNull(String fromDate, String toDate, Pageable pageable);
}
