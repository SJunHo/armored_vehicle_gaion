package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorWheelLifeInterface {
    int getIDX();

    String getSdaId();

    // AI Predict
    Integer getAI_Trip();

    String getAI_Trip_ALGO();

    String getAI_Trip_MODEL();

    Date getAI_Trip_DATE();

    // from Engine
    Double getW_2X();

    Double getW_3X();

    Double getW_Fault3();

    Date getDate();
}
