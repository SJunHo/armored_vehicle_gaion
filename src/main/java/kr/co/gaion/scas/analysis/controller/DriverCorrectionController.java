package kr.co.gaion.scas.analysis.controller;

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
import org.springframework.web.bind.annotation.RestController;

import kr.co.gaion.scas.analysis.model.Cmncd;
import kr.co.gaion.scas.analysis.model.DriverCorrectInfo;
import kr.co.gaion.scas.analysis.model.Sda;
import kr.co.gaion.scas.analysis.model.SearchRequest;
import kr.co.gaion.scas.analysis.model.TreeInfo;
import kr.co.gaion.scas.analysis.service.DriverCorrectionService;

@CrossOrigin(origins = "http://localhost:8083")
@RestController
@RequestMapping("/api/drivercorrection")
public class DriverCorrectionController {

	@Autowired
	DriverCorrectionService driverCorrectionService;

	@GetMapping("/divsList")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Cmncd>> getDivList() {
		try {

			List<Cmncd> cmncd = driverCorrectionService.getDivsList();
			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/bnList/{data}") // date -> service
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<TreeInfo>> getBnList(@PathVariable("data") String data) {
		try {

			List<TreeInfo> cmncd = driverCorrectionService.getBnList(data);

			return new ResponseEntity<>(cmncd, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/sdaList/{data}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Sda>> getSdaList(@PathVariable("data") String data) {
		try {

			List<Sda> sda = driverCorrectionService.getSdaList(data);

			return new ResponseEntity<>(sda, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/search")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Map<String,Object>>  search(@RequestBody SearchRequest data) {
		try {
			Map<String,Object> searchList = driverCorrectionService.getSearchResult(data);
			return new ResponseEntity<>(searchList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}