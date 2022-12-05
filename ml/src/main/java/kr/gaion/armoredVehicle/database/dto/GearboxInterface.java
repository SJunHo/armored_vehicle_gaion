package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface GearboxInterface {
    int getIDX();

    // AI_Predict
    int getAI_GEAR();

    // from Gearbox
    double getW_RPM();

    double getG_V_OverallRMS();

    double getG_V_Wheel1X();

    double getG_V_Wheel2X();

    double getG_V_Pinion1X();

    double getG_V_Pinion2X();

    double getG_V_GMF1X();

    double getG_V_GMF2X();

    // from Engine
    double getAC_h();

    double getAC_v();

    double getAC_a();

    Date getDATE();
}
