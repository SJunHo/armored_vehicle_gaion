package kr.gaion.armoredVehicle.web.analysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.security.jwt.mapper.UserMapper;
import kr.gaion.armoredVehicle.web.security.jwt.model.User;
import kr.gaion.armoredVehicle.web.utils.Paging;

@Service
public class UserManagementService {
	 @Autowired
	 UserMapper userRepository;

	 public Map<String,Object> findUserList(int page, int pageSize){
		  	Paging paging = new Paging();
			
			paging.setTotalcount(userRepository.countUsers());
			paging.setPagenum(page-1);
			paging.setContentnum(pageSize);
			paging.setCurrentblock(page);
			paging.setLastblock(paging.getTotalcount());
			
			paging.prevnext(page);
			paging.setStartPage(paging.getCurrentblock());
			paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
			paging.setTotalPageCount();
			Map<String, Integer> parameter = new HashMap<String, Integer>();
			parameter.put("page",paging.getPagenum()*pageSize);
			parameter.put("pageSize", paging.getContentnum());
			List<User> userList = userRepository.findUserList(parameter);
			
			Map<String, Object> map = new HashMap<String,Object>();
			
			map.put("userList",userList);
			map.put("paging", paging);
			return map;
		  
	  }
	 public User findById(String id) {
		 return userRepository.findById(id);
	 }
	 
	 public void updateUser(User user) {
		 userRepository.updateUser(user);
	 }
	 public void deleteUser(String id) {
		 userRepository.deleteUser(id);
	 }
}
