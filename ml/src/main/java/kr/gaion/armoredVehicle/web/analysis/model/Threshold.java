package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Threshold {
	
	private String snsrid;
	private double max;
	private double min;
	private Date crtdt;
	private String crtor;
	private Date mdfcdt;
	private String mdfr;
	private boolean applicability;
	
	
}
