package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorGearboxInterface {
    int getIDX();

    String getSdaId();

    // AI Predict
    Integer getAI_GEAR();

    String getAI_GEAR_ALGO();

    String getAI_GEAR_MODEL();

    Date getAI_GEAR_DATE();

    // User Judgement
    Integer getUSER_GEAR();

    String getUSER_GEAR_ID();

    Date getUSER_GEAR_DATE();

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
