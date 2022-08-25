package kr.co.gaion.scas.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Cmpntsrplc {
	
	private String grid;
	private Date crtdt;
	private String crtor;
	private Date mdfcdt;
	private String mdfr;
	private String msg;
	private String stdval;
	private String prdval;
	private String nmval;
	private boolean applicability;
	
}
