package kr.gaion.armoredVehicle.algorithm.dto.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseAlgorithmInput {
  private int numberPrincipalComponents;

  private List<String> featureCols;
}
