package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.DriverAttitd;

@Mapper
public interface DriverattitdMapper {

	public List<DriverAttitd> findDriverattitdAll();
	
	public List<DriverAttitd> findDriverattitdByDaid(String daid);
	
	public void updateDriverAttitd(DriverAttitd data);
}
