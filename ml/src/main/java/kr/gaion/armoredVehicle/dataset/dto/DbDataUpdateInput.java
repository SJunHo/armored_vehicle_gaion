package kr.gaion.armoredVehicle.dataset.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class DbDataUpdateInput {
  private String dataType;
  private Long id;
  private String aiAlgorithm;
  private String modelName;
  private Double aiPredict;
}
