package kr.gaion.armoredVehicle.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConfigurationProperties("database")
@Getter
@Setter
public class DatabaseConfiguration  {
  @JsonProperty
  private String url;

  @JsonProperty
  private String driverClass;

  @JsonProperty
  private String password;

  @JsonProperty
  private String user;

  @JsonProperty
  private Properties properties;
}
