package kr.co.gaion.scas.analysis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gaion.scas.analysis.mapper.CmpntsrplcMapper;
import kr.co.gaion.scas.analysis.model.Cmpntsrplc;
import kr.co.gaion.scas.analysis.model.CmpntsrplcResponse;

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
