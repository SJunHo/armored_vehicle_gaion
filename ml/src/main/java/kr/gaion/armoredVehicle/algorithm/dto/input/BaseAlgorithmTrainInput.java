package kr.gaion.armoredVehicle.algorithm.dto.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseAlgorithmTrainInput extends BaseAlgorithmInput {
  // DAO의 input이니까 즉 Request. 즉, 얘는 웹으로 통해 들어오는 사용자가 선택한 알고리즘의 '학습'을 위한 정보들(Request)
  private String trainingESIndex;
  /**
   * Number of classes for classification
   */
  private int numClasses;

  /**
   * Number of trees in the random forest.
   */
  private int numTrees;

  /**
   * Number of features to consider for splits at each node. Supported values:
   * "auto", "all", "sqrt", "log2", "onethird". If "auto" is set, this parameter
   * is set based on numTrees: if numTrees == 1, set to "all"; if numTrees is
   * greater than 1 (forest) set to "sqrt".
   */
  private String featureSubsetStrategy;

  /**
   * Criterion used for information gain calculation. Supported values: "gini"
   * (recommended) or "entropy".
   */
  private String impurity;

  /**
   * Maximum depth of the tree (e.g. depth 0 means 1 leaf node, depth 1 means 1
   * internal node + 2 leaf nodes). (suggested value: 4)
   */
  private int maxDepths;

  /**
   * Maximum number of bins used for splitting features (suggested value: 100)
   */
  private int maxBins;
  private int bin;

  private List<String> filterOutFields;

  private String classCol;

  private String modelName;

  private boolean featuresSelectionEnableFlg;

  private String action;

  private double fraction;

  private long lSeed;

  private long seed;

  private int maxIter;

  // for mlp
  private int layers = 2;

  // for mlp
  private int blockSize = 128;

  // for lr
  private double lambda;

  // for lr
  private double regParam;

  private boolean intercept;

  // for lr
  private double elasticNetMixing;
}
