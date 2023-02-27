package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.Map;

import kr.gaion.armoredVehicle.database.model.FileInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileInfoMapper {

	// 데이터 업로드 시 해당 파일 정보를 INSERT하기 위해
	public void insertFileInfo(FileInfo fileInfo);
	public void insertFileInfoWithSdaid(Map<String, String> param);
	public void insertFileInfoWithSdaidAll(Map<String, Object> param);
	public void deleteFileInfoWidSdaid(String sdaid);
	
	// 최근 파일정보를 가져와 SEQUENCE 세팅을 위해
	public FileInfo findTodayRecentFileInfo(Map<String, String> param);
}
