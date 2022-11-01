package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Threshold;

@Mapper
public interface ThresholdMapper {

	public void updateThreshold(Threshold threshold);
	public List<Threshold> findThresholdList();
}
