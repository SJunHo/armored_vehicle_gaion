package kr.gaion.armoredVehicle.dataset.dto;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
public class DbJudgementUpdateInput {
    private Long idx;
    private Integer userJudgement;
}
