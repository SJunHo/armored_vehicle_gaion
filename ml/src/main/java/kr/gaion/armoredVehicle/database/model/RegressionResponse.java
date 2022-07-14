package kr.gaion.armoredVehicle.database.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@DiscriminatorValue("regressionResponse")
public class RegressionResponse {
    @Id
    @Column(name = "regression_id")
    private long regressionResponseId;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "description")
    private String description;

    @Column(name = "checked")
    private boolean checked;

    @Type(type="json")
    @Column( name = "label",columnDefinition = "json")
    private String[] label;

    @Type(type="json")
    @Column( name = "coefficients",columnDefinition = "json")
    private double[] coefficients;

    @Column(name = "rootMeanSquaredError")
    private double rootMeanSquaredError;

    @Column(name = "r2")
    private double r2;

    @Type(type = "json")
    @Column( name = "residuals",columnDefinition = "json")
    private List<String> residuals;

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
