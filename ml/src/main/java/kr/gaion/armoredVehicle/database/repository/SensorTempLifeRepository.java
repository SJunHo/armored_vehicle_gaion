package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorTempLife;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorTempLifeRepository extends JpaRepository<SensorTempLife, Long> {
    //    Page<Object> findSensorBearingByAiPredictIsNull(Pageable pageable);
    @Query(value = "Select * from TEMPLIFEDATA t where t.AI_Predict IS NULL", nativeQuery = true)
    Page<SensorTempLife> findSensorTempLifeByAiPredictIsNull(Pageable pageable);
}
