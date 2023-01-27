package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorBearingLifeInterface {
    int getIDX();
    
    // AI Predict
    int getAI_Trip();

    String getAI_Trip_ALGO();

    String getAI_Trip_MODEL();

    Date getAI_Trip_DATE();

    // from Bearing
    double getB_OverallRMS();

    double getB_1X();

    double getB_6912BPFO();

    double getB_6912BPFI();

    double getB_6912BSF();

    double getB_6912FTF();

    double getB_32924BPFO();

    double getB_32924BPFI();

    double getB_32924BSF();

    double getB_32924FTF();

    double getB_32922BPFO();

    double getB_32922BPFI();

    double getB_32922BSF();

    double getB_32922FTF();

    double getB_CrestFactor();

    double getB_Demodulation();

    double getB_Fault1();

    double getB_Fault2();

    double getB_Temperature();
}
