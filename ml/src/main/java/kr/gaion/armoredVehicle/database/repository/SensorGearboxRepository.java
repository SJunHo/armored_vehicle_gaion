package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.SensorGearboxInput;
import kr.gaion.armoredVehicle.database.model.SensorGearbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorGearboxRepository extends JpaRepository<SensorGearbox, Long> {
    @Query(value = " SELECT g.IDX, g.AI_GEAR, g.AI_GEAR_ALGO, g.AI_GEAR_MODEL, g.AI_GEAR_DATE, " +
            " g.USER_GEAR, g.USER_GEAR_ID, g.USER_GEAR_DATE, " +
            " g.W_RPM, g.G_V_OverallRMS, g.G_V_Wheel1X, g.G_V_Wheel2X, g.G_V_Pinion1X, g.G_V_Pinion2X, g.G_V_GMF1X, g.G_V_GMF2X, " +
            " e.AC_h, e.AC_v, e.AC_a, g.`DATE` " +
            " FROM GRBDATA g, ENGDATA e " +
            " WHERE g.`DATE` = e.`DATE` AND g.AI_GEAR is Null ", nativeQuery = true)
    List<SensorGearboxInput> findSensorGearboxAiGEARIsNull();
}