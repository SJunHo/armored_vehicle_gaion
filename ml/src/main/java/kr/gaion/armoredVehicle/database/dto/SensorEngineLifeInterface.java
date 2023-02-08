package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorEngineLifeInterface {
    int getIDX();

    // AI Predict
    Integer getAI_Trip();

    String getAI_Trip_ALGO();

    String getAI_Trip_MODEL();

    Date getAI_Trip_DATE();

    // from Engine
    Double getE_OverallRMS();

    Double getE_1_2X();

    Double getE_1X();

    Double getE_CrestFactor();

    Date getDate();
}
