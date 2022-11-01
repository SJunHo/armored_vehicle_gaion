package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.ExcelDownByMonitorDiagnos;
import kr.gaion.armoredVehicle.web.analysis.model.Grbdata;
import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.troubleDataRequest;

@Mapper
public interface GrbdataMapper {

	public void insertGrbdata(Grbdata grbdata);
	public void insertGrbsim(Grbdata grbdata);
	public List<Grbdata> findGrbdataBySdaidAndFilenm(Map<String,String> data);
	
	public int countGrbdataByTable(troubleDataRequest data);
	public List<Grbdata> findGrbdata(troubleDataRequest data);
	
	public List<Grbdata> findGrbdataForExcel(ExcelDownByMonitorDiagnos info);
	

}
