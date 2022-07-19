package kr.co.gaion.scas.monitoring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.monitoring.model.Cmncd;


@Mapper
public interface CmncdMapper {

	public List<Cmncd> findListByCode(String code);
}
