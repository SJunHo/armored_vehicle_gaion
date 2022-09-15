package kr.gaion.armoredVehicle.web.analysis.controller;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.gaion.armoredVehicle.web.analysis.service.StatisticalGraphService;
import kr.gaion.armoredVehicle.web.analysis.service.StatisticalTableService;
import kr.gaion.armoredVehicle.web.analysis.service.StatisticalTreeInfoService;


@CrossOrigin(origins = "http://localhost:8083")
@RestController
@RequestMapping("/api/statistical")
public class StatisticalInfoController {

	@Autowired
	StatisticalTreeInfoService treeInfoService;
	
	@Autowired
	StatisticalGraphService sGraphService;
	
	@Autowired
	StatisticalTableService sTableService;
	
//	@GetMapping("/info")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	public Map<String, Object> getInfo(){
//
//		JSONObject json = treeInfoService.findTreeInfo();
//		Map<String, Object> map = new HashMap<String,Object>();
//		map.put("tree", json);
//		return map;
//	}
	
	@GetMapping("/graph/{level}/{url}/{date}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Map<String, Object> getGraph(@PathVariable("level") String level,
										@PathVariable("url") String url,
										@PathVariable("date") Date date){
		JSONObject json = sGraphService.findGraph(level, url, date);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("graph", json);
		return map;
	}
	
	@GetMapping("/table/{level}/{url}/{date}")
	public Map<String, Object> getTable(@PathVariable("level") String level,
										@PathVariable("url") String url,
										@PathVariable("date") Date date){
		JSONObject json = sTableService.findTable(level, url, date);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("table", json);
		return map;
	}
}
