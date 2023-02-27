package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.ExcelDownByMonitorDiagnos;
import kr.gaion.armoredVehicle.web.analysis.model.Grbdata;
import kr.gaion.armoredVehicle.web.analysis.model.Grblife;
import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.troubleDataRequest;

@Mapper
public interface GrbdataMapper {

	// 추가 센서데이터 업로드 시 GRBDATA를 INSERT => 사용X
	public void insertGrbdata(Grbdata grbdata);
	// 추가 센서데이터 업로드 시 GRBDATA를 INSERT 하며 속도개선을 위해 LIST처리
	public void insertGrbdataList(Map<String, Object> inputMap);
	// 기어박스 시뮬레이션 데이터 INSERT => 사용X 화면상 시뮬레이션 데이터를 넣는 곳 없음
	public void insertGrbsim(Grbdata grbdata);
	// 추가 센서데이터 업로드 시 해당 GRB데이터에 이상치 탐지 데이터를 저장
	public void insertGrbstatList(Map<String, Object> inputMap);
	
	// 사용안함
	public List<Grbdata> findGrbdataBySdaidAndFilenm(Map<String,String> data);
	// 상태데이터조회, 고장진단 모니터링에서 기어박스 데이터를 보여주기 위해
	public int countGrbdataByTable(troubleDataRequest data);
	public List<Grbdata> findGrbdata(troubleDataRequest data);
	// 상태데이터조회, 고장진단 모니터링에서 기어박스 데이터를 엑셀 다운로드 하기 위해
	public List<Grbdata> findGrbdataForExcel(ExcelDownByMonitorDiagnos info);
	// 고장진단 모니터링에서 기어박스 시뮬레이션 데이터를 보여주기 위해
	public int countSimulationByTable();
	public List<Grbdata> findSimulation(troubleDataRequest data);
	
	// 잔존수명예지 조회에서 GRBLIFEDATA를 보여주기 위해
	public int countGrblife(troubleDataRequest data);
	public List<Grblife> findGrblife(troubleDataRequest data);
	
	public int findPart(Map<String, Object> data);
}
