package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorBearingLeftRetainerInterface {
    int getIDX();

    // AI Predict
    String getAI_LFTF();

    String getAI_LFTF_ALGO();

    String getAI_LFTF_MODEL();

    Date getAI_LFTF_DATE();

//    // User Judgement
//    String getUSER_LFTF();
//
//    String getUSER_LFTF_ID();
//
//    Date getUSER_LFTF_DATE();

    // from Bearing
    double getW_RPM();

    double getL_B_V_1X();

    double getL_B_V_6912FTF();

    double getL_B_V_32924FTF();

    double getL_B_V_32922FTF();

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
