package kr.gaion.armoredVehicle.database.model;

import kr.gaion.armoredVehicle.database.converter.ClassificationResponseConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "DB_MODEL_RESPONSE")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "Algorithm_Type")
public class DbModelResponse {
    @Id
    @GeneratedValue
    @Column(name = "algorithm_response_id")
    private Long algorithmResponseId;

    @Column(name = "MODEL_NAME")
    private String modelName;

    @Column(name = "algorithm_type")
    private String type;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CHECKED")
    private Boolean checked;

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

}
