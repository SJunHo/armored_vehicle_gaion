package kr.gaion.armoredVehicle.algorithm.dto.response;

import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class RandomForestClassificationResponse extends ClassificationResponse {
  private String decisionTree;

  public RandomForestClassificationResponse(@NonNull ResponseType type) {
    super(type);
  }
}
