package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorWheelLifeInterface {
    int getIDX();

    // AI Predict
    int getAI_Trip();

    String getAI_Trip_ALGO();

    String getAI_Trip_MODEL();

    Date getAI_Trip_DATE();

    // from Engine
    double getW_2X();

    double getW_3X();

    double getW_Fault3();
}
