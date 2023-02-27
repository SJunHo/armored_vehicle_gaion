package kr.gaion.armoredVehicle.web.analysis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Grblife {

	double G_OverallRMS;
	double G_Wheel1X;
	double G_Wheel2X;
	double G_Pinion1X;
	double G_Pinion2X;
	double G_GMF1X;
	double G_GMF2X;
	int AI_Trip;
	String FILENM;
	int remainDistance;
	double remainRatio;
	double remainTime;
	String AI_Trip_DATE;
	String date;

}
