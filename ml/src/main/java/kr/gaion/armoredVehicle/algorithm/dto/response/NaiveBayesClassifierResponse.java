package kr.gaion.armoredVehicle.algorithm.dto.response;

import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import lombok.NonNull;

public class NaiveBayesClassifierResponse extends ClassificationResponse {
  public NaiveBayesClassifierResponse(@NonNull ResponseType type) {
    super(type);
  }
}
