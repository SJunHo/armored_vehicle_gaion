package kr.co.gaion.scas.security.jwt.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

  private String id;

  private String password;

}
