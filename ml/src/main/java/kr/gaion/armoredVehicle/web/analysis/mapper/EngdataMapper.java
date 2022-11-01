package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.Engdata;
import kr.gaion.armoredVehicle.web.analysis.model.ExcelDownByMonitorDiagnos;
import kr.gaion.armoredVehicle.web.analysis.model.troubleDataRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EngdataMapper {

	public void insertEngdata(Engdata engdata);
	public void insertEngsim(Engdata engdata);
	public List<Engdata> findEngdataBySdaidAndFilenm(Map<String,String> data);
	
	public int countEngdataByTable(troubleDataRequest data);
	public List<Engdata> findEngdata(troubleDataRequest data);
	
	public List<Engdata> findEngdataForExcel(ExcelDownByMonitorDiagnos info);
}
