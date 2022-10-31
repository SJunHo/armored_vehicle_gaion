package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.FilenmDttimeForDefaultBookmark;
import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.SdaData;


@Mapper
public interface SdaDataMapper {
	public List<SdaData> findSdaDataByFileNM(String tablenm,String filenm);

	public Double findSpdAvgByFileNM(String tablenm, String filenm);

	public Double findMvMttimeByFileNM(String tablenm, String filenm);

	public Double findEngnnldnRateByFileNM(String tablenm, String filenm);

	public List<SdaData> getFileWithId(Map<String, Object> selectFile);
	public List<SdaData> getOneChartData(Map<String, String> params);

	public int findTimeStampDiffByEngheat(String tablenm, String filenm);
	public List<SdaData> findCoolTempAndDttime(Map<String, String> tablenm);

	public List<FilenmDttimeForDefaultBookmark> findRecentFile(Map<String, String> tablenm);
}
