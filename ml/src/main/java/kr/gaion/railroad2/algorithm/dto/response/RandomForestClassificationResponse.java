package kr.gaion.railroad2.algorithm.dto.response;

import kr.gaion.railroad2.algorithm.dto.ResponseType;
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
