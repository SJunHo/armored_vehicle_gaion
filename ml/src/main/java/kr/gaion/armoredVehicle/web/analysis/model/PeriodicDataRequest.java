package kr.gaion.armoredVehicle.web.analysis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodicDataRequest {

	private String filenm;
	private String sdaid;
	private int page;
	private int size;
	private String tableName;
}
