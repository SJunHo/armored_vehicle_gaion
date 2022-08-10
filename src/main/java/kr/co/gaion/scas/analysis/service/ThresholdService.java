package kr.co.gaion.scas.analysis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gaion.scas.analysis.mapper.CmncdMapper;
import kr.co.gaion.scas.analysis.mapper.SnsrMapper;
import kr.co.gaion.scas.analysis.mapper.ThresholdMapper;
import kr.co.gaion.scas.analysis.model.Cmncd;
import kr.co.gaion.scas.analysis.model.Snsr;
import kr.co.gaion.scas.analysis.model.Threshold;
import kr.co.gaion.scas.analysis.model.ThresholdResponse;

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
