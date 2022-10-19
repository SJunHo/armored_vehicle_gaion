package kr.gaion.armoredVehicle.web.analysis.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Prinfo {
	
	private String divs;				//사단
	private String expln;
	private String sdaid;			//차량ID
	private String brgd;				//연대
	private String dttime;			//발생시간
	private String stdvle;			//발생값
	private String stdval;			//기준값
	private String prdvle;
	private String prdval;
	private String nmvle;
	private String nmval;
	private String msg;				//교환정보
	private String code;
	
	private String grid;
}
