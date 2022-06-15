package kr.co.gaion.scas.security.jwt.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.security.jwt.model.ERole;
import kr.co.gaion.scas.security.jwt.model.Role;

@Mapper
public interface RoleMapper {
	public Role findByName(ERole roleUser);  
	public Role findById(Integer id);
}
