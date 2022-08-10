package kr.co.gaion.scas.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.gaion.scas.security.service.UserDetailsImpl;
import kr.co.gaion.scas.security.service.UserDetailsServiceImpl;
import kr.co.gaion.scas.analysis.model.Cmncd;
import kr.co.gaion.scas.security.jwt.JwtUtils;
import kr.co.gaion.scas.security.jwt.mapper.UserMapper;
import kr.co.gaion.scas.security.jwt.mapper.UserRolesMapper;
import kr.co.gaion.scas.security.jwt.mapper.RoleMapper;
import kr.co.gaion.scas.security.jwt.model.ERole;
import kr.co.gaion.scas.security.jwt.model.Role;
import kr.co.gaion.scas.security.jwt.model.User;
import kr.co.gaion.scas.security.jwt.model.UserRole;
import kr.co.gaion.scas.security.jwt.request.LoginRequest;
import kr.co.gaion.scas.security.jwt.request.SignupRequest;
import kr.co.gaion.scas.security.jwt.response.JwtResponse;
import kr.co.gaion.scas.security.jwt.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserMapper userRepository;

  @Autowired
  RoleMapper roleRepository;
  
  @Autowired
  UserRolesMapper userRoleRepository;
  
  @Autowired
  PasswordEncoder encoder;
  
  

  @Autowired
  UserDetailsServiceImpl userService;
  
  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    System.out.println(roles);
    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.findByUsername(signUpRequest.getId()) != null) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

	/*
	 * if (userRepository.existsByEmail(signUpRequest.getEmail())) { return
	 * ResponseEntity .badRequest() .body(new
	 * MessageResponse("Error: Email is already in use!")); }
	 */

    // Create new user's account
    User user = new User(signUpRequest.getId(),signUpRequest.getUsername(), 
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()),
               signUpRequest.getPhonenum(), 
               signUpRequest.getMltrank(),
               signUpRequest.getMltnum(),
               signUpRequest.getMltunit());

    char strRoles = signUpRequest.getUsrth();    

    user.setUsrth(strRoles);
    userRepository.insertUser(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  
  
}
