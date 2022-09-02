package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmpntsrplcResponse {

	private String grid;
	private String grnm;
	private String msg;
	private String stdval;
	private String prdval;
	private String nmval;
	private Date mdfcdt;
	private String mdfr;
	private char usedvcd;
}
