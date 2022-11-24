package kr.gaion.armoredVehicle.dataset.dto;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
public class DbDataUpdateInput {
    private Long id;

    private String partType;

    private String aiPredict;

    private String aiAlgorithmName;

    private String aiModelName;

    private Date aiPredictDate;
}
