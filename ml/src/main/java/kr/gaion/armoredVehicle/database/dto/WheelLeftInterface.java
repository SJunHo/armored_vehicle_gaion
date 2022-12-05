package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface WheelLeftInterface {
    int getIDX();

    // AI_Predict
    int getAI_LW();

    // from Wheel
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
