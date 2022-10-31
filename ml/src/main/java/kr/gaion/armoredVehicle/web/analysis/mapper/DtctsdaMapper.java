package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Dtctsda;

@Mapper
public interface DtctsdaMapper {

	public int countAll(String dttime);
	public int countDtctsdaBySdaid(Map<String, Object> params);
	public void insertDtctsda(Dtctsda dtctsda);
	

	public List<Dtctsda> getDtctsda(HashMap<String, String> params);
}
