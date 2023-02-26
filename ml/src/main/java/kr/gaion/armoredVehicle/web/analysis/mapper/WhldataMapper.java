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
	public void insertWhldataList(Map<String, Object> inputMap);
	public void insertWhlsim(Whldata whldata);
	public void insertWhlstatList(Map<String, Object> inputMap);
	public List<Whldata> findWhldataBySdaidAndFilenm(Map<String,String> data);
	
	public int countWhldataByTable(troubleDataRequest data);
	public List<Whldata> findWhldata(troubleDataRequest data);
	
	public List<Whldata> findWhldataForExcel(ExcelDownByMonitorDiagnos info);
	public int countSimulationByTable();
	public List<Whldata> findSimulation(troubleDataRequest data);
	
	public int countWhllife(troubleDataRequest data);
	public List<Whllife> findWhllife(troubleDataRequest data);
	
	public int findPart(Map<String, Object> data);
}
