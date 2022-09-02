package kr.gaion.armoredVehicle.dataset.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateDataLookupInput {
  private String lookupName;

  private String index;

  private String delimiter;

  private Integer indexOfLabeledField;
}
