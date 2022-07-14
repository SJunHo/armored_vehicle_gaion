package kr.gaion.armoredVehicle.database.model;

import kr.gaion.armoredVehicle.algorithm.dto.response.ClusterResponse;
import kr.gaion.armoredVehicle.database.converter.ClassificationResponseConverter;
import kr.gaion.armoredVehicle.database.converter.ClusterResponseConverter;
import kr.gaion.armoredVehicle.database.converter.RegressionResponseConverter;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@Table(name = "algorithm_response")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Algorithm_Type")
public class AlgorithmResponseDB {
    @Id
    @Column(name = "algorithm_response_id")
    private String algorithmResponseId;

    @Column(name = "algorithm_type")
    private String type;

    @Column(name = "response_status")
    private String status;

    @Column(name = "message")
    private String message;

    @Column(name = "id_col")
    private String idCol;

    @Type(type="json")
    @Column( name = "list_features",columnDefinition = "json")
    private String[] listFeatures;

    @Column(name = "class_col")
    private String classCol;

    @Convert(converter = ClassificationResponseConverter.class)
    @Column(name = "classification_response", columnDefinition = "json")
    private ClassificationResponse classificationResponse;

    @Convert(converter = ClusterResponseConverter.class)
    @Column(name = "cluster_response", columnDefinition = "json")
    private ClusterResponse clusterResponse;

    @Convert(converter = RegressionResponseConverter.class)
    @Column(name = "regression_response", columnDefinition = "json")
    private RegressionResponse regressionResponse;
}
