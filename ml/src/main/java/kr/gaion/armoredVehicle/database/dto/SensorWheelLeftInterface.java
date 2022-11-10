package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorWheelLeftInterface {
    int getIDX();

    // AI Predict
    String getAI_LW();

    String getAI_LW_ALGO();

    String getAI_LW_MODEL();

    Date getAI_LW_DATE();

//    // User Judgement
//    String getUSER_LW();
//
//    String getUSER_LW_ID();
//
//    Date getUSER_LW_DATE();

    double getW_RPM();

    double getL_W_V_2X();

    double getL_W_V_3X();

    double getL_W_S_Fault3();

    // from Engine
    double getAC_h();

    double getAC_v();

    double getAC_a();

    Date getDATE();
}
