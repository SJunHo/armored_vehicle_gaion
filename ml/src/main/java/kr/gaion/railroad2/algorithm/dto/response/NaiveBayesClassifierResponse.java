package kr.gaion.railroad2.algorithm.dto.response;

import kr.gaion.railroad2.algorithm.dto.ResponseType;
import lombok.NonNull;

public class NaiveBayesClassifierResponse extends ClassificationResponse {
  public NaiveBayesClassifierResponse(@NonNull ResponseType type) {
    super(type);
  }
}
