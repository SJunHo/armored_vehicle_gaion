package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface WheelRightInterface {
    int getIDX();

    // AI_Predict
    int getAI_RW();

    // from Wheel
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
