package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorGearboxLifeInterface {
    int getIDX();

    // AI Predict
    int getAI_Trip();

    String getAI_Trip_ALGO();

    String getAI_Trip_MODEL();

    Date getAI_Trip_DATE();

    // from Gearbox
    double getG_OverallRMS();

    double getG_Wheel1X();

    double getG_Wheel2X();

    double getG_Pinion1X();

    double getG_Pinion2X();

    double getG_GMF1X();

    double getG_GMF2X();
}
