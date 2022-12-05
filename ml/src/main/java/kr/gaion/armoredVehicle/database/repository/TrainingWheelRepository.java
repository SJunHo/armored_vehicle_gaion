package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.WheelLeftInterface;
import kr.gaion.armoredVehicle.database.dto.WheelRightInterface;
import kr.gaion.armoredVehicle.database.model.TrainingWheel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingWheelRepository extends JpaRepository<TrainingWheel, Long> {
    // Wheel Left
    @Query(value = " SELECT WHLTRNNG.IDX, WHLTRNNG.AI_LW, WHLTRNNG.W_RPM, WHLTRNNG.L_W_V_2X, WHLTRNNG.L_W_V_3X, WHLTRNNG.L_W_S_Fault3, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, WHLTRNNG.`DATE` " +
            " FROM `WHLTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON WHLTRNNG.`DATE` = ENGTRNNG.`DATE` ", nativeQuery = true)
    List<WheelLeftInterface> findWheelLeft();

    // Wheel Right
    @Query(value = " SELECT WHLTRNNG.IDX, WHLTRNNG.AI_RW, WHLTRNNG.W_RPM, WHLTRNNG.R_W_V_2X, WHLTRNNG.R_W_V_3X, WHLTRNNG.R_W_S_Fault3, " +
            " ENGTRNNG.AC_h, ENGTRNNG.AC_v, ENGTRNNG.AC_a, WHLTRNNG.`DATE` " +
            " FROM `WHLTRNNG` " +
            " INNER JOIN `ENGTRNNG` ON WHLTRNNG.`DATE` = ENGTRNNG.`DATE` ", nativeQuery = true)
    List<WheelRightInterface> findWheelRight();
}
