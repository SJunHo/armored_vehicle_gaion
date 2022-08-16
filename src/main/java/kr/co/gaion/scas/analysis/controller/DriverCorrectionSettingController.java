package kr.co.gaion.scas.analysis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.gaion.scas.analysis.model.DriverAttitd;
import kr.co.gaion.scas.analysis.model.Snsr;
import kr.co.gaion.scas.analysis.model.Threshold;
import kr.co.gaion.scas.analysis.model.ThresholdResponse;
import kr.co.gaion.scas.analysis.service.DriverCorrectionService;

@CrossOrigin(origins = "http://localhost:8083")
@RestController
@RequestMapping("/api/driverPostureCorrection")
public class DriverCorrectionSettingController {

	@Autowired
	DriverCorrectionService driverCorrectionService;
	
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<DriverAttitd>> getThresholdList(){
		try {
			List<DriverAttitd> thresholdList = new ArrayList<DriverAttitd>();
			
			thresholdList = driverCorrectionService.findDriverAttitdList();
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
	public ResponseEntity<DriverAttitd> createTutorial(@RequestBody List<DriverAttitd> data) {
	  try {
		  driverCorrectionService.updateThreshold(data);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);
	  } catch (Exception e) {
		  e.printStackTrace();
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@GetMapping("/snsrlist")
	public ResponseEntity<List<Snsr>> findSnsrList(){
		try {
			List<Snsr> snsrList = new ArrayList<Snsr>();
			
			snsrList = driverCorrectionService.findSnsrList();
			if(snsrList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else {
				return new ResponseEntity<>(snsrList, HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
