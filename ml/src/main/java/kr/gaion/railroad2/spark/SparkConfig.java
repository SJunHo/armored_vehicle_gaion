package kr.gaion.railroad2.spark;

import kr.gaion.railroad2.elasticsearch.ESIndexConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spark")
@Getter
@Setter
public class SparkConfig {
  private String host;
  private Integer port;
  private String protocol;
  private boolean isEnableHdfs;

  public String getUri(String api) {
		var uriBuilder = this.sparkUriBuilder().append(api);
		return uriBuilder.toString();
	}

	private StringBuilder sparkUriBuilder() {
		StringBuilder uriBuilder = new StringBuilder();
		var sparkIpAddress = this.getHost();
		var sparkPort = this.getPort();

		// make uri
		uriBuilder.append(this.getProtocol());
		uriBuilder.append("://");
		uriBuilder.append(sparkIpAddress);
		uriBuilder.append(":");
		uriBuilder.append(sparkPort);
		return uriBuilder;
	}

  @Bean
  public SparkSession getSession(ESIndexConfig config) {
//    this.setMaster("yarn");
//    this.setMaster("local[16]");
    return SparkSession.builder()
				.appName("Railroad2")
				.master("local[*]")
				.config("es.index.auto.create", "true")
       	.config("es.nodes", config.getHost() + ":" + config.getTransportPort())
				.getOrCreate();
  }
}
