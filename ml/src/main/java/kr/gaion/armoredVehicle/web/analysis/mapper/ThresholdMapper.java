package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Threshold;
import kr.gaion.armoredVehicle.web.analysis.model.ThresholdResponse;

@Mapper
public interface ThresholdMapper {

	public void updateThreshold(ThresholdResponse threshold);
	public List<ThresholdResponse> findThresholdList();
}
