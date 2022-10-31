package kr.gaion.armoredVehicle.web.security.jwt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Usercd {
	private String userid;

	  private String name;

	  private String pwd;

	  private char usrth;
	  
	  private String rnkcd;
	  
	  private String srvno;
	  
	  private char usedvcd;
	  
	  private Date blocktime;
	  
	  private Date rctlogindt;
	  
	  private Date rctpwdchngdt;
	  
	  private Date crtdt;
	  
	  private Date mdfcdt;
	  
	  private String divs;
	  
	  private String brgd;
	  
	  private String bn;
	  
	  private String rspofc;
	  
	  private String telno1;
	  
	  private String telno2;
	  
	  private Set<Role> roles = new HashSet<>();

	  public Usercd() {
		  
	  }
	  
	public Usercd(String userid, String name, String pwd, char usrth, String rnkcd, String srvno, Date crtdt,
			String divs, String brgd, String bn, String rspofc, String telno1, String telno2) {
		super();
		this.userid = userid;
		this.name = name;
		this.pwd = pwd;
		this.usrth = usrth;
		this.rnkcd = rnkcd;
		this.srvno = srvno;
		this.crtdt = crtdt;
		this.divs = divs;
		this.brgd = brgd;
		this.bn = bn;
		this.rspofc = rspofc;
		this.telno1 = telno1;
		this.telno2 = telno2;
	}
	  
}
