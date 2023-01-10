package kr.gaion.armoredVehicle.algorithm.dto.response;

import kr.gaion.armoredVehicle.algorithm.dto.ResponseType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ClusterResponse extends AlgorithmResponse {
  public ClusterResponse(@NonNull ResponseType type) {
    super(type);
  }

  private boolean isProcessed;
  private List<double[]> centers;
  private List<String> predictionInfo;
  private Map<Integer, Long> totalPointsEachCluster;

  private double[] confusionMatrix;
  private String[] labels;
  private List<Map<String, Object>> actual;

  public static final String PCA_FEATURES = "pcaFeatures";
  public static final String ID_COLUMN = "label" ;
  public static final String TAG_COLUMN = "tag" ;
  public static final String EXTRA_COLUMNS = "extraColumns" ;
  public static final String FEATURES = "features";

  public static final String CLUSTER_ID = "clusterId";
  public static final String CLUSTER_ID_TXT = "Cluster Id";

  public static final String NOVELTY_RANK_TXT = "Novelty Rank";
  public static final String PREDICTED_TAG_TXT = "Predicted Tag";

  public static final String UNUSED_COLUMNS = "unUsedCols";
  public static final String FEATURES_VECTOR = "vector";
}
