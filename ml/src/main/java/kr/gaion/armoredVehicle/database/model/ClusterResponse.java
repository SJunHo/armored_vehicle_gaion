package kr.gaion.armoredVehicle.database.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@DiscriminatorValue("clusterResponse")
public class ClusterResponse {
    @Id
    @Column(name = "cluster_id")
    private long clusterResponseId;

    @Column(name = "is_processed")
    private boolean isProcessed;

    @Type(type = "json")
    @Column( name = "centers",columnDefinition = "json")
    private List<double[]> centers;

    @Type(type = "json")
    @Column( name = "prediction_info",columnDefinition = "json")
    private List<String> predictionInfo;

    @Type(type = "json")
    @Column(name = "total_points_each_cluster",columnDefinition = "json")
    private Map<Integer, Long>  totalPointsEachCluster;

    @Type(type = "json")
    @Column( name = "confusion_matrix", columnDefinition = "json")
    private long[] confusionMatrix;

    @Type(type = "json")
    @Column(name = "tag_labels", columnDefinition = "json")
    private String[] tagLabels;

    @Type(type = "json")
    @Column(name = "actual", columnDefinition = "json")
    private List<Map<String, Object>> actual;

}
