package kr.gaion.armoredVehicle.security.jwt.request;

public class SignupRequest {

  private String id;
	
  private String username;

  private String email;

  private char usrth;

  private String password;

  private String phonenum;
  
  private String mltrank;
  
  private String mltnum;
  
  private String mltunit;
  
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

public String getPhonenum() {
	return phonenum;
}

public void setPhonenum(String phonenum) {
	this.phonenum = phonenum;
}

public String getMltrank() {
	return mltrank;
}

public void setMltrank(String mltrank) {
	this.mltrank = mltrank;
}

public String getMltnum() {
	return mltnum;
}

public void setMltnum(String mltnum) {
	this.mltnum = mltnum;
}

public String getMltunit() {
	return mltunit;
}

public void setMltunit(String mltunit) {
	this.mltunit = mltunit;
}

}
