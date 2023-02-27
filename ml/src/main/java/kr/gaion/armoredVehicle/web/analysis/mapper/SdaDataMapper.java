package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.ExcelDownInfo;
import kr.gaion.armoredVehicle.web.analysis.model.FilenmDttimeForDefaultBookmark;
import kr.gaion.armoredVehicle.web.analysis.model.PeriodicDataRequest;
import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.SdaData;


@Mapper
public interface SdaDataMapper {
	
	//차량별 주기성 데이터 업로드 
	public void insertSdaData(SdaData sdadata);
	// 위와 같은 기능을 하난 속도 개선을 위해 리스트 처리
	public void insertSdaDataList(Map<String, Object> param);
	
	// 차량 등록시 해당 테이블 생성
	public void createTable(Map<String, String> create_table);
	
	// 파일업로드 시 운전자 자세교정정보를 등록할 때 업로드 된 해당 파일정보를 가져오기 위해
	public List<SdaData> findSdaDataByFileNM(String tablenm,String filenm);
	
	// 통계정보에서 평균 속도정보를 가져오기 위해
	public Double findSpdAvgByFileNM(String tablenm, String filenm);
	// 통계정보에서 평균 운행시간 정보를 가져오기 위해
	public Double findMvMttimeByFileNM(String tablenm, String filenm);
	// 통계정보에서 엔진공회전비율을 가져오기 위해
	public Double findEngnnldnRateByFileNM(String tablenm, String filenm);
	
	// 파일선택 모달에서 에서 해당 파일이름과 운행일자를 가져오기 위해
	public List<Map<String, Object>> getFileWithId(Map<String, Object> selectFile);
	public Integer getCountOfFileInfo(String sdaid);
	public Integer getCountOfNotMappedFileInfo(String sdaid);
	public List<Map<String, Object>> getFileInfoWithSdaid(String sdaid);
	
	// 파일정보 조회에서 해당 파일에대한 차트 데이터를 가져오기 위해
	public List<SdaData> getOneChartData(Map<String, String> params);
	
	// 주기성데이터 조회에서 선택된 파일의 저옵를 가져오기 위해
	public List<SdaData> findSdaDataByTableAndFileNM(PeriodicDataRequest periodic);
	public int countSdaDataByTable(PeriodicDataRequest periodic);
	
	// 주기성 데이터조회에서 조회된 데이터를 엑셀 다운로드 하기 위해
	public List<SdaData> findSdaDataForExcel(ExcelDownInfo info);
	
	// 운전자 자세교정 정보에서 해당 엔진예열 시간을 파악하기 위해 (조건 중 하나)
	public int findTimeStampDiffByEngheat(String tablenm, String filenm);
	// 운전자 자세교정 정보에서 엔진 예열이 1인 데이터를 확인하기 위해 
	public List<SdaData> findCoolTempAndDttime(Map<String, String> tablenm);
	
	// 최신파일을 가져오는 과정에서 파일이름과 dttime가져오기 
	public List<FilenmDttimeForDefaultBookmark> findRecentFile(Map<String, String> tablenm);
	// 모의센서데이터 업로드 시 시뮬레이션 데이터 삭제
	public void deleteSimulationData();
}
