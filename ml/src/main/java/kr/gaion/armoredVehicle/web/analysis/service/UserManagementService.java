package kr.gaion.armoredVehicle.web.analysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.mapper.CmncdMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.SdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.TreeInfoMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Cmncd;
import kr.gaion.armoredVehicle.web.analysis.model.TreeInfo;
import kr.gaion.armoredVehicle.web.security.jwt.mapper.UsercdMapper;
import kr.gaion.armoredVehicle.web.security.jwt.model.Usercd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.utils.Paging;

@Service
public class UserManagementService {
	 @Autowired
	 UsercdMapper usercdRepository;

	@Autowired
	CmncdMapper cmncdMapper;

	@Autowired
	TreeInfoMapper treeinfoMapper;

	 public Map<String,Object> findUserList(int page, int pageSize){
		  	Paging paging = new Paging();
			
			paging.setTotalcount(usercdRepository.countUsers());
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
			List<Usercd> userList = usercdRepository.findUserList(parameter);
			
			Map<String, Object> map = new HashMap<String,Object>();
			
			map.put("userList",userList);
			map.put("paging", paging);
			return map;
		  
	  }
	 public Usercd findById(String id) {
		 return usercdRepository.findByUserid(id);
	 }
	 
	 public void updateUser(Usercd usercd) {
		 usercdRepository.updateUser(usercd);
	 }
	 public void deleteUser(String id) {
		 usercdRepository.deleteUser(id);
	 }

	public List<Cmncd> getDivsList() {
		String code="B";
		return cmncdMapper.findListByCode(code);
	}

	public List<TreeInfo> getBnList(String treeinfoname) {

		int treeinfoid = treeinfoMapper.findTreeInfoIdByTreeinfoname(treeinfoname);
		List<TreeInfo> treeList = treeinfoMapper.findHeader(treeinfoid);

		return treeList;
	}
}
