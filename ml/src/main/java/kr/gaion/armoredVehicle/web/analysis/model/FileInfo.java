package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {
  
	private String fileid;
	private String filenm;
	private char filesnsr;
	private char filetype;
	private char filediv;
	private char filept;
	private Date crtdt;
	private String crtor;
	private Date mdfcdt;
	private String mdfr;
	private String filepath;
	private String starttime;
	private String endtime;
	private int seq;
}
