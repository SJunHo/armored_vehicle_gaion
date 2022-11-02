package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.dto.*;
import kr.gaion.armoredVehicle.database.model.TrainingBearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingBearingRepository extends JpaRepository<TrainingBearing, Long> {
    // Bearing Left Ball
    @Query(value = " SELECT b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BSF, b.L_B_V_32924BSF, b.L_B_V_32922BSF, b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
            " FROM BERTRNNG b, ENGTRNNG e " +
            " WHERE b.`DATE` = e.`DATE` ", nativeQuery = true)
    List<BearingLeftOutsideInput> findBearingLeftBall();

    // Bearing Left Inside
    @Query(value = " SELECT b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BPFI, b.L_B_V_32924BPFI, b.L_B_V_32922BPFI, b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
            " FROM BERTRNNG b, ENGTRNNG e " +
            " WHERE b.`DATE` = e.`DATE` ", nativeQuery = true)
    List<BearingLeftInsideInput> findBearingLeftInside();

    // Bearing Left Outside
    @Query(value = " SELECT b.W_RPM, b.L_B_V_1X, b.L_B_V_6912BPFO, b.L_B_V_32924BPFO, b.L_B_V_32922BPFO, b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
            " FROM BERTRNNG b, ENGTRNNG e " +
            " WHERE b.`DATE` = e.`DATE` ", nativeQuery = true)
    List<BearingLeftOutsideInput> findBearingLeftOutside();

    // Bearing Left Retainer
    @Query(value = " SELECT b.W_RPM, b.L_B_V_1X, b.L_B_V_6912FTF, b.L_B_V_32924FTF, b.L_B_V_32922FTF, b.L_B_V_Crestfactor, b.L_B_V_Demodulation, b.L_B_S_Fault1, b.L_B_S_Fault2, b.L_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
            " FROM BERTRNNG b, ENGTRNNG e " +
            " WHERE b.`DATE` = e.`DATE` ", nativeQuery = true)
    List<BearingLeftRetainerInput> findBearingLeftRetainer();

    // Bearing Right Ball
    @Query(value = " SELECT b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BSF, b.R_B_V_32924BSF, b.R_B_V_32922BSF, b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
            " FROM BERTRNNG b, ENGTRNNG e " +
            " WHERE b.`DATE` = e.`DATE` ", nativeQuery = true)
    List<BearingRightBallInput> findBearingRightBall();

    // Bearing Right Inside
    @Query(value = " SELECT b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BPFI, b.R_B_V_32924BPFI, b.R_B_V_32922BPFI, b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
            " FROM BERTRNNG b, ENGTRNNG e " +
            " WHERE b.`DATE` = e.`DATE` ", nativeQuery = true)
    List<BearingRightInsideInput> findBearingRightInside();

    // Bearing Right Outside
    @Query(value = " SELECT b.W_RPM, b.R_B_V_1X, b.R_B_V_6912BPFO, b.R_B_V_32924BPFO, b.R_B_V_32922BPFO, b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
            " FROM BERTRNNG b, ENGTRNNG e " +
            " WHERE b.`DATE` = e.`DATE` ", nativeQuery = true)
    List<BearingRightOutsideInput> findBearingRightOutside();

    // Bearing Right Retainer
    @Query(value = " SELECT b.W_RPM, b.R_B_V_1X, b.R_B_V_6912FTF, b.R_B_V_32924FTF, b.R_B_V_32922FTF, b.R_B_V_Crestfactor, b.R_B_V_Demodulation, b.R_B_S_Fault1, b.R_B_S_Fault2, b.R_B_T_Temperature, e.AC_h, e.AC_v, e.AC_a " +
            " FROM BERTRNNG b, ENGTRNNG e " +
            " WHERE b.`DATE` = e.`DATE` ", nativeQuery = true)
    List<BearingRightRetainerInput> findBearingRightRetainer();
}
