package kr.gaion.armoredVehicle.web.analysis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Englife {

	double E_OverallRMS;
	double E_1_2X;
	double E_1X;
	double E_CrestFactor;
	double AI_Trip;
	String FILENM;
	String SDAID;
	int remainDistance;
	double remainTime;
	String AI_Trip_DATE;
	String date;
}
