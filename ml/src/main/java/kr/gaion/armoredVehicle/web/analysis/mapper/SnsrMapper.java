package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Snsr;

@Mapper
public interface SnsrMapper {

	public List<Snsr> findSnsrList();
}
