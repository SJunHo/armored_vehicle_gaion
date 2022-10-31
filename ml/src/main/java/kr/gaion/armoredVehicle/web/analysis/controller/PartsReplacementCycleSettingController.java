package kr.gaion.armoredVehicle.web.analysis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import kr.gaion.armoredVehicle.web.analysis.service.PartsReplacementCycleService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/partsreplacementcycle")
public class PartsReplacementCycleSettingController {

	@Autowired
	PartsReplacementCycleService ReplacementService;

	@GetMapping("/list")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<CmpntsrplcResponse>> getThresholdList(){
		try {
			List<CmpntsrplcResponse> thresholdList = new ArrayList<CmpntsrplcResponse>();

			thresholdList = ReplacementService.findCmpntsrplcList();
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
		  ReplacementService.updateCmpntsrplc(data);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);
	  } catch (Exception e) {
		  e.printStackTrace();
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}

	@GetMapping("/divsList")
	public ResponseEntity<List<Cmncd>> getDivList() {
		try {

			List<Cmncd> cmncd = ReplacementService.getDivsList();
			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/bnList/{data}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<TreeInfo>> getBnList(@PathVariable("data") String data) {
		try {

			List<TreeInfo> cmncd = ReplacementService.getBnList(data);

			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vnList/{data}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Sda>> getSdaList(@PathVariable("data") String data) {
		try {

			List<Sda> sda = ReplacementService.getVnList(data);

			return new ResponseEntity<>(sda, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/cmncdList/{data}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Cmncd>> getCmncdList(@PathVariable("data") String data){
		try {
			List<Cmncd> cmncd = ReplacementService.getCmncdList(data);

			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/search")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<CmpntsrplcInfo>> search(@RequestBody Map<String, Object> paramMap) {
		try {
			CmpntsrplcInfo param = new CmpntsrplcInfo();
			param.setSdaid((String)paramMap.get("sdaid"));
			param.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse((String)paramMap.get("startDate")));
			param.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse((String)paramMap.get("endDate")));
			List<CmpntsrplcInfo> searchList = ReplacementService.search(param);
			return new ResponseEntity<>(searchList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<CmpntsrplcHistry>> add(@RequestBody CmpntsrplcData add) {
		try {
			ReplacementService.insertData(add);

			CmpntsrplcHistry param = new CmpntsrplcHistry();
			param.setSdaid(add.getSdaid());
			param.setGrid(add.getGrid());
			List<CmpntsrplcHistry> searchList = ReplacementService.getHistory(param);
			return new ResponseEntity<>(searchList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/history")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<CmpntsrplcHistry>> history(@RequestBody CmpntsrplcHistry param) {
		try {
			List<CmpntsrplcHistry> searchList = ReplacementService.getHistory(param);
			return new ResponseEntity<>(searchList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
