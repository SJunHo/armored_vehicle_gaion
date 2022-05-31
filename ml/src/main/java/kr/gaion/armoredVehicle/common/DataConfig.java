package kr.gaion.armoredVehicle.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("data")
@Getter
@Setter
public class DataConfig {
  @Getter
  @Setter
  public static class DataIngestConfig {
    @Getter
    @Setter
    public static class DataIngestSchemaConfig {
      private String[] ktme;
      private String[] globiz;
    }

    private DataIngestSchemaConfig schema;
    private String separator;
  }

  @Getter
  @Setter
  public static class DataTrainingConfig {
    private String[] fieldFilterOut;
  }

  private DataIngestConfig ingest;
  private DataTrainingConfig training;
  private String[] esDatasetIndices;
}
