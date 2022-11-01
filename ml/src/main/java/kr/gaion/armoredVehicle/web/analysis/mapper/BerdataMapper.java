package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.Berdata;
import kr.gaion.armoredVehicle.web.analysis.model.ExcelDownByMonitorDiagnos;
import org.apache.ibatis.annotations.Mapper;
import kr.gaion.armoredVehicle.web.analysis.model.troubleDataRequest;

@Mapper
public interface BerdataMapper {

	public void insertBerdata(Berdata berdata);
	public void insertBersim(Berdata berdata);
	public List<Berdata> findBerdataBySdaidAndFilenm(Map<String,String> data);
	
	public int countBerdataByTable(troubleDataRequest data);
	public List<Berdata> findBerdata(troubleDataRequest data);

	public List<Berdata> findBerdataForExcel(ExcelDownByMonitorDiagnos info);
	
}
