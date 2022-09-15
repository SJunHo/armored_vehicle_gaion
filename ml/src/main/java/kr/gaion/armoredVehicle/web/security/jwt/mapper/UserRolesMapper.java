package kr.gaion.armoredVehicle.web.security.jwt.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.security.jwt.model.Role;
import kr.gaion.armoredVehicle.web.security.jwt.model.UserRole;

@Mapper
public interface UserRolesMapper {

	public void insertUserRoles(UserRole userRole);
	public int findByUserId(String userId);
}
