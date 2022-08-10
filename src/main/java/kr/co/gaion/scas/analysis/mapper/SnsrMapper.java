package kr.co.gaion.scas.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.Snsr;

@Mapper
public interface SnsrMapper {

	public List<Snsr> findSnsrList();
}
