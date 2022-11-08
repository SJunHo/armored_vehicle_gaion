package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface EngineInterface {
    int getIDX();

    // AI_Predict
    int getAI_ENGINE();

    // from Engine
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
