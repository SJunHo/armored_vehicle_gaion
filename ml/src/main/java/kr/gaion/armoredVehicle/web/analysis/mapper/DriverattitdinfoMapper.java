package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.DriverAttitdInfo;
import kr.gaion.armoredVehicle.web.analysis.model.DriverCorrectInfo;
import kr.gaion.armoredVehicle.web.analysis.model.SearchRequest;

@Mapper
public interface DriverattitdinfoMapper {
	public void insertDriverattitdinfo(DriverAttitdInfo info);
	
	public List<DriverCorrectInfo> findDriverInfoBySearch(SearchRequest search);
	
	public int countDriverInfoBySearch(SearchRequest search);
}
