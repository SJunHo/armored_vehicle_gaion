package kr.gaion.armoredVehicle.algorithm.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClusterTrainInput extends BaseAlgorithmTrainInput {
    //
    private double fraction;

    private int numClusters;
    private int numIterations;

    // IF
    private int numEstimators;
    private int maxFeatures;
    private int maxSamples;
}
