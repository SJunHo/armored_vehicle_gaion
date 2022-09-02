package kr.gaion.armoredVehicle.algorithm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictionInfo<T, V> {
  private T predictedValue;
  private T actualValue;
  private V features;
}
