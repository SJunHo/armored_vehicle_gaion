package kr.gaion.armoredVehicle.web.analysis.mapper;

import kr.gaion.armoredVehicle.web.analysis.model.CmpntsrplcData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CmpntsplcDataMapper {
	public void insertData(CmpntsrplcData cmpntsrplcData);

}
