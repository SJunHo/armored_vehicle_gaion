package kr.gaion.railroad2.algorithm.dto.response;

import kr.gaion.railroad2.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LinearRegressionTrainResponse extends ClassificationResponse {
  public LinearRegressionTrainResponse(@NonNull ResponseType type) {
    super(type);
  }

  private double[] coefficients;
  private List<?> residuals;
  private double rootMeanSquaredError;
  private double r2;
}
