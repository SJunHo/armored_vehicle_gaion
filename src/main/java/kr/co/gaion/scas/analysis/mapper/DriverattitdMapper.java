package kr.co.gaion.scas.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.DriverAttitd;

@Mapper
public interface DriverattitdMapper {

	public List<DriverAttitd> findDriverattitdAll();
	
	public List<DriverAttitd> findDriverattitdByDaid(String daid);
	
	public void updateDriverAttitd(DriverAttitd data);
}
