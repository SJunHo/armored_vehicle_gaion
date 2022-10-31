package kr.gaion.armoredVehicle.web.analysis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.Cmncd;
import kr.gaion.armoredVehicle.web.analysis.model.TreeInfo;
import kr.gaion.armoredVehicle.web.security.jwt.model.Usercd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.gaion.armoredVehicle.web.analysis.service.UserManagementService;

@CrossOrigin(origins = "*")
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
		public ResponseEntity<Usercd> getUser(@PathVariable("id") String id){
			try {
				Usercd usercd = userManagementService.findById(id);
				String password = null;
				usercd.setPwd(password);
				return new ResponseEntity<>(usercd, HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	  	
	  	@PostMapping("/update")
		public ResponseEntity<Usercd> updateUser(@RequestBody Usercd usercd){
			try {
					if(usercd.getPwd() != null) {
						String password = encoder.encode(usercd.getPwd());
						usercd.setPwd(password);
					}
					userManagementService.updateUser(usercd);
				  return new ResponseEntity<>(usercd, HttpStatus.OK);
			  } catch (Exception e) {
				  e.printStackTrace();
			    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			  }
		}
	  	
	  	@GetMapping("/delete/{id}")
		public ResponseEntity<Usercd> deleteUser(@PathVariable("id") String id){
			try {
				userManagementService.deleteUser(id);
				return new ResponseEntity<>(null, HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	@GetMapping("/divsList")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Cmncd>> getDivList() {
		try {

			List<Cmncd> cmncd = userManagementService.getDivsList();
			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/bnList/{data}") // date -> service
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<TreeInfo>> getBnList(@PathVariable("data") String data) {
		try {

			List<TreeInfo> cmncd = userManagementService.getBnList(data);

			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
