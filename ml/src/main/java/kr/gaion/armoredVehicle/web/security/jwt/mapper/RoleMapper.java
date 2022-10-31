package kr.gaion.armoredVehicle.web.security.jwt.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.gaion.armoredVehicle.web.security.jwt.model.ERole;
import kr.gaion.armoredVehicle.web.security.jwt.model.Role;

@Mapper
public interface RoleMapper {
	public Role findByName(ERole roleUser);  
	public Role findById(Integer id);
}
