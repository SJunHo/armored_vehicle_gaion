package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.GearboxInterface;
import kr.gaion.armoredVehicle.database.model.TrainingGearbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingGearboxRepository extends JpaRepository<TrainingGearbox, Long> {
    // Gearbox
    @Query(value = " SELECT GRBTRNNG.IDX, GRBTRNNG.AI_GEAR, GRBTRNNG.W_RPM, GRBTRNNG.G_V_OverallRMS, GRBTRNNG.G_V_Wheel1X, GRBTRNNG.G_V_Wheel2X, " +
            " GRBTRNNG.G_V_Pinion1X, GRBTRNNG.G_V_Pinion2X, GRBTRNNG.G_V_GMF1X, GRBTRNNG.G_V_GMF2X, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, GRBTRNNG.`DATE` " +
            " FROM `GRBTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON GRBTRNNG.`DATE` = ENGTRNNG.`DATE` ", nativeQuery = true)
    List<GearboxInterface> findGearbox();
}