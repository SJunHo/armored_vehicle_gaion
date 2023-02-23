package kr.gaion.armoredVehicle.web.analysis.service;

import kr.gaion.armoredVehicle.web.analysis.mapper.LifeThresholdMapper;
import kr.gaion.armoredVehicle.web.analysis.model.LifeThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LifeThresholdService {

	@Autowired
	private LifeThresholdMapper lifeThresholdMapper;

	public List<LifeThreshold> getList(){
		List<LifeThreshold> thresholdList = lifeThresholdMapper.get();
		return thresholdList;
	}
	
	public void update(List<LifeThreshold> thresholdList) {
		
		for(LifeThreshold t : thresholdList) {
			lifeThresholdMapper.updateThreshold(t);
		}
	}
}
