package kr.gaion.armoredVehicle.web.analysis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Berlife {

	double B_OverallRMS;
	double B_1X;
	double B_6912BPFO;
	double B_6912BPFI;
	double B_6912BSF;
	double B_6912FTF;
	double B_32924BPFO;
	double B_32924BPFI;
	double B_32924BSF;
	double B_32924FTF;
	double B_32922BPFO;
	double B_32922BPFI;
	double B_32922BSF;
	double B_32922FTF;
	double B_CrestFactor;
	double B_Demodulation;
	double B_Fault1;
	double B_Fault2;
	double B_Temperature;
	int AI_Trip;
	String FILENM;
	int remainDistance;
	double remainRatio;
	double remainTime;
	String AI_Trip_DATE;
	String date;

}
