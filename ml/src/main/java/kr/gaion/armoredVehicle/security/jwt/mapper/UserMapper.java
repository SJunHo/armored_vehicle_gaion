package kr.gaion.armoredVehicle.security.jwt.mapper;

import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.security.jwt.model.User;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface UserMapper {

	public User findByUsername(String username);
	public User findById(String id);
	public User findByIdAll(String id);
	public void insertUser(User user);
	public List<User> findUserList(Map<String,Integer> param);
	public int countUsers();
	public void updateUser(User user);
	public void deleteUser(String id);
}
