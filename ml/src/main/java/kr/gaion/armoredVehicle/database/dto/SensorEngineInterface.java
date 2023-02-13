package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorEngineInterface {
    int getIDX();

    String getSDAID();

    // AI Predict
    Integer getAI_ENGINE();

    String getAI_ENGINE_ALGO();

    String getAI_ENGINE_MODEL();

    Date getAI_ENGINE_DATE();

    // User Judgement
    Integer getUSER_ENGINE();

    String getUSER_ENGINE_ID();

    Date getUSER_ENGINE_DATE();

    double getW_RPM();

    double getE_V_OverallRMS();

    double getE_V_1_2X();

    double getE_V_1X();

    double getE_V_Crestfactor();

    double getAC_h();

    double getAC_v();

    double getAC_a();

    Date getDATE();
}
