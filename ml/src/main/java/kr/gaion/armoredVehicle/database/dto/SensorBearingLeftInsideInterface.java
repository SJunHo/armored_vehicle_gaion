package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorBearingLeftInsideInterface {
    int getIDX();

    // AI Predict
    String getAI_LBPFI();

    String getAI_LBPFI_ALGO();

    String getAI_LBPFI_MODEL();

    Date getAI_LBPFI_DATE();

    // User Judgement
    String getUSER_LBPFI();

    String getUSER_LBPFI_ID();

    Date getUSER_LBPFI_DATE();

    // from Bearing
    double getW_RPM();

    double getL_B_V_1X();

    double getL_B_V_6912BPFI();

    double getL_B_V_32924BPFI();

    double getL_B_V_32922BPFI();

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
