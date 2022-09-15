package kr.gaion.armoredVehicle.web.analysis.controller;

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
import org.springframework.web.bind.annotation.RestController;

import kr.gaion.armoredVehicle.web.analysis.model.Cmpntsrplc;
import kr.gaion.armoredVehicle.web.analysis.model.CmpntsrplcResponse;
import kr.gaion.armoredVehicle.web.analysis.service.PartsReplacementCycleService;

@CrossOrigin(origins = "http://localhost:8083")
@RestController
@RequestMapping("/api/partsreplacementcycle")
public class PartsReplacementCycleSettingController {

	@Autowired
	PartsReplacementCycleService prcService;
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<CmpntsrplcResponse>> getThresholdList(){
		try {
			List<CmpntsrplcResponse> thresholdList = new ArrayList<CmpntsrplcResponse>();
			
			thresholdList = prcService.findCmpntsrplcList();
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
	public ResponseEntity<Cmpntsrplc> createTutorial(@RequestBody List<Cmpntsrplc> data) {
	  try {
		  prcService.updateCmpntsrplc(data);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);
	  } catch (Exception e) {
		  e.printStackTrace();
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
}
