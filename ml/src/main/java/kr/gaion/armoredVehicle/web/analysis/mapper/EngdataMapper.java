package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.Engdata;
import kr.gaion.armoredVehicle.web.analysis.model.Englife;
import kr.gaion.armoredVehicle.web.analysis.model.ExcelDownByMonitorDiagnos;
import kr.gaion.armoredVehicle.web.analysis.model.troubleDataRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EngdataMapper {

	// 추가센서데이터 엔진 부분 INSERT => 사용 X
	public void insertEngdata(Engdata engdata);
	// 위와 마찬가지의 기능이지만 속도 개선을 위해 리스트 처리
	public void insertEngdataList(Map<String, Object> inputMap);
	// ENGSIM 시뮬레이션 데이터 INSER => 화면상 시뮬레이션 기능을 삽입하는 곳이 없음
	public void insertEngsim(Engdata engdata);
	// 추가 센서데이터 업로드 시 해당 ENGDATA의 이상치 탐지 데이터 INSERT
	public void insertEngstatList(Map<String, Object> inputMap);
	// SDAID와 FILENM을 활용하여 ENGDATA 가져오기 => 사용X
	public List<Engdata> findEngdataBySdaidAndFilenm(Map<String,String> data);
	// 상태데이터 조회, 고장진단 모니터링에서 사용할 데이터를 가져와 페이징하기 위해
	public int countEngdataByTable(troubleDataRequest data);
	// 상태데이터 조회, 고장진단 모니터렝에서 사용할 데이터를 가져오기 위해
	public List<Engdata> findEngdata(troubleDataRequest data);
	// 상태데이터 조회, 고장진단 모니터링의 엑셀 다운로드 기능을 위해
	public List<Engdata> findEngdataForExcel(ExcelDownByMonitorDiagnos info);
	
	// 엔진 시뮬레이션 정보를 가져와 페이징 하기 위해
	public int countSimulationByTable();
	// 엔진 시뮬레이션 정보를 가져오기 위해
	public List<Engdata> findSimulation(troubleDataRequest data);
	
	// 잔존수명예지 ENGLIFEDATA를 가져와 페이징 하기 위해
	public int countEnglife(troubleDataRequest data);
	// 잔존수명예지 ENGLIFEDATA를 가져오기 위해
	public List<Englife> findEnglife(troubleDataRequest data);
	
	public int findPart(Map<String, Object> data);
}
