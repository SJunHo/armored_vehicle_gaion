package kr.co.gaion.scas.monitoring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gaion.scas.monitoring.mapper.CmncdMapper;
import kr.co.gaion.scas.monitoring.model.Cmncd;
import kr.co.gaion.scas.utils.Paging;

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
		
		List<Cmncd> sdaList = cmncdMapper.findCmncdList(paging.getPagenum()*pageSize, paging.getContentnum());
		
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
