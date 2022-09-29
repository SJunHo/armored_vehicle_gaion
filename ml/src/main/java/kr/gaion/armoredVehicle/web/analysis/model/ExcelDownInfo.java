package kr.gaion.armoredVehicle.web.analysis.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelDownInfo {

	List<String> nummericSensor;
	List<String> categoricSensor;
	List<String> nummericSensorWithKor;
	List<String> categoricSensorWithKor;
	String sdaid;
	String filenm;
	String colName;
	String tableName;
	
	public void makePredicColName() {
		String basic = "sdaid, sdanm, operdate, opertime, dttime, time ";
		String numeric = "";
		String categoric = "";
		
		if(!this.nummericSensor.isEmpty()) {
			for(String s : this.nummericSensor) {
				String addData = "," + s  ;
				numeric = numeric + addData; 
			}
		}
		
		if(!this.categoricSensor.isEmpty()) {
			for(String s : this.categoricSensor) {
				String addData = "," + s  ;
				categoric = categoric + addData; 
			}
		}
		
		this.colName = basic + numeric + categoric;
	}
}
