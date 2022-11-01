package kr.gaion.armoredVehicle.web.analysis.model;

import java.sql.Time;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Whldata {
	String sdaid;
	String sdanm;
	Date operdate;
	Time opertime;
	Date date;
	long time;
	double W_RPM;
	double L_W_V_2X;
	double L_W_V_3X;
	double L_W_S_Fault3;
	double R_W_V_2X;
	double R_W_V_3X;
	double R_W_S_Fault3;
	String filenm;
	String AI_LW;	//좌측 휠
	String AI_RW;	//우측 휠
	double AC_h;
	double AC_v;
	double AC_a;
}
