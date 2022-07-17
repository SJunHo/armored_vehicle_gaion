package kr.gaion.armoredVehicle.algorithm.dto.response;

import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LinearRegressionTrainResponse extends RegressionResponse {
  // DAO의 Response. 즉, 얘는 'Linear Regression' 모델이 웹으로 다시 돌려주어야 할 결과가 가지고 있어야 하는 정보들
  public LinearRegressionTrainResponse(@NonNull ResponseType type) {
    super(type);
  }
  private List<String> predictionInfo;

  private double[] coefficients;
  private List<?> residuals;
  private double rootMeanSquaredError;
  private double r2;
}
