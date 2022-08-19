package kr.co.gaion.scas.analysis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.analysis.model.UserSnsr;

@Mapper
public interface UsersnsrMapper {
	public List<UserSnsr> getBookmark(Map<String, String> param);
	
	public void insertBookmark(Map<String, String> param);
	
	public void deleteBookmark(Map<String, String> param);
}
