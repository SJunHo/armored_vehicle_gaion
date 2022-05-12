package kr.gaion.railroad2.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotNull;

@JsonSerialize
public class AuthDTO {
  public static class AuthRequestDTO {
    public @NotNull String username;

    public @NotNull String password;
  }

  public static class AuthResponseDTO {
    public String token;
    public String refreshToken;
  }
}
