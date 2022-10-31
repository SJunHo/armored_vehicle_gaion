package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.CmpntsrplcInfo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CmpntsrplcinfoMapper {
	
	public List<CmpntsrplcInfo> find(CmpntsrplcInfo param);

	public CmpntsrplcInfo findCmpntsrplcinfoBySdaidAndGrid(Map<String, String> data);
	
	public void insertCmpntsrplcinfo(CmpntsrplcInfo info);
	
	public void updateCmpntsrplcinfo(CmpntsrplcInfo info);
}
