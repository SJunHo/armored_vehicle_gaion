package kr.gaion.armoredVehicle.web.analysis.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import kr.gaion.armoredVehicle.web.analysis.model.ExcelDownByMonitorDiagnos;
import kr.gaion.armoredVehicle.web.analysis.model.Sda;
import kr.gaion.armoredVehicle.web.analysis.model.troubleDataRequest;
import kr.gaion.armoredVehicle.web.analysis.service.StatusDataLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
 * 상태데이터 조회 관련 컨트롤러
 * */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/statusdatalookup")
public class StatusDataLookupController {

	@Autowired
	StatusDataLookupService statusdatalookupService;
	

	@GetMapping("/getAllVehicle")
	public ResponseEntity<List<Sda>> getAllVehicleInfo(){
		try {
			List<Sda> sda = statusdatalookupService.getAllVehicleInfo();
			
			return new ResponseEntity<>(sda, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/searchStatusBer")
	public ResponseEntity<Map<String, Object>> getTroubleBer(@RequestBody troubleDataRequest data){
		try {
			System.out.println("시작");
			Map<String, Object> berdata = statusdatalookupService.getStatusData(data);
			return new ResponseEntity<>(berdata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/searchStatusEng")
	public ResponseEntity<Map<String, Object>> getTroubleEng(@RequestBody troubleDataRequest data){
		try {
			System.out.println("시작");
			Map<String, Object> engdata = statusdatalookupService.getStatusData(data);
			return new ResponseEntity<>(engdata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/searchStatusGrb")
	public ResponseEntity<Map<String, Object>> getTroubleGrb(@RequestBody troubleDataRequest data){
		try {
			Map<String, Object> grbdata = statusdatalookupService.getStatusData(data);
			return new ResponseEntity<>(grbdata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/searchStatusWhl")
	public ResponseEntity<Map<String, Object>> getTroubleWhl(@RequestBody troubleDataRequest data){
		try {
			Map<String, Object> whldata = statusdatalookupService.getStatusData(data);
			return new ResponseEntity<>(whldata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/downloadExcel")
	public void excelDownLoad(HttpServletResponse response,
								@RequestBody ExcelDownByMonitorDiagnos data) {
		statusdatalookupService.downloadExcelTroubleData(response, data);
		response.setHeader("Content-Disposition", "attachment; Filename=\"troubleData.xlsx\"");
		response.setContentType("appliction/ms-excel; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

	}
}
