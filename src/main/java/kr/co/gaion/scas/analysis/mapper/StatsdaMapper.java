package kr.co.gaion.scas.analysis.mapper;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.Statsda;
import kr.co.gaion.scas.analysis.model.StatsdaGroup;

@Mapper
public interface StatsdaMapper {

	public List<Statsda> findStatsdaByListSdaid(Map<String,Object> map);
	public int countStatssdaAll(String operdate);
	public int countStatssda(String sdaid, String operdate);
	public List<StatsdaGroup> findStatsdaByGroupDivs(String operdate);
	public List<StatsdaGroup> findStatsdaByGroupBN(String operdate, String divscode);
	public void insertStatsda(Statsda statsda);
}
