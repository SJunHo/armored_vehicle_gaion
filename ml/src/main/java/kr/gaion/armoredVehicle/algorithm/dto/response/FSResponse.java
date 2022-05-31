package kr.gaion.armoredVehicle.algorithm.dto.response;

import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
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
