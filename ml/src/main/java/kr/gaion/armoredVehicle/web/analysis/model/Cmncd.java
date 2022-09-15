package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Cmncd {
	
	private int cmncdid;
	private String code;
	private String var;
	private String expln;
	private char usedvcd;
	private Date crtdt;
	private Date mdfcdt;
	private String groupcode;
}