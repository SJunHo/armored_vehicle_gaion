package kr.co.gaion.scas.security.jwt.model;

import java.util.HashSet;

import java.util.Set;

public class User {

  private String id;

  private String username;

  private String email;

  private String password;
  
  private char usrth;
  
  private String phonenum;
  
  private String mltrank;
  
  private String mltnum;
  
  private String mltunit;

  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public User(String id, String username, String email, String password
		  		,String phonenum, String mltrank, String mltnum, String mltunit) {
	  
	this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.phonenum = phonenum;
    this.mltrank = mltrank;
    this.mltnum = mltnum;
    this.mltunit = mltunit;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
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
