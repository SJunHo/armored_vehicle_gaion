package kr.gaion.armoredVehicle.web.security.jwt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.security.jwt.model.User;


@Mapper
public interface UserMapper {

	public User findByUsername(String username);
	public User findById(String id);
	public User findByIdAll(String id);
	public void insertUser(User user);
	public List<User> findUserList(int page, int pageSize);
	public int countUsers();
	public void updateUser(User user);
	public void deleteUser(String id);
}
