package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorGearboxInterface;
import kr.gaion.armoredVehicle.database.model.SensorGearbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface SensorGearboxRepository extends JpaRepository<SensorGearbox, Long> {
    @Query(value = "Select GRBDATA.IDX, GRBDATA.AI_GEAR, GRBDATA.AI_GEAR_ALGO, GRBDATA.AI_GEAR_MODEL, GRBDATA.AI_GEAR_DATE, " +
            "GRBDATA.W_RPM, GRBDATA.G_V_OverallRMS, GRBDATA.G_V_Wheel1X, GRBDATA.G_V_Wheel2X, " +
            "GRBDATA.G_V_Pinion1X, GRBDATA.G_V_Pinion2X, GRBDATA.G_V_GMF1X, GRBDATA.G_V_GMF2X, " +
            "ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, GRBDATA.`DATE` from GRBDATA " +
            "LEFT JOIN `ENGDATA` ON GRBDATA.`DATE` = `ENGDATA`.`DATE` AND GRBDATA.SDAID = `ENGDATA`.SDAID " +
            "WHERE (GRBDATA.AI_GEAR IS NOT NULL) AND (GRBDATA.SDAID = ?1) AND (GRBDATA.DATE BETWEEN ?2 AND ?3)", nativeQuery = true)
    List<Map<String, String>> getGearboxAiGearPredictedData(String carId, LocalDate fromDate, LocalDate toDate);


    @Query(value = "Select DISTINCT SDAID from GRBDATA b where ?1 is Not NULL", nativeQuery = true)
    List<String> findDistinctByCarId(String targetColumn);


    @Query(value = " SELECT GRBDATA.IDX, GRBDATA.AI_GEAR, GRBDATA.AI_GEAR_ALGO, GRBDATA.AI_GEAR_MODEL, GRBDATA.AI_GEAR_DATE, " +
            " GRBDATA.W_RPM, GRBDATA.G_V_OverallRMS, GRBDATA.G_V_Wheel1X, GRBDATA.G_V_Wheel2X, " +
            " GRBDATA.G_V_Pinion1X, GRBDATA.G_V_Pinion2X, GRBDATA.G_V_GMF1X, GRBDATA.G_V_GMF2X, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, GRBDATA.`DATE` " +
            " FROM `GRBDATA` " +
            " INNER JOIN `ENGDATA` ON GRBDATA.`DATE` = ENGDATA.`DATE` " +
            " WHERE GRBDATA.AI_GEAR IS NULL ", nativeQuery = true)
    Page<SensorGearboxInterface> findSensorGearboxAiGEARIsNull(Pageable pageable);
}