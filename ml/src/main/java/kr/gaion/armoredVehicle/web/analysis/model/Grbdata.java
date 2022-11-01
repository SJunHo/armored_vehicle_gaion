package kr.gaion.armoredVehicle.web.analysis.model;

import java.sql.Time;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Grbdata {
	
	String sdaid;
	String sdanm;
	Date operdate;
	Time opertime;
	Date date;
	long time;
	double W_RPM;
	double G_V_OverallRMS;
	double G_V_Wheel1X;
	double G_V_Wheel2X;
	double G_V_Pinion1X;
	double G_V_Pinion2X;
	double G_V_GMF1X;
	double G_V_GMF2X;
	String filenm;
	String AI_GEAR;
	double AC_h;
	double AC_v;
	double AC_a;
}
