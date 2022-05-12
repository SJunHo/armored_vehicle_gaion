package kr.gaion.railroad2.algorithm.dto.input;

import kr.gaion.railroad2.algorithm.dto.DataInputOption;
import kr.gaion.railroad2.algorithm.dto.DataProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseAlgorithmPredictInput extends BaseAlgorithmInput {
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
