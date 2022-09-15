package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.analysis.model.TreeInfo;

@Mapper
public interface TreeInfoMapper {

	public List<TreeInfo> findTree();
	public TreeInfo findFirstHeader(int trinfoid);
	public List<TreeInfo> findHeader(int trinfohead);
	public TreeInfo findByCode(String code);
	public Integer findTreeInfoIdByCode(String treeinfocode);
}
