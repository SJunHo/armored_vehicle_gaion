package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorGearboxInterface;
import kr.gaion.armoredVehicle.database.model.SensorGearbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SensorGearboxRepository extends JpaRepository<SensorGearbox, Long> {
    @Query(value = "Select DISTINCT SDAID from GRBDATA ", nativeQuery = true)
    List<String> findDistinctByCarId(String targetColumn);

    @Query(value = "Select DISTINCT AI_GEAR_MODEL from GRBDATA where AI_GEAR_MODEL Is Not Null ", nativeQuery = true)
    List<String> findDistinctByGEARModel();

    @Query(value = "Select G.IDX, G.SDAID, G.AI_GEAR, G.AI_GEAR_ALGO, G.AI_GEAR_MODEL, G.AI_GEAR_DATE, " +
            "G.USER_GEAR, G.USER_GEAR_ID, G.USER_GEAR_DATE, " +
            "G.W_RPM, G.G_V_OverallRMS, G.G_V_Wheel1X, G.G_V_Wheel2X, " +
            "G.G_V_Pinion1X, G.G_V_Pinion2X, G.G_V_GMF1X, G.G_V_GMF2X, " +
            "`ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, G.`DATE` " +
            "from (Select * from `GRBDATA` WHERE `GRBDATA`.AI_GEAR IS NOT NULL AND `GRBDATA`.SDAID = ?1 " +
            " AND `GRBDATA`.AI_GEAR_MODEL = ?2 AND `GRBDATA`.DATE BETWEEN ?3 AND ?4) G " +
            "INNER JOIN `ENGDATA` ON G.`DATE` = `ENGDATA`.`DATE` AND G.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorGearboxInterface> getGearboxAiGearPredictedData(String carId, String modelName, Date fromDate, Date toDate);

    @Query(value = " SELECT G.IDX, G.SDAID, G.AI_GEAR, G.AI_GEAR_ALGO, G.AI_GEAR_MODEL, G.AI_GEAR_DATE, " +
            " G.W_RPM, G.G_V_OverallRMS, G.G_V_Wheel1X, G.G_V_Wheel2X, " +
            " G.G_V_Pinion1X, G.G_V_Pinion2X, G.G_V_GMF1X, G.G_V_GMF2X, " +
            " ENGDATA.AC_h, ENGDATA.AC_v, ENGDATA.AC_a, G.`DATE` " +
            " FROM (Select * from `GRBDATA` WHERE `GRBDATA`.SDAID = ?1 AND `GRBDATA`.AI_GEAR IS NULL AND `GRBDATA`.DATE BETWEEN ?2 AND ?3) G " +
            " INNER JOIN `ENGDATA` ON G.`DATE` = ENGDATA.`DATE` AND G.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorGearboxInterface> findSensorGearboxAiGEARIsNull(String carId, Date fromDate, Date toDate);

    // get G's User judgement values are not Null data
    @Query(value = "Select G.IDX, G.SDAID, G.USER_GEAR, G.USER_GEAR_ID, G.USER_GEAR_DATE, " +
            "G.W_RPM, G.G_V_OverallRMS, G.G_V_Wheel1X, G.G_V_Wheel2X, " +
            "G.G_V_Pinion1X, G.G_V_Pinion2X, G.G_V_GMF1X, G.G_V_GMF2X, " +
            "`ENGDATA`.AC_h, `ENGDATA`.AC_v, `ENGDATA`.AC_a, G.`DATE` " +
            "from (Select * from `GRBDATA` WHERE `GRBDATA`.USER_GEAR IS NOT NULL AND `GRBDATA`.SDAID = ?1 " +
            " AND `GRBDATA`.AI_GEAR_MODEL = ?2 AND `GRBDATA`.DATE BETWEEN ?3 AND ?4) G  " +
            "INNER JOIN `ENGDATA` ON G.`DATE` = `ENGDATA`.`DATE` AND G.SDAID = `ENGDATA`.SDAID ", nativeQuery = true)
    List<SensorGearboxInterface> getGearboxUserGearData(String carId, String modelName, Date fromDate, Date toDate);
}