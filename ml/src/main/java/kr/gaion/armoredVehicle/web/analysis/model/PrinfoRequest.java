package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class PrinfoRequest {

	private String sdaid; //차량
	private Date startDate; //검색기간
	private Date endDate; //검색기간
	private String divscode;
	private String brgdbncode;
}
