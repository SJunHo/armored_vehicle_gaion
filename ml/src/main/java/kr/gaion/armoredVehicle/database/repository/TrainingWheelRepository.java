package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.WheelLeftInput;
import kr.gaion.armoredVehicle.database.dto.WheelRightInput;
import kr.gaion.armoredVehicle.database.model.TrainingWheel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingWheelRepository extends JpaRepository<TrainingWheel, Long> {
    // Wheel Left
    @Query(value = " SELECT w.IDX, w.AI_LW, w.W_RPM, w.L_W_V_2X, w.L_W_V_3X, w.L_W_S_Fault3, e.AC_h, e.AC_v, e.AC_a, w.`DATE` " +
            " FROM WHLTRNNG w, ENGTRNNG e " +
            " WHERE w.`DATE` = e.`DATE` ", nativeQuery = true)
    List<WheelLeftInput> findWheelLeft();

    // Wheel Right
    @Query(value = " SELECT w.IDX, w.AI_LW, w.W_RPM, w.R_W_V_2X, w.R_W_V_3X, w.R_W_S_Fault3, e.AC_h, e.AC_v, e.AC_a, w.`DATE` " +
            " FROM WHLTRNNG w, ENGTRNNG e " +
            " WHERE w.`DATE` = e.`DATE` ", nativeQuery = true)
    List<WheelRightInput> findWheelRight();
}
