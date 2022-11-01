package kr.gaion.armoredVehicle.web.analysis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import kr.gaion.armoredVehicle.web.analysis.mapper.ThresholdMapper;
import kr.gaion.armoredVehicle.web.analysis.model.*;
import kr.gaion.armoredVehicle.web.analysis.service.MonitorDiagnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/monitordiagnost")
public class MonitorDiagnosController {
	
	@Autowired
	MonitorDiagnosService monitordiagnosService;
	
	@Autowired
	ThresholdMapper thresholdMapper;
	
	@GetMapping("/getAllVehicle")
	public ResponseEntity<List<Sda>> getAllVehicleInfo(){
		try {
			List<Sda> sda = monitordiagnosService.getAllVehicleInfo();
			
			return new ResponseEntity<>(sda, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/searchTroubleBer")
	public ResponseEntity<Map<String, Object>> getTroubleBer(@RequestBody troubleDataRequest data){
		try {
			System.out.println("시작");
			Map<String, Object> berdata = monitordiagnosService.getTroubleData(data);
			return new ResponseEntity<>(berdata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/searchTroubleEng")
	public ResponseEntity<Map<String, Object>> getTroubleEng(@RequestBody troubleDataRequest data){
		try {
			System.out.println("시작");
			Map<String, Object> engdata = monitordiagnosService.getTroubleData(data);
			return new ResponseEntity<>(engdata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/searchTroubleGrb")
	public ResponseEntity<Map<String, Object>> getTroubleGrb(@RequestBody troubleDataRequest data){
		try {
			Map<String, Object> grbdata = monitordiagnosService.getTroubleData(data);
			return new ResponseEntity<>(grbdata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/searchTroubleWhl")
	public ResponseEntity<Map<String, Object>> getTroubleWhl(@RequestBody troubleDataRequest data){
		try {
			Map<String, Object> whldata = monitordiagnosService.getTroubleData(data);
			return new ResponseEntity<>(whldata, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/downloadExcel")
	public void excelDownLoad(HttpServletResponse response,
								@RequestBody ExcelDownByMonitorDiagnos data) {
		monitordiagnosService.downloadExcelTroubleData(response, data);
		response.setHeader("Content-Disposition", "attachment; Filename=\"troubleData.xlsx\"");
		response.setContentType("appliction/ms-excel; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

	}
					
//	@PostMapping("/searchAllTroubleChartData")
//	public ResponseEntity<Map<String, Object>> getAllTroubleData(@RequestBody troubleDataRequest data){
//		try {
//			Map<String, Object> troubleForChartData = monitordiagnosService.getAllTroubleDataForChart(data);
//			return new ResponseEntity<>(troubleForChartData, HttpStatus.OK);
//		} catch(Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@PostMapping("/searchParamdesc")
	ResponseEntity<List<Paramdesc>> getParamdesc(@RequestBody ParamdescRequest data){
		System.out.println(data.getParamList());
		try {
			List<Paramdesc> paramdescList = monitordiagnosService.getParamdescList(data);
			return new ResponseEntity<>(paramdescList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getThreshold")
	public ResponseEntity<List<Threshold>> getThreshold() {
		try {
			List<Threshold> thresholdList = new ArrayList<Threshold>();
			
			thresholdList = thresholdMapper.findThresholdList();
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
}
