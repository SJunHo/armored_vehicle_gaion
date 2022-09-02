package kr.gaion.armoredVehicle.algorithm.dto.response;

import kr.gaion.armoredVehicle.algorithm.dto.ResponseStatus;
import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AlgorithmResponse {
  // DAO의 Response. 즉, 얘는 모델 상관없이 웹으로 다시 돌려주어야 할 결과가 기본적으로 꼭 가지고 있어야 하는 정보들
  /**
   * to indicate that response is [message] or [object data]
   */
  @NonNull private final ResponseType type;

  /**
   * to indicate status of running task
   */
  private ResponseStatus status;

  private String message;

  /*
   * common responses
   */
  protected String idCol;
  protected String[] listFeatures; // #PC0002
  protected String classCol;
}
