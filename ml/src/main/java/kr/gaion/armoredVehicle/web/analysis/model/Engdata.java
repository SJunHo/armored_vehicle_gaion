package kr.gaion.armoredVehicle.web.analysis.model;

import java.sql.Time;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Engdata {

	private String sdaid;
	private String sdanm;
	private Date operdate;
	private Time opertime;
	private Date date;
	private long time;
	private double W_RPM;
	private double E_V_OverallRMS;
	private double E_V_1_2X;
	private double E_V_1X;
	private double E_V_Crestfactor;
	private double AC_h;
	private double AC_v;
	private double AC_a;
	private double LA;
	private double LO;
	private String filenm;
	private String AI_ENGINE;
}
