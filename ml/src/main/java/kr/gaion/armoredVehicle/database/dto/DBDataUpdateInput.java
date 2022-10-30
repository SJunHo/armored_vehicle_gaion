package kr.gaion.armoredVehicle.database.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class DBDataUpdateInput {
    private Long idx;
    private Integer defectUser;
}
