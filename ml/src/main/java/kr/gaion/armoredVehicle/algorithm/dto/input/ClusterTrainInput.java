package kr.gaion.armoredVehicle.algorithm.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClusterTrainInput extends BaseAlgorithmTrainInput  {
  private int numClusters;
  private int numIterations;

  // IF
  private boolean bootstrap;
  private int maxFeatures;
  private int maxSamples;
}
