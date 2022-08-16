package kr.co.gaion.scas.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SearchRequest {
	
	private String sdaid; //차량
	private Date startDate; //검색기간
	private Date endDate;
	private String divscode;
	private String brgdbncode;
	private int page;
	private int size;
}
