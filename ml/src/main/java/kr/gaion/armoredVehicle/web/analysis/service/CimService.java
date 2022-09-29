package kr.gaion.armoredVehicle.web.analysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.analysis.mapper.CmncdMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Cmncd;
import kr.gaion.armoredVehicle.web.utils.Paging;

@Service
public class CimService {

	@Autowired
	private CmncdMapper cmncdMapper;
	
	public Map<String, Object> getCmncdList(int page, int pageSize){
		
		Paging paging = new Paging();
		
		paging.setTotalcount(cmncdMapper.countCmncdAll());
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

		List<Cmncd> sdaList = cmncdMapper.findCmncdList(parameter);
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("cmncdList",sdaList);
		map.put("paging", paging);
		return map;
	}
	
	public Cmncd getCmncd(int id) {
		return cmncdMapper.findCmncd(id);
	}
	public void insertCmncd(Cmncd cmncd) {
		cmncdMapper.insertCmncd(cmncd);
	}
	
	public void updateCmncd(Cmncd cmncd) {
		cmncdMapper.updateCmncd(cmncd);
	}
	
	public void deleteCmncd(int id) {
		cmncdMapper.deleteCmncd(id);
	} 
}
