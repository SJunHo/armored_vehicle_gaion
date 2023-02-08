package kr.gaion.armoredVehicle.database.dto;

import java.util.Date;

public interface WheelLifeInterface {
    int getIDX();

    // label
    int getTrip();

    // from Engine
    double getW_2X();

    double getW_3X();

    double getW_Fault3();

    Date getDate();
}
