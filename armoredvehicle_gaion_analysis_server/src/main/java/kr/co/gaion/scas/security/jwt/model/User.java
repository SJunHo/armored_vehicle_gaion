package kr.co.gaion.scas.security.jwt.model;

import java.util.HashSet;

import java.util.Set;



public class User {

  private String id;

  private String username;

  private String email;

  private String password;

  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public User(String id, String username, String email, String password) {
	 this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
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
}
