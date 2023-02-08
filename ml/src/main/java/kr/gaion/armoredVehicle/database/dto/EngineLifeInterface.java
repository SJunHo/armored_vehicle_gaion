package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface EngineLifeInterface {
    int getIDX();

    // label
    int getTrip();

    // from Engine
    double getE_OverallRMS();

    double getE_1_2X();

    double getE_1X();

    double getE_CrestFactor();

    Date getDATE();
}
