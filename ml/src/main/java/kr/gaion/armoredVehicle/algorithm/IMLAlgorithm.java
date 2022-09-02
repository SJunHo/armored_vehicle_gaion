package kr.gaion.armoredVehicle.algorithm;

import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.armoredVehicle.algorithm.dto.response.AlgorithmResponse;
import kr.gaion.armoredVehicle.algorithm.dto.input.BaseAlgorithmTrainInput;

public interface IMLAlgorithm<I extends BaseAlgorithmTrainInput, I2 extends BaseAlgorithmPredictInput> {
  AlgorithmResponse train(I input) throws Exception;
  AlgorithmResponse predict(I2 input) throws Exception;
}
