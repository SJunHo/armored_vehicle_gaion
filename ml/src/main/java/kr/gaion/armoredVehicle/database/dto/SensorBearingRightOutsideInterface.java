package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorBearingRightOutsideInterface {
    int getIDX();

    // AI Predict
    String getAI_RBPFO();

    String getAI_RBPFO_ALGO();

    String getAI_RBPFO_MODEL();

    Date getAI_RBPFO_DATE();

    // User Judgement
    String getUSER_RBPFO();

    String getUSER_RBPFO_ID();

    Date getUSER_RBPFO_DATE();

    // from Bearing
    double getW_RPM();

    double getR_B_V_1X();

    double getR_B_V_6912BPFO();

    double getR_B_V_32924BPFO();

    double getR_B_V_32922BPFO();

    double getR_B_V_Crestfactor();

    double getR_B_V_Demodulation();

    double getR_B_S_Fault1();

    double getR_B_S_Fault2();

    double getR_B_T_Temperature();

    // from Engine
    double getAC_h();

    double getAC_v();

    double getAC_a();

    Date getDATE();
}
