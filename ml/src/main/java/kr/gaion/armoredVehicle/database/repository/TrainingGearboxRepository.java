package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.GearboxInput;
import kr.gaion.armoredVehicle.database.model.TrainingGearbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingGearboxRepository extends JpaRepository<TrainingGearbox, Long> {
    // Gearbox
    @Query(value = " SELECT g.IDX, g.AI_GEAR, g.W_RPM, g.G_V_OverallRMS, g.G_V_Wheel1X, g.G_V_Wheel2X, g.G_V_Pinion1X, g.G_V_Pinion2X, g.G_V_GMF1X, g.G_V_GMF2X, e.AC_h, e.AC_v, e.AC_a, g.`DATE` " +
            " FROM GRBTRNNG g, ENGTRNNG e " +
            " WHERE g.`DATE` = e.`DATE` ", nativeQuery = true)
    List<GearboxInput> findGearbox();
}