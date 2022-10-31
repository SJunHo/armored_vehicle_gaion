package kr.gaion.armoredVehicle.web.security.service;

import kr.gaion.armoredVehicle.web.security.jwt.mapper.UsercdMapper;
import kr.gaion.armoredVehicle.web.security.jwt.model.Usercd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.gaion.armoredVehicle.web.security.jwt.mapper.RoleMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    RoleMapper roleRepository;
    @Autowired
    UsercdMapper usercdRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Usercd usercd = usercdRepository.findByUserid(userid);
//    //user에 대한 role 가져오기
//    int roleId = userRolesRepository.findByUserId(user.getId());
//    Role role = roleRepository.findById(roleId);
//    Role role = userRepository.findRoleById(userid);
//    Set<Role> roles = new HashSet<>();
//    roles.add(role);
//    user.setRoles(roles);

        return UserDetailsImpl.build(usercd);
    }

}
