package kr.gaion.armoredVehicle.database.model;

import kr.gaion.armoredVehicle.database.converter.ClassificationResponseConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "Algorithm_Type")
    private String type;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CHECKED")
    private Boolean checked;

    // Classification
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
    // Regression
    @Type(type="json")
    @Column( name = "coefficients",columnDefinition = "json")
    private double[] coefficients;

    @Column(name = "rootMeanSquaredError")
    private double rootMeanSquaredError;

    @Column(name = "r2")
    private double r2;

    @Type(type="json")
    @Column(name = "listFeatures")
    private String[] listFeatures;

//    @Type(type = "json")
//    @Column( name = "residuals",columnDefinition = "json")
//    private List<?> residuals;

}
