package kr.gaion.armoredVehicle.web.analysis.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LifeThreshold {
	
	private String snsrtype;
	private Integer distance;
	private Integer years;
	private char usedvcd;
	private Date crtdt;
	private String crtor;
	private Date mdfcdt;
	private String mdfr;
}
