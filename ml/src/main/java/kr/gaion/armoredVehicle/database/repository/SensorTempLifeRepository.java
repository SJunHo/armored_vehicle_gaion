package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorTempLife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorTempLifeRepository extends JpaRepository<SensorTempLife, Long> {
//    Page<Object> findSensorBearingByAiPredictIsNull(Pageable pageable);
    @Query(value = "Select * from TEMPLIFEDATA t where t.ACPOWER is Null", nativeQuery = true)
    List<SensorTempLife> findSensorTempLifeByAcPowerIsNull();
}
