package kr.co.gaion.scas.analysis.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BkdsdaMapper {

	public int countAll(String dttime);
	public int countBkdsdaBySdaid(String sdaid, String dttime);
}
