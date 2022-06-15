package kr.co.gaion.scas.monitoring.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SdadataEvent {

	private String filenm;
	private String sdaid;
	private String operdate;
	private String opertime;
	private String date;
	private String time;
	private String eventcd;
	private String msg;
	private String state;
	private Date crtdt;
	private String crtor;
	private Date mdfcdt;
	private String mdfr;
	
	public SdadataEvent(String filenm, String sdaid, String operdate, String opertime, String date, String time,
			String eventcd, String msg, String state, Date crtdt, String crtor, Date mdfcdt, String mdfr) {
		super();
		this.filenm = filenm;
		this.sdaid = sdaid;
		this.operdate = operdate;
		this.opertime = opertime;
		this.date = date;
		this.time = time;
		this.eventcd = eventcd;
		this.msg = msg;
		this.state = state;
		this.crtdt = crtdt;
		this.crtor = crtor;
		this.mdfcdt = mdfcdt;
		this.mdfr = mdfr;
	}
	
	public SdadataEvent(String[] stringArray) {
		this.sdaid = stringArray[0];
		this.operdate = stringArray[1];
		this.opertime = stringArray[2];
		this.date = stringArray[3];
		this.time = stringArray[4];
		this.eventcd = stringArray[5];
		this.msg = stringArray[6];
		this.state = stringArray[7];
	}
}
