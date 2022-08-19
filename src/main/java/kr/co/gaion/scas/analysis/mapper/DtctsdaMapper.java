package kr.co.gaion.scas.analysis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.Dtctsda;

@Mapper
public interface DtctsdaMapper {

	public int countAll(String dttime);
	public int countDtctsdaBySdaid(String sdaid, String dttime);
	public void insertDtctsda(Dtctsda dtctsda);
	

	public List<Dtctsda> getDtctsda(HashMap<String, String> params);
}
