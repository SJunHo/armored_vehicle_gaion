package kr.gaion.armoredVehicle.database.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import kr.gaion.armoredVehicle.algorithm.dto.response.ClusterResponse;
import kr.gaion.armoredVehicle.database.converter.ClassificationResponseConverter;
import kr.gaion.armoredVehicle.database.converter.ClusterResponseConverter;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@DiscriminatorValue("classificationResponse")
public class ClassificationResponse {
    @Id
    @Column(name = "classification_id")
    private long classificationResponseId;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "description")
    private String description;

    @Column(name = "checked")
    private boolean checked;

    @Type(type="json")
    @Column( name = "confusion_matrix",columnDefinition = "json")
    private double[] confusionMatrix;

    @Type(type="json")
    @Column( name = "label",columnDefinition = "json")
    private String[] label;

    @Column(name = "weighted_false_positive_rate")
    private double weightedFalsePositiveRate;

    @Column(name = "weighted_F_measure")
    private double weightedFMeasure;

    @Column(name = "accuracy")
    private double accuracy;

    @Column(name = "weighted_precision")
    private double weightedPrecision;

    @Column(name = "weighted_recall")
    private double weightedRecall;

    @Column(name = "weighted_true_positive_rate")
    private double weightedTruePositiveRate;

    @Type(type = "json")
    @Column( name = "prediction_info",columnDefinition = "json")
    private List<String> predictionInfo;

    @Type(type = "json")
    @Column( name = "predicted_actual_feature_line",columnDefinition = "json")
    private List<String> predictedActualFeatureLine;

    @Type(type = "json")
    @Column( name = "predicted_feature_line",columnDefinition = "json")
    private List<String> predictedFeatureLine;
}
