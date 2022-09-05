package kr.gaion.armoredVehicle.security.jwt.mapper;

import kr.gaion.armoredVehicle.security.jwt.model.ERole;
import kr.gaion.armoredVehicle.security.jwt.model.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
	public Role findByName(ERole roleUser);
	public Role findById(Integer id);
}
