package kr.gaion.armoredVehicle.ml.dto;

import kr.gaion.armoredVehicle.algorithm.dto.response.ClassificationResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelResponse {
  private String esId;
  private String modelName;
  private ClassificationResponse response;
  private String description;
  private Boolean checked;
}
