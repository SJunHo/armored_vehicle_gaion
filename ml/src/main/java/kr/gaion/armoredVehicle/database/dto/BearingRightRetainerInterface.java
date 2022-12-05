package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface BearingRightRetainerInterface {
    int getIDX();

    // AI_Predict
    int getAI_RFTF();

    // from Bearing
    double getW_RPM();

    double getR_B_V_1X();

    double getR_B_V_6912FTF();

    double getR_B_V_32924FTF();

    double getR_B_V_32922FTF();

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
