package kr.gaion.railroad2.algorithm.dto.response;

import kr.gaion.railroad2.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class ClassificationResponse extends AlgorithmResponse{
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
