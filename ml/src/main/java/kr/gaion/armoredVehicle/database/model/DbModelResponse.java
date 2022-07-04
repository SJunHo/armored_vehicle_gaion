package kr.gaion.armoredVehicle.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DB_MODEL_RESPONSE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbModelResponse {
    @Id
    @Column(name = "MODEL_NAME")
    private String modelName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CHECKED")
    private Boolean checked;

    @Column(name = "CONFUSION_MATRIX")
    private String confusionMatrix;

    @Column(name = "LABELS")
    private String labels;

    @Column(name = "WEIGHTHED_FALSE_POSITIVE_RATE")
    private double weightedFalsePositiveRate;

    @Column(name = "WEIGHTHED_F_MEASURE")
    private double weightedFMeasure;

    @Column(name = "ACCURACY")
    private double accuracy;

    @Column(name = "WEIGHTED_PRECISION")
    private double weightedPrecision;

    @Column(name = "WEIGHTED_RECALL")
    private double weightedRecall;

    @Column(name = "WEIGHTED_TRUE_POSITIVE_RATE")
    private double weightedTruePositiveRate;

    @Column(name = "PREDICTION_INFO")
    private String predictionInfo;

    @Column(name = "PREDICTED_ACTUAL_FEATURE_LINE")
    private String predictedActualFeatureLine;

    @Column(name = "PREDICTED_FEATURE_LINE")
    private String predictedFeatureLine;
}
