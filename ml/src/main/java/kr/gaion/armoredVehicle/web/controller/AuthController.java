package kr.gaion.armoredVehicle.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.*;

import kr.gaion.armoredVehicle.web.security.jwt.mapper.UsercdMapper;
import kr.gaion.armoredVehicle.web.security.jwt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.gaion.armoredVehicle.web.security.jwt.JwtUtils;
import kr.gaion.armoredVehicle.web.security.jwt.mapper.RoleMapper;
import kr.gaion.armoredVehicle.web.security.jwt.request.LoginRequest;
import kr.gaion.armoredVehicle.web.security.jwt.request.SignupRequest;
import kr.gaion.armoredVehicle.web.security.jwt.response.JwtResponse;
import kr.gaion.armoredVehicle.web.security.jwt.response.MessageResponse;
import kr.gaion.armoredVehicle.web.security.service.UserDetailsImpl;
import kr.gaion.armoredVehicle.web.security.service.UserDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  RoleMapper roleRepository;

  @Autowired
  PasswordEncoder encoder;
  
  @Autowired
  UsercdMapper usercdRepository;

  @Autowired
  UserDetailsServiceImpl userService;
  
  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      Usercd usercd= usercdRepository.findByUserid(loginRequest.getId());
      String jwt = jwtUtils.generateJwtToken(usercd);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
          .map(item -> item.getAuthority())
          .collect(Collectors.toList());
      System.out.println(roles);
      if(roles.contains("ROLE_USER")) {
        return new ResponseEntity<>("권한이 있는 계정으로 로그인해주세요", HttpStatus.BAD_REQUEST);
      }else {
        //로그인 시점 insert 로그인 정보
        Map<String, Object> param = new HashMap<String, Object>();
        Date now = new Date();
        param.put("userid", userDetails.getUserId());
        param.put("logindt", now);
        usercdRepository.insertUserLog(param);
      }
      return ResponseEntity.ok(new JwtResponse(jwt,
              userDetails.getUserId(),
              userDetails.getUsername(),
              roles));
    }catch(DisabledException e) {
      return new ResponseEntity<>("계정이 비활성화 되어있습니다",HttpStatus.BAD_REQUEST);
    }catch(LockedException e) {
      return new ResponseEntity<>("계정이 잠겨있습니다",HttpStatus.BAD_REQUEST);
    }catch(BadCredentialsException e) {
      return new ResponseEntity<>("비밀번호가 틀렸습니다",HttpStatus.BAD_REQUEST);
    }catch(Exception e) {
      return new ResponseEntity<>("존재하지 않는 계정입니다",HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (usercdRepository.findByUserid(signUpRequest.getUserid()) != null) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: UserId is already taken!"));
    }

	/*
	 * if (userRepository.existsByEmail(signUpRequest.getEmail())) { return
	 * ResponseEntity .badRequest() .body(new
	 * MessageResponse("Error: Email is already in use!")); }
	 */

    Date today = new Date();
    SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date crtdt = null;
    try {
      crtdt = sformat.parse(sformat.format(today));
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    // Create new user's account

    Usercd usercd = new Usercd(signUpRequest.getUserid(),signUpRequest.getName(),
               encoder.encode(signUpRequest.getPwd()),
               signUpRequest.getUsrth(),
               signUpRequest.getRnkcd(),
               signUpRequest.getSrvno(),
               crtdt,
               signUpRequest.getDivs(),
               signUpRequest.getBrgd(),
               signUpRequest.getBn(),
               signUpRequest.getRspofc(),
               signUpRequest.getTelno1(),
               signUpRequest.getTelno2());
    usercd.setUsedvcd('Y');
    usercdRepository.insertUsercd(usercd);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  
  
}
