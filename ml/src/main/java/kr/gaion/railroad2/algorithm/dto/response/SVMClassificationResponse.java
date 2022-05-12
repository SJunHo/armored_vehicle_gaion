package kr.gaion.railroad2.algorithm.dto.response;

import kr.gaion.railroad2.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SVMClassificationResponse extends ClassificationResponse {
  public SVMClassificationResponse(@NonNull ResponseType type) {
    super(type);
  }

	private List<?> rocByThresholds;
	private double areaUnderRoc;
	private double areaUnderPrecisionRecallCurve;
	private List<Double> thresholds;
  private List<?> precisionByThreshold;
	private List<?> recallByThreshold;
	private List<?> f1ScoreByThreshold;
	private List<?> f2ScoreByThreshold;
	private List<?> precisionRecallCurve;
}
