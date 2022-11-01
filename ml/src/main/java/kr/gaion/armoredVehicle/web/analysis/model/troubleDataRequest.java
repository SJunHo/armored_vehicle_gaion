package kr.gaion.armoredVehicle.web.analysis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class troubleDataRequest {
	private String sdaid;
	private String part;
	private String startDate;
	private String endDate;
	int page;
	int size;
}
