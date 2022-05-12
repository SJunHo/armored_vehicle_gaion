package kr.gaion.railroad2.dataset.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ESDataUpdateInput {
  private String esId;
  private Integer gDefectProb;
  private Integer uDefectProb;
  private Integer defectUser;
}
