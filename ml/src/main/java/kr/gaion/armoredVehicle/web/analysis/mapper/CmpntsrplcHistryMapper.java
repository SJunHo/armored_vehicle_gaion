package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.HashMap;
import java.util.List;

import kr.gaion.armoredVehicle.web.analysis.model.CmpntsrplcHistry;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CmpntsrplcHistryMapper {
	public List<CmpntsrplcHistry> find(CmpntsrplcHistry param);
	public CmpntsrplcHistry findCmpntsrplchistryBySdaidAndGrid(HashMap<String,String> map);
}
