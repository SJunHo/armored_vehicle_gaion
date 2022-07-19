package kr.co.gaion.scas.monitoring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.monitoring.model.Threshold;
import kr.co.gaion.scas.monitoring.model.ThresholdResponse;

@Mapper
public interface ThresholdMapper {

	public void updateThreshold(ThresholdResponse threshold);
	public List<ThresholdResponse> findThresholdList();
}
