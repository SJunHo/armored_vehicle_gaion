package kr.co.gaion.scas.monitoring.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Cmncd {
	
	private int cmmcdid;
	private String code;
	private String var;
	private String expln;
	private char usedvcd;
	private Date crtdt;
	private Date mdfcdt;
}