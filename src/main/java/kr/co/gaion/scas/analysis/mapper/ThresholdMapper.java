package kr.co.gaion.scas.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.Threshold;
import kr.co.gaion.scas.analysis.model.ThresholdResponse;

@Mapper
public interface ThresholdMapper {

	public void updateThreshold(ThresholdResponse threshold);
	public List<ThresholdResponse> findThresholdList();
}
