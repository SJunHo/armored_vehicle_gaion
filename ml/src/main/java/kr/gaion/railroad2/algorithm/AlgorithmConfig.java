package kr.gaion.railroad2.algorithm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("algorithm")
@Getter
@Setter
public class AlgorithmConfig {
  private Integer maxResult;
}
