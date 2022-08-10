package kr.co.gaion.scas.analysis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gaion.scas.analysis.mapper.DriverattitdMapper;
import kr.co.gaion.scas.analysis.mapper.SnsrMapper;
import kr.co.gaion.scas.analysis.model.DriverAttitd;
import kr.co.gaion.scas.analysis.model.Snsr;

@Service
public class DriverCorrectionService {

	@Autowired
	DriverattitdMapper driverAttitdMapper;
	
	@Autowired
	SnsrMapper snsrMapper;
	
	public List<DriverAttitd> findDriverAttitdList(){
		return driverAttitdMapper.findDriverattitdAll();
	}
	
	public void updateThreshold(List<DriverAttitd> data) {
		for(DriverAttitd da : data) {
			driverAttitdMapper.insertDriverAttitd(da);			
		}
	}
	
	public List<Snsr> findSnsrList(){
		return snsrMapper.findSnsrList();
	}
}
