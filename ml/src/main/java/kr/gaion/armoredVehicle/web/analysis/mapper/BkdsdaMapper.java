package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BkdsdaMapper {

	public int countAll(String dttime);
	public int countBkdsdaBySdaid(Map<String, Object> params);
}
