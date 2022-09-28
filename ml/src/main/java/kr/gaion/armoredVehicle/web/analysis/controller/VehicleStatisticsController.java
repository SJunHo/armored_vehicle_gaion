package kr.gaion.armoredVehicle.web.analysis.controller;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import kr.gaion.armoredVehicle.web.analysis.model.Cmncd;
import kr.gaion.armoredVehicle.web.analysis.model.Sda;
import kr.gaion.armoredVehicle.web.analysis.model.SdaData;
import kr.gaion.armoredVehicle.web.analysis.model.SdaDataWithDtctsda;
import kr.gaion.armoredVehicle.web.analysis.model.UserSnsr;
import kr.gaion.armoredVehicle.web.analysis.service.VehicleStaticsService;

@CrossOrigin(origins = "http://localhost:8083")
@RestController
@RequestMapping("/api/vehicleStatistics")
public class VehicleStatisticsController {

	@Autowired
	VehicleStaticsService vehicleStaticsService;


//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")

	@GetMapping("/searchEachInfo/{id}")
	public ResponseEntity<Sda> getEachData(@PathVariable("id") String id){
		try {
			Sda sda = vehicleStaticsService.getEachInfo(id);
			return new ResponseEntity<>(sda,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchEachInfo/getAll")
	public ResponseEntity<List<Sda>> getAllVehicleData(){
		try {

			List<Sda> sda = vehicleStaticsService.getAllVehicleInfo();

			return new ResponseEntity<>(sda, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/searchEachInfo/getFile/{id}")
	public ResponseEntity<List<SdaData>> getFileWithId(@PathVariable("id") String id){
		try{

			List<SdaData> sdaData = vehicleStaticsService.getFileWithId(id);

			return new ResponseEntity<>(sdaData, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/searchEachInfo/getForBtn1")
	public ResponseEntity<List<Cmncd>> getForButton1(){
		try {
			List<Cmncd> cmncd = vehicleStaticsService.getForBtn1();
			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchEachInfo/getForBtn2")
	public ResponseEntity<List<Cmncd>> getForButton2(){
		try {
			List<Cmncd> cmncd = vehicleStaticsService.getForBtn2();
			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchEachInfo/getChartData/{param1}+{param2}+{param3}")
	public ResponseEntity<List<SdaData>> getChartData(@PathVariable("param1") List param1,
													  @PathVariable("param2") List param2,
													  @PathVariable("param3") List param3 ) {
		try {
			List<SdaData> sdaData = vehicleStaticsService.getChartData(param1, param2, param3);

			return new ResponseEntity<>(sdaData, HttpStatus.OK);

		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchEachInfo/getBookmark/{id}/{grpid}")
	public ResponseEntity<List<UserSnsr>> getBookmark(@PathVariable("id") String id, @PathVariable("grpid") String grpid){
		try {
			List<UserSnsr> userSnsrList = vehicleStaticsService.getBookmark(id, grpid);
			return new ResponseEntity<>(userSnsrList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/allBookmarkForDefault/{id}")
	public ResponseEntity<List<Cmncd>> defaultBookmark(@PathVariable("id") String userid){
		try {
			List<Cmncd> usersnsr = vehicleStaticsService.defaultBookmark(userid);
			return new ResponseEntity<>(usersnsr, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/searchEachInfo/insertBookmarks")
	public ResponseEntity<UserSnsr> insertBookmark(@RequestBody List<UserSnsr> userSnsr) {
		try {

			vehicleStaticsService.insertBookmark(userSnsr);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getDtctsdaData/{params1}/{params2}/{params3}")
	public ResponseEntity<List<SdaDataWithDtctsda>> getDtctsdaData(@PathVariable("params1") List param1,
																   @PathVariable("params2") List param2,
																   @PathVariable("params3") List param3){
		try {
			List<SdaDataWithDtctsda> sdadata = vehicleStaticsService.getDtctsdaData(param1, param2, param3);

			return new ResponseEntity<>(sdadata, HttpStatus.OK);


		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/findRecentFile/{sdaid}")
	public ResponseEntity<String> findRecentFile(@PathVariable("sdaid") String sdaid){
		try {
			String fileName = vehicleStaticsService.findRecentFile(sdaid);
			return new ResponseEntity<>(fileName, HttpStatus.OK);
		}catch(Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
