package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface BearingLeftBallInterface {
    int getIDX();

    // AI_Predict
    int getAI_LBSF();

    // from Bearing
    double getW_RPM();

    double getL_B_V_1X();

    double getL_B_V_6912BSF();

    double getL_B_V_32924BSF();

    double getL_B_V_32922BSF();

    double getL_B_V_Crestfactor();

    double getL_B_V_Demodulation();

    double getL_B_S_Fault1();

    double getL_B_S_Fault2();

    double getL_B_T_Temperature();

    // from Engine
    double getAC_h();

    double getAC_v();

    double getAC_a();

    Date getDATE();
}
