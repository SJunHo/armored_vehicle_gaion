package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorWheelRightInterface {
    int getIDX();

    // AI Predict
    String getAI_RW();

    String getAI_RW_ALGO();

    String getAI_RW_MODEL();

    Date getAI_RW_DATE();

//    // User Judgement
//    String getUSER_RW();
//
//    String getUSER_RW_ID();
//
//    Date getUSER_RW_DATE();

    double getW_RPM();

    double getR_W_V_2X();

    double getR_W_V_3X();

    double getR_W_S_Fault3();

    // from Engine
    double getAC_h();

    double getAC_v();

    double getAC_a();

    Date getDATE();
}
