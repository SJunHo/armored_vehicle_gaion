package kr.gaion.armoredVehicle.web.analysis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.analysis.mapper.CmncdMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.SnsrMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.ThresholdMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Cmncd;
import kr.gaion.armoredVehicle.web.analysis.model.Snsr;
import kr.gaion.armoredVehicle.web.analysis.model.Threshold;

@Service
public class ThresholdService {

	@Autowired
	private ThresholdMapper thresholdMapper;
	
	@Autowired
	private SnsrMapper snsrMapper;
	
	@Autowired
	private CmncdMapper cmncdMapper;
	
	public List<Threshold> findThresholdList(){
		List<Threshold> thresholdList = thresholdMapper.findThresholdList();
		
		List<Cmncd> cmncdList = cmncdMapper.findListByCode("P");
		

		for(Threshold t : thresholdList) {
			for(Cmncd c : cmncdList) {
				if(c.getVar().equals(t.getSnsrid())) {
					t.setExpln(c.getExpln());
				}
			}
		}
		
		return thresholdList;
	}
	
	public void updateThreshold(List<Threshold> thresholdList) {
		
		for(Threshold t : thresholdList) {
			thresholdMapper.updateThreshold(t);
		}
	}
	
	public List<Snsr> findSnsrList() {
		return snsrMapper.findSnsrList();
	}
}
