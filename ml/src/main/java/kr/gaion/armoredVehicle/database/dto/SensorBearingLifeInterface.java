package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface SensorBearingLifeInterface {
    int getIDX();

    String getSdaId();

    // AI Predict
    Integer getAI_Trip();

    String getAI_Trip_ALGO();

    String getAI_Trip_MODEL();

    Date getAI_Trip_DATE();

    // from Bearing
    Double getB_OverallRMS();

    Double getB_1X();

    Double getB_6912BPFO();

    Double getB_6912BPFI();

    Double getB_6912BSF();

    Double getB_6912FTF();

    Double getB_32924BPFO();

    Double getB_32924BPFI();

    Double getB_32924BSF();

    Double getB_32924FTF();

    Double getB_32922BPFO();

    Double getB_32922BPFI();

    Double getB_32922BSF();

    Double getB_32922FTF();

    Double getB_CrestFactor();

    Double getB_Demodulation();

    Double getB_Fault1();

    Double getB_Fault2();

    Double getB_Temperature();

    Date getDate();
}
