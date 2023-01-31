package kr.gaion.armoredVehicle.web.analysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.analysis.mapper.BerdataMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.EngdataMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.GrbdataMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.WhldataMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Berlife;
import kr.gaion.armoredVehicle.web.analysis.model.Englife;
import kr.gaion.armoredVehicle.web.analysis.model.Grblife;
import kr.gaion.armoredVehicle.web.analysis.model.Whllife;
import kr.gaion.armoredVehicle.web.analysis.model.troubleDataRequest;
import kr.gaion.armoredVehicle.web.utils.Paging;

@Service
public class MonitorRemainingService {

	@Autowired
	BerdataMapper berdataMapper;
	
	@Autowired
	EngdataMapper engdataMapper;
	
	@Autowired
	GrbdataMapper grbdataMapper;
	
	@Autowired
	WhldataMapper whldataMapper;
	
	public Map<String, Object> getBerlife(troubleDataRequest data){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Paging paging = new Paging();
		int page = data.getPage();
		int pageSize = data.getSize();
		
		paging.setTotalcount(berdataMapper.countBerlife());
		paging.setPagenum(page -1);
		paging.setContentnum(pageSize);
		paging.setCurrentblock(paging.getTotalcount());
		
		paging.prevnext(page);
		paging.setStartPage(paging.getCurrentblock());
		paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
		paging.setTotalPageCount();
		
		data.setPage(paging.getPagenum()*pageSize);
		data.setSize(paging.getContentnum());
		
		List<Berlife> berlifeData = berdataMapper.findBerlife(data);
		
		map.put("lifeList", berlifeData);
		map.put("paging", paging);
		
		return map;
	}
	
	public Map<String, Object> getEnglife(troubleDataRequest data){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Paging paging = new Paging();
		int page = data.getPage();
		int pageSize = data.getSize();
		
		paging.setTotalcount(engdataMapper.countEnglife());
		paging.setPagenum(page -1);
		paging.setContentnum(pageSize);
		paging.setCurrentblock(paging.getTotalcount());
		
		paging.prevnext(page);
		paging.setStartPage(paging.getCurrentblock());
		paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
		paging.setTotalPageCount();
		
		data.setPage(paging.getPagenum()*pageSize);
		data.setSize(paging.getContentnum());
		
		List<Englife> englifeData = engdataMapper.findEnglife(data);
		
		map.put("lifeList", englifeData);
		map.put("paging", paging);
		
		return map;
	}
	
	public Map<String, Object> getGrblife(troubleDataRequest data){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Paging paging = new Paging();
		int page = data.getPage();
		int pageSize = data.getSize();
		
		paging.setTotalcount(grbdataMapper.countGrblife());
		paging.setPagenum(page -1);
		paging.setContentnum(pageSize);
		paging.setCurrentblock(paging.getTotalcount());
		
		paging.prevnext(page);
		paging.setStartPage(paging.getCurrentblock());
		paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
		paging.setTotalPageCount();
		
		data.setPage(paging.getPagenum()*pageSize);
		data.setSize(paging.getContentnum());
		
		List<Grblife> grblifeData = grbdataMapper.findGrblife(data);
		
		map.put("lifeList", grblifeData);
		map.put("paging", paging);
		
		return map;
	}
	
	public Map<String, Object> getWhllife(troubleDataRequest data){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Paging paging = new Paging();
		int page = data.getPage();
		int pageSize = data.getSize();
		
		paging.setTotalcount(whldataMapper.countWhllife());
		paging.setPagenum(page -1);
		paging.setContentnum(pageSize);
		paging.setCurrentblock(paging.getTotalcount());
		
		paging.prevnext(page);
		paging.setStartPage(paging.getCurrentblock());
		paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
		paging.setTotalPageCount();
		
		data.setPage(paging.getPagenum()*pageSize);
		data.setSize(paging.getContentnum());
		
		List<Whllife> whllifeData = whldataMapper.findWhllife(data);
		
		map.put("lifeList", whllifeData);
		map.put("paging", paging);
		
		return map;
	}
}
