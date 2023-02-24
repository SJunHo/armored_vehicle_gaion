package kr.gaion.armoredVehicle.spark.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ReTrainingInput {
    private List<Map<String, String>> newData;
    private String modelName;
    private String partType;
    private String algorithmName;
}
