package kr.co.gaion.scas.analysis.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.gaion.scas.analysis.model.Cmncd;
import kr.co.gaion.scas.analysis.service.UserManagementService;
import kr.co.gaion.scas.security.jwt.model.User;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserManagementController {
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserManagementService userManagementService;
	
	@GetMapping("/list")
	  public ResponseEntity<Map<String,Object>> getCmncdList(
				@RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "10") int pageSize){
			try {
				Map<String,Object> userList = new HashMap<String,Object>();
				
				userList = userManagementService.findUserList(page, pageSize);
				
				if(userList.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}else {
					return new ResponseEntity<>(userList, HttpStatus.OK);
				}
			}catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	  }
	  
	  	@GetMapping("/info/{id}")
		public ResponseEntity<User> getUser(@PathVariable("id") String id){
			try {
				User user = userManagementService.findById(id);
				String password = null;
				user.setPassword(password);
				return new ResponseEntity<>(user, HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	  	
	  	@PostMapping("/update")
		public ResponseEntity<User> updateUser(@RequestBody User user){
			try {
					if(user.getPassword() != null) {
						String password = encoder.encode(user.getPassword());
						user.setPassword(password);
					}
					userManagementService.updateUser(user);
				  return new ResponseEntity<>(user, HttpStatus.OK);
			  } catch (Exception e) {
				  e.printStackTrace();
			    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			  }
		}
	  	
	  	@GetMapping("/delete/{id}")
		public ResponseEntity<Cmncd> deleteUser(@PathVariable("id") String id){
			try {
				userManagementService.deleteUser(id);
				return new ResponseEntity<>(null, HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
}
