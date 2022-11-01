package kr.gaion.armoredVehicle.web.analysis.model;

import lombok.Setter;

import java.sql.Time;
import java.util.Date;

import lombok.Getter;

@Getter
@Setter
public class Berdata {
	
	String sdaid;
	String sdanm;
	Date operdate;
	Time opertime;
	Date date;
	long time;
	double W_RPM;
	double L_B_V_OverallRMS;
	double L_B_V_1X;
	double L_B_V_6912BPFO;
	double L_B_V_6912BPFI;
	double L_B_V_6912BSF;
	double L_B_V_6912FTF;
	double L_B_V_32924BPFO;
	double L_B_V_32924BPFI;
	double L_B_V_32924BSF;
	double L_B_V_32924FTF;
	double L_B_V_32922BPFO;
	double L_B_V_32922BPFI;
	double L_B_V_32922BSF;
	double L_B_V_32922FTF;
	double L_B_V_Crestfactor;
	double L_B_V_Demodulation;
	double L_B_S_Fault1;
	double L_B_S_Fault2;
	double L_B_T_Temperature;
	double R_B_V_OverallRMS;
	double R_B_V_1X;
	double R_B_V_6912BPFO;
	double R_B_V_6912BPFI;
	double R_B_V_6912BSF;
	double R_B_V_6912FTF;
	double R_B_V_32924BPFO;
	double R_B_V_32924BPFI;
	double R_B_V_32924BSF;
	double R_B_V_32924FTF;
	double R_B_V_32922BPFO;
	double R_B_V_32922BPFI;
	double R_B_V_32922BSF;
	double R_B_V_32922FTF;
	double R_B_V_Crestfactor;
	double R_B_V_Demodulation;
	double R_B_S_Fault1;
	double R_B_S_Fault2;
	double R_B_T_Temperature;
	String filenm;
	String AI_LBPFO; //베어링 좌 외륜
	String AI_LBPFI; //베어링 좌 내륜
	String AI_LBSF; //베어링 좌 볼
	String AI_LFTF; // 베어링 좌 리테이너
	String AI_RBPFO; //베어링 우 외륜
	String AI_RBPFI; //베어링 우 내륜
	String AI_RBSF; //베어링 우 볼
	String AI_RFTF; //베어링 우 리테이너
	double AC_h;
	double AC_v;
	double AC_a;
	

}
