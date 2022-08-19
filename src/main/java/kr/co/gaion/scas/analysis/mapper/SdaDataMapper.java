package kr.co.gaion.scas.analysis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.SdaData;


@Mapper
public interface SdaDataMapper {
	public List<SdaData> getFileWithId(Map<String, Object> selectFile);
	public List<SdaData> getOneChartData(Map<String, String> params);
}
