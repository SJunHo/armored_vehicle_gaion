package kr.gaion.armoredVehicle.web.analysis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.analysis.mapper.CmncdMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.SnsrMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.ThresholdMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Cmncd;
import kr.gaion.armoredVehicle.web.analysis.model.Snsr;
import kr.gaion.armoredVehicle.web.analysis.model.Threshold;
import kr.gaion.armoredVehicle.web.analysis.model.ThresholdResponse;

@Service
public class ThresholdService {

	@Autowired
	private ThresholdMapper thresholdMapper;
	
	@Autowired
	private SnsrMapper snsrMapper;
	
	@Autowired
	private CmncdMapper cmncdMapper;
	
	public List<ThresholdResponse> findThresholdList(){
		List<ThresholdResponse> thresholdList = thresholdMapper.findThresholdList();
		
		List<Cmncd> cmncdList = cmncdMapper.findListByCode("P");
		

		for(ThresholdResponse t : thresholdList) {
			for(Cmncd c : cmncdList) {
				if(c.getVar().equals(t.getSnsrid())) {
					t.setExpln(c.getExpln());
				}
			}
		}
		
		return thresholdList;
	}
	
	public void updateThreshold(List<ThresholdResponse> thresholdList) {
		
		for(ThresholdResponse t : thresholdList) {
			thresholdMapper.updateThreshold(t);
		}
	}
	
	public List<Snsr> findSnsrList() {
		return snsrMapper.findSnsrList();
	}
}
