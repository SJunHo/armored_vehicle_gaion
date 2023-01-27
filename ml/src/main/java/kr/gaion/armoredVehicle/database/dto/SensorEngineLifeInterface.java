package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorEngineLifeInterface {
    int getIDX();

    // AI Predict
    int getAI_Trip();

    String getAI_Trip_ALGO();

    String getAI_Trip_MODEL();

    Date getAI_Trip_DATE();

    // from Engine
    double getE_OverallRMS();

    double getE_1_2X();

    double getE_1X();

    double getE_CrestFactor();
}
