package kr.gaion.armoredVehicle.algorithm.dto.response;

import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegressionResponse extends AlgorithmResponse{
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

  private double[] coefficients;
  private List<?> residuals;
  private double rootMeanSquaredError;
  private double r2;


  public RegressionResponse(@NonNull ResponseType type) {
    super(type);
  }

  public RegressionResponse() {
    super(ResponseType.MESSAGE);
  }
}
