package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.SensorBearing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorBearingRepository extends JpaRepository<SensorBearing, Long> {
    @Query(value = "Select * from BERDATA f where f.AI_Predict is Null", nativeQuery = true)
    List<SensorBearing> findSensorBearingByAiPredictIsNull();

    @Query(value = "Select * from BERDATA f where f.AI_Predict is not Null and DATE(f.`DATE`) >= ?1 and DATE(f.`DATE`) <= ?2 ", nativeQuery = true)
    Page<SensorBearing> findSensorBearingByAiPredictIsNotNull(String fromDate, String toDate, Pageable pageable);

    @Query(value = "Select * from BERDATA f where f.IDX = ?1 ", nativeQuery = true)
    List<SensorBearing> findSensorBearingByIds();
}
