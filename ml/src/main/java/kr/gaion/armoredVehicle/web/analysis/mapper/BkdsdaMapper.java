package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.BkdResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BkdsdaMapper {

	public int countBkd(Map<String,Object> map);

	public List<BkdResponse> findBkdMsg(Map<String, Object> map);
}
