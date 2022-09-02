package kr.gaion.armoredVehicle.database.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.gaion.armoredVehicle.database.model.ClusterResponse;

import javax.persistence.AttributeConverter;


public class ClusterResponseConverter implements AttributeConverter<ClusterResponse, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ClusterResponse information) {
        //Information 객체 -> Json 문자열로 변환
        try {
            return objectMapper.writeValueAsString(information);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ClusterResponse convertToEntityAttribute(String jsonString) {
        //Json 문자열 Information 객체로 변환
        try {
            return objectMapper.readValue(jsonString, ClusterResponse.class);
        } catch (Exception e) {
            return null;
        }
    }
}