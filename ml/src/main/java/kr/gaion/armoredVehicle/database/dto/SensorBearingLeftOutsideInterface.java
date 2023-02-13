package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorBearingLeftOutsideInterface {
    int getIDX();

    String getSDAID();

    // AI Predict
    Integer getAI_LBPFO();

    String getAI_LBPFO_ALGO();

    String getAI_LBPFO_MODEL();

    Date getAI_LBPFO_DATE();

    // User Judgement
    Integer getUSER_LBPFO();

    String getUSER_LBPFO_ID();

    Date getUSER_LBPFO_DATE();

    // from Bearing
    double getW_RPM();

    double getL_B_V_1X();

    double getL_B_V_6912BPFO();

    double getL_B_V_32924BPFO();

    double getL_B_V_32922BPFO();

    double getL_B_V_Crestfactor();

    double getL_B_V_Demodulation();

    double getL_B_S_Fault1();

    double getL_B_S_Fault2();

    double getL_B_T_Temperature();

    // from Engine
    double getAC_h();

    double getAC_v();

    double getAC_a();

    Date getDATE();
}
