package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;

import kr.gaion.armoredVehicle.web.analysis.model.Prinfo;
import kr.gaion.armoredVehicle.web.analysis.model.PrinfoRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrinfoMapper {
	
	public List<Prinfo> findPhinfo();
	
	public List<Prinfo> findSearchData(PrinfoRequest prinfo);
	
	
}
