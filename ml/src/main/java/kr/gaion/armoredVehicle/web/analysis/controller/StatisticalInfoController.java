package kr.gaion.armoredVehicle.web.analysis.controller;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.StatisticalInfo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	@GetMapping("/info")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Map<String, Object> getInfo(){

		JSONObject json = treeInfoService.findTreeInfo();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("tree", json);
		return map;
	}

	@PostMapping("/graph")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Map<String, Object> getGraph(@RequestBody StatisticalInfo info){
		JSONObject json = sGraphService.findGraph(info.getLevel(), info.getUrl(), info.getDate());
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("graph", json);
		return map;
	}

	@PostMapping("/table")
	public Map<String, Object> getTable(@RequestBody StatisticalInfo info){
		JSONObject json = sTableService.findTable(info.getLevel(), info.getUrl(), info.getDate());
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("table", json);
		return map;
	}
}
