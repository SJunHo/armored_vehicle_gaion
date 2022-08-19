package kr.co.gaion.scas.analysis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.SdaDataWithDtctsda;


@Mapper
public interface SdaDataWithDtctsdaMapper {
	public List<SdaDataWithDtctsda> getDataWithDtctsda(HashMap<String, String> params);

}
