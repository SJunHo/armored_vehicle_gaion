package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Cmncd;


@Mapper
public interface CmncdMapper {

	public int countCmncdAll();
	public List<Cmncd> findListByCode(String code);
	public List<Cmncd> findCmncdList(Map<String,Integer> param);
	public void insertCmncd(Cmncd cmncd);
	public Cmncd findCmncd(int id);
	public void updateCmncd(Cmncd cmncd);
	public void deleteCmncd(int id);
	
	public List<Cmncd> getForBtn1();	//수치형센서데이터 목록
	public List<Cmncd> getForBtn2();	//범주형센서데이터 목록
}
