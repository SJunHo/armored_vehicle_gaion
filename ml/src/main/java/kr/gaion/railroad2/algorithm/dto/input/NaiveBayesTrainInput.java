package kr.gaion.railroad2.algorithm.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaiveBayesTrainInput extends BaseAlgorithmTrainInput {
  private long seed;
  private double fraction;
  private double lambda;
}
