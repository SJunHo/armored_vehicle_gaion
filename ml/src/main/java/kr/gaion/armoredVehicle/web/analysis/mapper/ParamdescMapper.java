package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;

import kr.gaion.armoredVehicle.web.analysis.model.Paramdesc;
import kr.gaion.armoredVehicle.web.analysis.model.ParamdescRequest;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ParamdescMapper {

	List<Paramdesc> findParamdesc(ParamdescRequest param);
}
