package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Sda;

@Mapper
public interface SdaMapper {

	public List<Sda> findSda(Map<String, Object> search);
	public String findSdaIdBySdanm(String sdanm);

	public Integer countSda(Map<String, Object> search);

	public void insertSda(Sda sda);
	public void updateSda(Sda sda);
	public Sda findByDivsCodeGroup(String divscode);
	public Sda findByBrgdbnCodeGroup(String brgdbncode);
}
