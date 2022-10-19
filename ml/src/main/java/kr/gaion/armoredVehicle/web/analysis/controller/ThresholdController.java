package kr.gaion.armoredVehicle.web.analysis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.gaion.armoredVehicle.web.analysis.model.Threshold;
import kr.gaion.armoredVehicle.web.analysis.model.ThresholdResponse;
import kr.gaion.armoredVehicle.web.analysis.service.ThresholdService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/threshold")
public class ThresholdController {

	@Autowired
	private ThresholdService thresholdService;
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<ThresholdResponse>> getThresholdList(@RequestParam(required = false) String title){
		try {
			List<ThresholdResponse> thresholdList = new ArrayList<ThresholdResponse>();
			
			thresholdList = thresholdService.findThresholdList();
			if(thresholdList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else {
				return new ResponseEntity<>(thresholdList, HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Threshold> createTutorial(@RequestBody List<ThresholdResponse> data) {
	  try {
		  thresholdService.updateThreshold(data);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);
	  } catch (Exception e) {
		  e.printStackTrace();
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
}
