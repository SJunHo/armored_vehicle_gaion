package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.Berdata;
import kr.gaion.armoredVehicle.web.analysis.model.Berlife;
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
	// 시뮬레이션 시 페이징을 위해 사용
	public int countSimulationByTable();
	// 시뮬레이션 데이터를 보여주기 위해 사용
	public List<Berdata> findSimulation(troubleDataRequest data);
	
	// 잔존 수명예지 부분의 BERLIFEDATA를 페이징 하기 위해 사용
	public int countBerlife(troubleDataRequest data);
	// 잔존 수명예지 부분의 BERLIFEDATA를 보여주기 위해 사용
	public List<Berlife> findBerlife(troubleDataRequest data);
	
	public int findPart(Map<String, Object> data);
}
