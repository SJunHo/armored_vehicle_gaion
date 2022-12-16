package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorBearingRightInsideInterface {
    int getIDX();

    // AI Predict
    Integer getAI_RBPFI();

    String getAI_RBPFI_ALGO();

    String getAI_RBPFI_MODEL();

    Date getAI_RBPFI_DATE();

    // User Judgement
    Integer getUSER_RBPFI();

    String getUSER_RBPFI_ID();

    Date getUSER_RBPFI_DATE();

    // from Bearing
    double getW_RPM();

    double getR_B_V_1X();

    double getR_B_V_6912BPFI();

    double getR_B_V_32924BPFI();

    double getR_B_V_32922BPFI();

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
