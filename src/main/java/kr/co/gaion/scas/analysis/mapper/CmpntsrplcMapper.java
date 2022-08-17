package kr.co.gaion.scas.analysis.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.CmpntsrplcResponse;
import kr.co.gaion.scas.analysis.model.Cmpntsrplc;

@Mapper
public interface CmpntsrplcMapper {

	public List<CmpntsrplcResponse> findCmpntsrplcAll();
	public void updateCmpntsrplc(Cmpntsrplc cmpntsrplc);
}
