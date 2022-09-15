package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Sda;

@Mapper
public interface SdaMapper {

	public List<Sda> findSdaByBrgdbnCode(String brgdbncode);
	public List<Sda> findSdaByDivCode(String divcode);
	public List<Sda> findSdaAll();
	public List<Sda> findSdalAllPaging(int page, int pageSize);
	public Integer countSdaAll();
	public Integer countSdaByDivCode(String divcode);
	public Integer countSdaByBrgdbnCode(String brgdbncode);
	
	public Sda findSdaBySdaid(String sdaid);
	public void insertSda(Sda sda);
	public void updateSda(Sda sda);
	

	public Sda getEachInfo(String id);
	public List<Sda> getAllVehicleInfo();
	
	public Sda findByDivsCodeGroup(String divscode);
	public Sda findByBrgdbnCodeGroup(String brgdbncode);
}
