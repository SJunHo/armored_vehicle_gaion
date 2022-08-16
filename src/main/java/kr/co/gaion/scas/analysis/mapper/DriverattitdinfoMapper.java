package kr.co.gaion.scas.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.gaion.scas.analysis.model.DriverAttitdInfo;
import kr.co.gaion.scas.analysis.model.DriverCorrectInfo;
import kr.co.gaion.scas.analysis.model.SearchRequest;

@Mapper
public interface DriverattitdinfoMapper {
	public void insertDriverattitdinfo(DriverAttitdInfo info);
	
	public List<DriverCorrectInfo> findDriverInfoBySearch(SearchRequest search);
	
	public int countDriverInfoBySearch(SearchRequest search);
}
