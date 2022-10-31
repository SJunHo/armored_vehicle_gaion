package kr.gaion.armoredVehicle.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotNull;

@JsonSerialize
public class AuthDTO {
  public static class AuthRequestDTO {
    //public @NotNull String username;
    // username -> id 변경
    public @NotNull String id;

    public @NotNull String password;
  }

  public static class AuthResponseDTO {
    public String token;
    public String refreshToken;
  }
}
