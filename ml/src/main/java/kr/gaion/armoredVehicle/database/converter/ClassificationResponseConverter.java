package kr.gaion.armoredVehicle.database.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.gaion.armoredVehicle.database.model.ClassificationResponse;
import javax.persistence.AttributeConverter;

public class ClassificationResponseConverter implements AttributeConverter<ClassificationResponse, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ClassificationResponse information) {
        //Information 객체 -> Json 문자열로 변환
        try {
            return objectMapper.writeValueAsString(information);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ClassificationResponse convertToEntityAttribute(String jsonString) {
        //Json 문자열 Information 객체로 변환
        try {
            return objectMapper.readValue(jsonString, ClassificationResponse.class);
        } catch (Exception e) {
            return null;
        }
    }
}