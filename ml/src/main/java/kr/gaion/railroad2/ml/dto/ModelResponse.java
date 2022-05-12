package kr.gaion.railroad2.ml.dto;

import kr.gaion.railroad2.algorithm.dto.response.ClassificationResponse;
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
