package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CmpntsrplcData {
	
	private String sdaid;		//차량ID
	private String snsrid;		//센서ID
	private String snsrnm;		//센서이름
	private String workr;		//작업자
	private Date rplcdate;		//교체일
	private String msg;			//교체사유
	private Date mdfcdt;		//수정일
	private String mdfr;			//수정자
	private String expln;		//부품명
	
	private String grid;		//code
	private String code;
	
}
