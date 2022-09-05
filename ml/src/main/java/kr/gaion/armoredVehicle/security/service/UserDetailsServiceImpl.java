package kr.gaion.armoredVehicle.security.service;

import java.util.HashSet;
import java.util.Set;

import kr.gaion.armoredVehicle.security.jwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.gaion.armoredVehicle.security.jwt.mapper.UserMapper;
import kr.gaion.armoredVehicle.security.jwt.model.ERole;
import kr.gaion.armoredVehicle.security.jwt.model.Role;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
    User user = userMapper.findById(userid);
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
