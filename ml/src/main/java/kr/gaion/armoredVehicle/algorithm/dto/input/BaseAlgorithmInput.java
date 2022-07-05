package kr.gaion.armoredVehicle.algorithm.dto.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseAlgorithmInput {
  // DAO의 input이니까 즉 Request. 즉,모든 알고리즘의 input이 반드시 가져야하는 정보(훈련, 예측 관계 없이)
  private int numberPrincipalComponents;

  private List<String> featureCols;

}
