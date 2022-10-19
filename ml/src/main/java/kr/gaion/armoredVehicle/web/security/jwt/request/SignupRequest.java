package kr.gaion.armoredVehicle.web.security.jwt.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SignupRequest {

  private String userid;
  private String pwd;
  private String name;
  private String rnkcd;
  private String srvno;
  private char usedvcd;
  private Date crtdt;
  private String divs;
  private String brgd;
  private String bn;
  private String rspofc;
  private String telno1;
  private String telno2;
  private char usrth;

}
