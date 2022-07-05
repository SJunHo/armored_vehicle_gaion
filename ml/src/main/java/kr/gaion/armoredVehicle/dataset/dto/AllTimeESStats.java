package kr.gaion.armoredVehicle.dataset.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
public class AllTimeESStats {
  private Map<String, Map<Integer, Long>> byTrainNo;
  private Map<String, Map<Integer, Long>> byCarNo;
  private Map<String, Map<Integer, Long>> byType;

  private List<Long> weightingW;
  private List<Long> weightingB;

  private Long defectScoreGt0;
  private Long defectUserGt0;

  private Map<String, Map<String, Long>> defectMatrix;
}
