package kr.gaion.armoredVehicle.elasticsearch;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Component
public class EsConnectorBean {
  @Bean
  EsConnector getEsConnector(ESIndexConfig config) {
    try {
      return new EsConnector(config.getHost(), config.getTransportPort());
    } catch (UnknownHostException e) {
      e.printStackTrace();
      return null;
    }
  }
}
