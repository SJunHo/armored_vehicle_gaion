package kr.gaion.armoredVehicle.web.analysis.mapper;

import kr.gaion.armoredVehicle.web.analysis.model.LifeThreshold;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LifeThresholdMapper {

	public void updateThreshold(LifeThreshold threshold);
	public List<LifeThreshold> get();
}
