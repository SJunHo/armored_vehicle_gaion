package kr.gaion.armoredVehicle.web.security.jwt.response;

import java.util.List;


public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String userid;
  private String name;
  private List<String> roles;

  public JwtResponse(String accessToken, String userid, String name, List<String> roles) {
    this.token = accessToken;
    this.userid = userid;
    this.name = name;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public String getId() {
    return userid;
  }

  public void setId(String userid) {
    this.userid = userid;
  }

  public String getUsername() {
    return name;
  }

  public void setUsername(String name) {
    this.name = name;
  }

  public List<String> getRoles() {
    return roles;
  }
}