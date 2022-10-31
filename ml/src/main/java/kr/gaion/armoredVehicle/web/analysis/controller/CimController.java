package kr.gaion.armoredVehicle.web.analysis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import kr.gaion.armoredVehicle.web.analysis.model.Cmncd;
import kr.gaion.armoredVehicle.web.analysis.service.CimService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/cim")
public class CimController {

	@Autowired
	CimService cimService;
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Map<String,Object>> getCmncdList(
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int pageSize){
		try {
			Map<String,Object> cmncdList = new HashMap<String,Object>();
			
			cmncdList = cimService.getCmncdList(page, pageSize);
			
			if(cmncdList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else {
				return new ResponseEntity<>(cmncdList, HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/info/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Cmncd> getCmncd(@PathVariable("id") int id){
		try {
			Cmncd cmncd = cimService.getCmncd(id);
			
			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Cmncd> createCmncd(@RequestBody Cmncd cmncd){
		try {
			Date today = new Date();
			  SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  Date crtdt = dtFormat.parse(dtFormat.format(today));
			  
			  cmncd.setCrtdt(crtdt);
			  cmncd.setMdfcdt(crtdt);
			  cimService.insertCmncd(cmncd);
			  return new ResponseEntity<>(cmncd, HttpStatus.OK);
		  } catch (Exception e) {
			  e.printStackTrace();
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Cmncd> updateCmncd(@RequestBody Cmncd cmncd){
		try {
			Date today = new Date();
			  SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  Date mdfcdt = dtFormat.parse(dtFormat.format(today));
			  
			  cmncd.setMdfcdt(mdfcdt);
			  cimService.updateCmncd(cmncd);
			  return new ResponseEntity<>(cmncd, HttpStatus.OK);
		  } catch (Exception e) {
			  e.printStackTrace();
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Cmncd> deleteCmncd(@PathVariable("id") int id){
		try {
			
			cimService.deleteCmncd(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
