package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Cmpntsrplc;
import kr.gaion.armoredVehicle.web.analysis.model.CmpntsrplcResponse;

@Mapper
public interface CmpntsrplcMapper {

	public List<CmpntsrplcResponse> findCmpntsrplcAll();
	public void updateCmpntsrplc(Cmpntsrplc cmpntsrplc);
}
