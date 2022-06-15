package kr.co.gaion.scas.security.jwt.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.security.jwt.model.Role;
import kr.co.gaion.scas.security.jwt.model.UserRole;

@Mapper
public interface UserRolesMapper {

	public void insertUserRoles(UserRole userRole);
	public int findByUserId(String userId);
}
