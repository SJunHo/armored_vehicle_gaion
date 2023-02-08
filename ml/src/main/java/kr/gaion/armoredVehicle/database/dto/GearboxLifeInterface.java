package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface GearboxLifeInterface {
    int getIDX();

    // label
    int getTrip();

    // from Gearbox
    double getG_OverallRMS();

    double getG_Wheel1X();

    double getG_Wheel2X();

    double getG_Pinion1X();

    double getG_Pinion2X();

    double getG_GMF1X();

    double getG_GMF2X();

    Date getDate();
}
