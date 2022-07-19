package kr.co.gaion.scas.security.jwt.request;

import java.util.Set;

public class SignupRequest {

  private String id;
	
  private String username;

  private String email;

  private char usrth;

  private String password;

  
  public String getId() {
	return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public char getUsrth() {
	  return usrth;
  }

  public void setUsrth(char usrth) {
	  this.usrth = usrth;
  }
}
