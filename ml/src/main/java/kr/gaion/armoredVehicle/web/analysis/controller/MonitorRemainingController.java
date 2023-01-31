package kr.gaion.armoredVehicle.web.analysis.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.gaion.armoredVehicle.web.analysis.model.troubleDataRequest;
import kr.gaion.armoredVehicle.web.analysis.service.MonitorRemainingService;

/*
 * 잔존수명 예지 모니터링 관련 컨트롤러
 * */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/monitorremaining")
public class MonitorRemainingController {

	@Autowired
	MonitorRemainingService remainingService;
	
	// 잔존수명예지 베어링 데이터 가져오기
	@PostMapping("/searchBerlife")
	public ResponseEntity<Map<String, Object>> getBerlife(@RequestBody troubleDataRequest data){
		try {
			Map<String, Object> berdata = remainingService.getBerlife(data);
			return new ResponseEntity<>(berdata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// 잔존수명예지 엔진 데이터 가져오기
	@PostMapping("/searchEnglife")
	public ResponseEntity<Map<String, Object>> getEnglife(@RequestBody troubleDataRequest data){
		try {
			Map<String, Object> engdata = remainingService.getEnglife(data);
			return new ResponseEntity<>(engdata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// 잔존수명예지 기어박스 데이터 가져오기
	@PostMapping("/searchGrblife")
	public ResponseEntity<Map<String, Object>> getGrblife(@RequestBody troubleDataRequest data){
		try {
			Map<String, Object> grbdata = remainingService.getGrblife(data);
			return new ResponseEntity<>(grbdata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// 잔존수명예지 휠 데이터 가져오기
	@PostMapping("/searchWhllife")
	public ResponseEntity<Map<String, Object>> getWhllife(@RequestBody troubleDataRequest data){
		try {
			Map<String, Object> whldata = remainingService.getWhllife(data);
			return new ResponseEntity<>(whldata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
