package kr.gaion.armoredVehicle.web.analysis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.analysis.mapper.CmpntsrplcMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Cmpntsrplc;
import kr.gaion.armoredVehicle.web.analysis.model.CmpntsrplcResponse;

@Service
public class PartsReplacementCycleService {

	@Autowired
	CmpntsrplcMapper cmpntsrplcMapper;
	
	
	public List<CmpntsrplcResponse> findCmpntsrplcList(){
		List<CmpntsrplcResponse> cmpntsrplcList = cmpntsrplcMapper.findCmpntsrplcAll();
		
		return cmpntsrplcList;
	}
	
	public void updateCmpntsrplc(List<Cmpntsrplc> data) {
		
		for(Cmpntsrplc c : data) {
			cmpntsrplcMapper.updateCmpntsrplc(c);
		}
	}
}
