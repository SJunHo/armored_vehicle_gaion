package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface BearingRightBallInterface {
    int getIDX();

    // AI_Predict
    int getAI_RBSF();

    // from Bearing
    double getW_RPM();

    double getR_B_V_1X();

    double getR_B_V_6912BSF();

    double getR_B_V_32924BSF();

    double getR_B_V_32922BSF();

    double getR_B_V_Crestfactor();

    double getR_B_V_Demodulation();

    double getR_B_S_Fault1();

    double getR_B_S_Fault2();

    double getR_B_T_Temperature();

    // from Engine
    double getAC_h();

    double getAC_v();

    double getAC_a();

    Date getDATE();
}