package kr.gaion.armoredVehicle.web.analysis.controller;


import kr.gaion.armoredVehicle.web.analysis.mapper.SdaMapper;
import kr.gaion.armoredVehicle.web.analysis.model.StatisticalInfo;
import kr.gaion.armoredVehicle.web.analysis.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 통계정보 조회 관련 컨트롤러
 * */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/statistical")
public class StatisticalInfoController {

	@Autowired
	StatisticalTreeInfoService treeInfoService;

	@Autowired
	StatisticalGraphService sGraphService;

	@Autowired
	StatisticalTableService sTableService;

	@Autowired
	PopUpInfoService popUpInfoService;

	@Autowired
	SdaMapper sdaMapper;
	
	@Autowired
	MonitorDiagnosService monitorDiagnosService;
	
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

	@GetMapping("/getId/{name}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> getId(@PathVariable("name") String name) {
	  String sdaid = sdaMapper.findSdaIdBySdanm(name);
	  if (null != sdaid) {
	    return new ResponseEntity<>(sdaid, HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}

	@GetMapping("/getPopUpInfo/{userid}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<String>> getPopUpInfo(@PathVariable("userid") String userid){
		List<String> popUpList = popUpInfoService.getPopUpInfo(userid);
		  if (popUpList != null) {
		    return new ResponseEntity<>(popUpList, HttpStatus.OK);
		  } else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
	}
	
	
	@GetMapping("/getPart/{id}/{startdate}")
	public ResponseEntity<String> getPart(@PathVariable("id") String id, @PathVariable("startdate") Date startdate){
		try {
			String partName = monitorDiagnosService.findPart(id,startdate);
			return new ResponseEntity<>(partName, HttpStatus.OK);
		}catch(Exception e) {
		
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
