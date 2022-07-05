package kr.gaion.armoredVehicle.algorithm.dto.response;

import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClassificationResponse extends AlgorithmResponse{
  // DAO의 Response. 즉,얘는 '분류' 모델이 웹으로 다시 돌려주어야 할 결과가 가지고 있어야 하는 정보들
  protected double[] confusionMatrix;
  protected String[] labels;
  protected double weightedFalsePositiveRate;
  protected double weightedFMeasure;
  protected double accuracy;
  protected double weightedPrecision;
  protected double weightedRecall;
  protected double weightedTruePositiveRate;
  protected List<String> predictionInfo;

  /**
   * each element of list is a line which represents for predicted label, actual
   * label and the respective features
   */
  protected List<String> predictedActualFeatureLine;

  /**
   * each element of list is a line which represents for predicted label and the
   * respective features
   */
  protected List<String> predictedFeatureLine;

  public ClassificationResponse(@NonNull ResponseType type) {
    super(type);
  }

  public ClassificationResponse() {
    super(ResponseType.MESSAGE);
  }
}
