package kr.co.gaion.scas.monitoring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.monitoring.model.Cmncd;


@Mapper
public interface CmncdMapper {

	public int countCmncdAll();
	public List<Cmncd> findListByCode(String code);
	public List<Cmncd> findCmncdList(int page, int pageSize);
	public void insertCmncd(Cmncd cmncd);
	public Cmncd findCmncd(int id);
	public void updateCmncd(Cmncd cmncd);
	public void deleteCmncd(int id);
}
