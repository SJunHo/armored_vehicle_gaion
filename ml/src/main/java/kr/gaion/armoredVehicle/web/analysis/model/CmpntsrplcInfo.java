package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CmpntsrplcInfo {
	private Integer criid;
	private String divs; //사단
	private String brgd; //연대
	private String bn; //대대
	private String sdaid; //차량호기
	private String sdanm; //차량이름
	private String sdatype; //차량타입
	private String grid; //발생센서ID
	private String expln; //발생센서명
	private String stdvle; //교체이후 누적거리
	private String stdval; //기준값(KM)
	private String prdvle; //교체이후 운행일
	private String prdval; //기준값(일)
	private String nmvle; // 교체이후 횟수
	private String nmval; // 기준값(횟수)
	private String msg; // 교환정보
	private Date dttime; //최근 파일 업로드 일시
	
	//검색조건
	private Date startDate;
	private Date endDate;
}
