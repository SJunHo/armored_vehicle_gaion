package kr.gaion.railroad2.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("auth")
@Getter
@Setter
public class JwtConfiguration {
  @JsonProperty
  private String secret;

  @JsonProperty
  private String issuer;

  @JsonProperty
  private long expiresAfter; // hours
}
