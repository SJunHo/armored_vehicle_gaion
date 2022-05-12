package kr.gaion.railroad2.algorithm.dto.response;

import kr.gaion.railroad2.algorithm.dto.ResponseStatus;
import kr.gaion.railroad2.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AlgorithmResponse {
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
