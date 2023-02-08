package kr.gaion.armoredVehicle.web.analysis.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Userlog {
    private String userid;
    private Date logindt;
}
