package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.SdaDataWithDtctsda;


@Mapper
public interface SdaDataWithDtctsdaMapper {
	public List<SdaDataWithDtctsda> getDataWithDtctsda(HashMap<String, String> params);

}
