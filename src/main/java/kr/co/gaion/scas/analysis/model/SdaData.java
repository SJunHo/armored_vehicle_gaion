package kr.co.gaion.scas.analysis.model;

import java.util.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SdaData {
	
	private String filenm;
	private String sdaid;
	private String operdate;
	private String opertime;
	private String dttime;
	private String time;
	private double engsta;
	private double engspd;
	private int engtok;
	private int reqendtok;
	private int engload;
	private double sdhspd;
	private int accpedal;
	private int cooltemp;
	private int enggastemp;
	private int engwarning;
	private double engoilprs;
	private int engoilsta;
	private int engheat;
	private int enggov;
	private int industfil;
	private double outtemp;
	private int cooloil;
	private int coollant;
	private int fueltemp;
	private double transoutspd;
	private double transinspd;
	private int engoverctlmd;
	private int overreqtok;
	private int autotrans;
	private int detailtrans;
	private int reqtrans;
	private int currttrans;
	private int emertransmd;
	private double transoiltemp; //수정
	private int transoilheat;
	private int fanmd;
	private double fanairtemp;
	private double fancoolanttemp;
	private double fanvvalduty;
	private int parksta;
	private int _break;
	private int retdbreak;
	private int retdcho;
	private int retdtok;
	private int reqretdtok;
	private int retdoiltemp;
	private int airmaster;
	private int breakoil;
	private double frtbreakprs;
	private double backbreakprs;
	private int airtankr;
	private int airtankl;
	private int frtairtand;
	private double fuel;
	private double voltage;
	private int battsta;
	private int absoper;
	private int absyaji;
	private int abswarning;
	private double _2avgspeed;
	private double _2lspeed;
	private double _2rspeed;
	private double _3lspeed;
	private double _3rspeed;	
	private int _1lock;
	private int _2lock;
	private int dmotion;
	private int _3lock;
	private int _4lock;
	private int shiftmode;
	private int lowswitch;
	private int norswitch;
	private int highswitch;
	private int pneumatic;
	private int _8by8;
	private int _6by6;
	private int _1wheel;
	private int _2wheel;
	private int _3wheel;
	private int _4wheel;
	private int promotewater;
	private int lockrelease;
	private double lowoilprs;
	private double lowoiltemp;
	private int lowoilqty;
	private int lowoilfilter;
	private int winchclutch;
	private int backdooropen;
	private double overprs;
	private int overprseqp;
	private int ctisairprs;
	private String pbit; //int -> String
	private Date crtdt;
	private String crtor;
	private Date mdfcdt;
	private String mdfr;

	private String tableName;
	
	public String toStringForChart(List nummeric, List categoric) {
		String nummeric1 = nummeric.get(0).toString();
		String categoric1 = categoric.get(0).toString();
		
		for(int i = 1; i < nummeric.size(); i++) {
			nummeric1 = nummeric1 + "," + nummeric.get(i).toString();
		}
		for(int i = 1; i < categoric.size(); i++) {
			categoric1 = categoric1 + "," + categoric.get(i).toString();
		}
		return nummeric1 + "," + categoric1;
		
	}
	
	public String toStringForTable(String id) {
		return id + "_PERIODIC (\r\n"
				+ "  seq INT not null auto_increment, \r\n"
				+ "  FILENM varchar(30) ,\r\n"
				+ "  SDAID varchar(10) ,\r\n"
				+ "  OPERDATE varchar(20) not null ,\r\n"
				+ "  OPERTIME varchar(20) not null ,\r\n"
				+ "  DTTIME varchar(30),\r\n"
				+ "  TIME varchar(10),\r\n"
				+ "  ENGSTA double ,\r\n"
				+ "  ENGSPD double ,\r\n"
				+ "  ENGTOK int ,\r\n"
				+ "  REQENDTOK int,\r\n"
				+ "  ENGLOAD int ,\r\n"
				+ "  SDHSPD double ,\r\n"
				+ "  ACCPEDAL int ,\r\n"
				+ "  COOLTEMP int ,\r\n"
				+ "  ENGGASTEMP int ,\r\n"
				+ "  ENGWARNING int ,\r\n"
				+ "  ENGOILPRS double ,\r\n"
				+ "  ENGOILSTA int ,\r\n"
				+ "  ENGHEAT int ,\r\n"
				+ "  ENGGOV int ,\r\n"
				+ "  INDUSTFIL int ,\r\n"
				+ "  OUTTEMP double ,\r\n"
				+ "  COOLOIL int ,\r\n"
				+ "  COOLLANT int ,\r\n"
				+ "  FUELTEMP int ,\r\n"
				+ "  TRANSOUTSPD double ,\r\n"
				+ "  TRANSINSPD double ,\r\n"
				+ "  ENGOVERCTLMD int,\r\n"
				+ "  OVERREQTOK int ,\r\n"
				+ "  AUTOTRANS int,\r\n"
				+ "  DETAILTRANS int,\r\n"
				+ "  REQTRANS int ,\r\n"
				+ "  CURRTTRANS int ,\r\n"
				+ "  EMERTRANSMD int,\r\n"
				+ "  TRANSOILTEMP double ,\r\n"
				+ "  TRANSOILHEAT int ,\r\n"
				+ "  FANMD int ,\r\n"
				+ "  FANAIRTEMP double ,\r\n"
				+ "  FANCOOLANTTEMP double,\r\n"
				+ "  FANVVALDUTY double ,\r\n"
				+ "  PARKSTA int,\r\n"
				+ "  _BREAK int,\r\n"
				+ "  RETDBREAK int,\r\n"
				+ "  RETDCHO int,\r\n"
				+ "  RETDTOK int ,\r\n"
				+ "  REQRETDTOK int ,\r\n"
				+ "  RETDOILTEMP int,\r\n"
				+ "  AIRMASTER int ,\r\n"
				+ "  BREAKOIL int ,\r\n"
				+ "  FRTBREAKPRS double,\r\n"
				+ "  BACKBREAKPRS double ,\r\n"
				+ "  AIRTANKR int ,\r\n"
				+ "  AIRTANKL int ,\r\n"
				+ "  FRTAIRTAND int,\r\n"
				+ "  FUEL double,\r\n"
				+ "  VOLTAGE double,\r\n"
				+ "  BATTSTA int ,\r\n"
				+ "  ABSOPER int ,\r\n"
				+ "  ABSYAJI int ,\r\n"
				+ "  ABSWARNING int,\r\n"
				+ "  _2AVGSPEED double,\r\n"
				+ "  _2LSPEED double,\r\n"
				+ "  _2RSPEED double,\r\n"
				+ "  _3LSPEED double,\r\n"
				+ "  _3RSPEED double ,\r\n"
				+ "  _1LOCK int ,\r\n"
				+ "  _2LOCK int ,\r\n"
				+ "  DMOTION int,\r\n"
				+ "  _3LOCK int ,\r\n"
				+ "  _4LOCK int ,\r\n"
				+ "  SHIFTMODE int ,\r\n"
				+ "  LOWSWITCH int ,\r\n"
				+ "  NORSWITCH int ,\r\n"
				+ "  HIGHSWITCH int,\r\n"
				+ "  PNEUMATIC int,\r\n"
				+ "  _8BY8 int,\r\n"
				+ "  _6BY6 int ,\r\n"
				+ "  _1WHEEL int ,\r\n"
				+ "  _2WHEEL int,\r\n"
				+ "  _3WHEEL int,\r\n"
				+ "  _4WHEEL int,\r\n"
				+ "  PROMOTEWATER int ,\r\n"
				+ "  LOCKRELEASE int ,\r\n"
				+ "  LOWOILPRS double ,\r\n"
				+ "  LOWOILTEMP double ,\r\n"
				+ "  LOWOILQTY int ,\r\n"
				+ "  LOWOILFILTER int ,\r\n"
				+ "  WINCHCLUTCH int,\r\n"
				+ "  BACKDOOROPEN int ,\r\n"
				+ "  OVERPRS double,\r\n"
				+ "  OVERPRSEQP int ,\r\n"
				+ "  CTISAIRPRS int ,\r\n"
				+ "  PBIT  varchar(30) ,\r\n"
				+ "  CRTDT datetime,\r\n"
				+ "  CRTOR varchar(20),\r\n"
				+ "  MDFCDT datetime,\r\n"
				+ "  MDFR varchar(20),\r\n"
				+ "  primary key (seq)\r\n"
				+ ")";
	}
	
}
