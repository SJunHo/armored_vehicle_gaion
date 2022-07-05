package kr.gaion.railroad2.algorithm.dto.input;

import kr.gaion.railroad2.algorithm.dto.DataInputOption;
import kr.gaion.railroad2.algorithm.dto.DataProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseAlgorithmPredictInput extends BaseAlgorithmInput {
  // DAO의 input이니까 즉 Request. 즉, 얘는 웹으로 통해 들어오는 사용자가 선택한 알고리즘의 '예측'을 위한 정보들(Request)
  private String classCol;
  private String modelName;
  private DataProvider dataProvider;
  private DataInputOption dataInputOption;
  private List<String> listFieldsForPredict;
  private List<String> esDocIds;
  private FileInput fileInput;
  private Double threshold;
  private boolean dimensionalityReduction;
}
