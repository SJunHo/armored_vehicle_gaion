package kr.gaion.armoredVehicle.web.analysis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Whllife {

	double W_2X;
	double W_3X;
	double W_Fault3;
	int AI_Trip;
	String FILENM;
	int remainDistance;
	double remainRatio;
	double remainTime;
	String AI_Trip_DATE;
	String date;
}
