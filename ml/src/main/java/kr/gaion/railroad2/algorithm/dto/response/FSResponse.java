package kr.gaion.railroad2.algorithm.dto.response;

import kr.gaion.railroad2.algorithm.dto.ResponseType;
import kr.gaion.railroad2.ml.dto.RailSensorData;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FSResponse extends AlgorithmResponse {
  public FSResponse(@NonNull ResponseType type) {
    super(type);
  }
  private List<String> filteredFeatures;
  private List<String> selectedFields;
  private String csv;
  private int numPrincipalComponents;
}
