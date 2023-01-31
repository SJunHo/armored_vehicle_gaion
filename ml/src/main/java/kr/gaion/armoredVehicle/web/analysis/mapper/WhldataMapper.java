package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.ExcelDownByMonitorDiagnos;
import kr.gaion.armoredVehicle.web.analysis.model.Whldata;
import kr.gaion.armoredVehicle.web.analysis.model.Whllife;
import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.troubleDataRequest;

@Mapper
public interface WhldataMapper {

	public void insertWhldata(Whldata whldata);
	public void insertWhlsim(Whldata whldata);
	public List<Whldata> findWhldataBySdaidAndFilenm(Map<String,String> data);
	
	public int countWhldataByTable(troubleDataRequest data);
	public List<Whldata> findWhldata(troubleDataRequest data);
	
	public List<Whldata> findWhldataForExcel(ExcelDownByMonitorDiagnos info);

	public int countWhllife();
	public List<Whllife> findWhllife(troubleDataRequest data);
}
