package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorGearboxLifeInterface {
    int getIDX();

    // AI Predict
    Integer getAI_Trip();

    String getAI_Trip_ALGO();

    String getAI_Trip_MODEL();

    Date getAI_Trip_DATE();

    // from Gearbox
    Double getG_OverallRMS();

    Double getG_Wheel1X();

    Double getG_Wheel2X();

    Double getG_Pinion1X();

    Double getG_Pinion2X();

    Double getG_GMF1X();

    Double getG_GMF2X();
}
