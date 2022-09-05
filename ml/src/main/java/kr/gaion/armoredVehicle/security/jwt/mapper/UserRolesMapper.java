package kr.gaion.armoredVehicle.security.jwt.mapper;

import kr.gaion.armoredVehicle.security.jwt.model.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRolesMapper {

	public void insertUserRoles(UserRole userRole);
	public int findByUserId(String userId);
}
