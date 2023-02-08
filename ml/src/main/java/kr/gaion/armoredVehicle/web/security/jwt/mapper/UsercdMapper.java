package kr.gaion.armoredVehicle.web.security.jwt.mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Userlog;
import kr.gaion.armoredVehicle.web.security.jwt.model.Usercd;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface UsercdMapper {

	public void insertUsercd(Usercd usercd);
	
	public Usercd findByName(String name);
	
	public Usercd findByUserid(String userid);

	public List<Usercd> findUserList(Map<String,Integer> pageSize);
	public int countUsers();
	public void updateUser(Usercd user);
	public void deleteUser(String id);

	public void insertUserLog(Map<String, Object> param);

	public List<Userlog> findUserLogList(Map<String, Integer> pageSize);

	public int countUserLogs();
}
