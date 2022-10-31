package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CmpntsrplcHistry {
	
	private String grid;
	private String sdaid;
	private String workr;
	private Date rplcdate;
	private String msg;
	private Date mdfcdt;
	private String mdfr;
}
