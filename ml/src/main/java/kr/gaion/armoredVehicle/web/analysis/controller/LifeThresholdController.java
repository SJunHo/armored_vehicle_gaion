package kr.gaion.armoredVehicle.web.analysis.controller;

import kr.gaion.armoredVehicle.web.analysis.model.LifeThreshold;
import kr.gaion.armoredVehicle.web.analysis.service.LifeThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lifethreshold")
public class LifeThresholdController {

	@Autowired
	private LifeThresholdService lifeThresholdService;
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<LifeThreshold>> getThresholdList(){
		try {
			List<LifeThreshold> thresholdList = new ArrayList<>();
			thresholdList = lifeThresholdService.getList();
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
	public ResponseEntity<LifeThreshold> update(@RequestBody List<LifeThreshold> data) {
	  try {
		  lifeThresholdService.update(data);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);
	  } catch (Exception e) {
		  e.printStackTrace();
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
}
