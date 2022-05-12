package kr.gaion.railroad2.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
@ConfigurationProperties("elasticsearch")
@Getter
@Setter
public class ESIndexConfig implements Serializable {
  private String index;
  private String readingType;
  private String clusterName;
  private String host;
  private Integer transportPort;
}
