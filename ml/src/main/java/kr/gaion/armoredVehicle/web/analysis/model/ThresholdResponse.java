package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThresholdResponse {

	private String snsrid;
	private String expln;
	private double max;
	private double min;
	private Date crtdt;
	private String crtor;
	private Date mdfcdt;
	private String mdfr;
	private char usedvcd;
}
