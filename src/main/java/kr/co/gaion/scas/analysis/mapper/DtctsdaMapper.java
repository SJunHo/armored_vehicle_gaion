package kr.co.gaion.scas.analysis.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.Dtctsda;

@Mapper
public interface DtctsdaMapper {

	public int countAll(String dttime);
	public int countDtctsdaBySdaid(String sdaid, String dttime);
	public void insertDtctsda(Dtctsda dtctsda);
}
