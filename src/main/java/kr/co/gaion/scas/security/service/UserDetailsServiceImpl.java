package kr.co.gaion.scas.security.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.gaion.scas.monitoring.model.Cmncd;
import kr.co.gaion.scas.security.jwt.mapper.RoleMapper;
import kr.co.gaion.scas.security.jwt.mapper.UserMapper;
import kr.co.gaion.scas.security.jwt.mapper.UserRolesMapper;
import kr.co.gaion.scas.security.jwt.model.ERole;
import kr.co.gaion.scas.security.jwt.model.Role;
import kr.co.gaion.scas.security.jwt.model.User;
import kr.co.gaion.scas.utils.Paging;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserMapper userRepository;

  @Autowired
  RoleMapper roleRepository;
  
  @Autowired
  UserRolesMapper userRolesRepository;
  
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
    User user = userRepository.findById(userid);
    //user에 대한 role 가져오기
//    int roleId = userRolesRepository.findByUserId(user.getId());
	    Role role = new Role();
	    if(user.getUsrth() == 'N') {
	    	role.setName(ERole.ROLE_USER);
	    }else if(user.getUsrth() == 'A') {
	    	role.setName(ERole.ROLE_ADMIN);
	    }else if(user.getUsrth() == 'M') {
	    	role.setName(ERole.ROLE_MODERATOR);
	    }
      Set<Role> roles = new HashSet<>();
      roles.add(role);
      user.setRoles(roles);
    
    return UserDetailsImpl.build(user);
  }

 
}
