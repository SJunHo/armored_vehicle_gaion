package kr.co.gaion.scas.analysis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gaion.scas.analysis.mapper.CmncdMapper;
import kr.co.gaion.scas.analysis.mapper.DtctsdaMapper;
import kr.co.gaion.scas.analysis.mapper.SdaDataMapper;
import kr.co.gaion.scas.analysis.mapper.SdaDataWithDtctsdaMapper;
import kr.co.gaion.scas.analysis.mapper.SdaMapper;
import kr.co.gaion.scas.analysis.mapper.UsersnsrMapper;
import kr.co.gaion.scas.analysis.model.Cmncd;
import kr.co.gaion.scas.analysis.model.Dtctsda;
import kr.co.gaion.scas.analysis.model.Sda;
import kr.co.gaion.scas.analysis.model.SdaData;
import kr.co.gaion.scas.analysis.model.SdaDataWithDtctsda;
import kr.co.gaion.scas.analysis.model.UserSnsr;

@Service
public class VehicleStaticsService {

	@Autowired
	SdaMapper sdaMapper;
	
	@Autowired
	SdaDataMapper sdaDataMapper;
	
	@Autowired
	CmncdMapper cmncdMapper;
	
	@Autowired
	UsersnsrMapper usersnsrMapper;
	
	@Autowired
	DtctsdaMapper dtctsdaMapper;
	
	@Autowired
	SdaDataWithDtctsdaMapper sdadatawithdtctsdaMapper;
	
	public Sda getEachInfo(String id) {
		
		Sda sda = sdaMapper.getEachInfo(id);
		System.out.println(id);
		return sda;
	}
	
	public List<Sda> getAllVehicleInfo(){
		List<Sda> sda = sdaMapper.getAllVehicleInfo();
		return sda;
	}
	
	public List<SdaData> getFileWithId(String id){
		String tableName = id + "_periodic";
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("tableName", tableName);
		
		List<SdaData> sdadata = sdaDataMapper.getFileWithId(param);
		return sdadata;
	}
	
	public List<Cmncd> getForBtn1(){
		List<Cmncd> cmncd = cmncdMapper.getForBtn1();
		return cmncd;
	}
	
	public List<Cmncd> getForBtn2(){
		List<Cmncd> cmncd = cmncdMapper.getForBtn2();
		return cmncd;
	}
	
	public List<SdaData> getChartData(List param1, List param2, List param3){

		String id = param1.get(0).toString();
		String tableName = id+"_periodic";
		String fileName = param1.get(1).toString();
		String nummeric = param2.get(0).toString();
		String categoric = param3.get(0).toString();

		String setColumns = new SdaData().toStringForChart(param2, param3);
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("id", id);
		params.put("tableName", tableName);
		params.put("fileName", fileName);
		params.put("setColumns", setColumns);
		System.out.println(params);
		
		List<SdaData> sdaData = sdaDataMapper.getOneChartData(params);
		
		return sdaData;
	}
	
	public List<UserSnsr> getBookmark(String userid, String grpid){
		HashMap <String, String> param = new HashMap<String, String>();
		param.put("userid", userid);
		param.put("grpid", grpid);
		System.out.println(param);
		List<UserSnsr> userSnsr = usersnsrMapper.getBookmark(param);
		return userSnsr;
	}
	
	public void insertBookmark(List<UserSnsr> param) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("grpid", param.get(0).getGrpid().toString());
		params.put("userid", param.get(0).getUserid().toString());
		
		List<UserSnsr> userSnsr = usersnsrMapper.getBookmark(params);
		usersnsrMapper.deleteBookmark(params);
		params.clear();
		
		for(int i = 0; i < param.size(); i++) {
			params.put("grpid", param.get(i).getGrpid().toString());
			params.put("userid", param.get(i).getUserid().toString());
			params.put("snsrid", param.get(i).getSnsrid().toString());
			usersnsrMapper.insertBookmark(params);
		}
	}
	
	public List<SdaDataWithDtctsda> getDtctsdaData(List param1, List param2, List param3){
		String sdaid = param1.get(0).toString();
		String tablenm = sdaid+"_periodic";
		String filenm = param1.get(1).toString();
		
		
		List<String> sensorGrp = new ArrayList<String>();	//모든 센서데이터 리스트 ( 수치 + 범주 ) 
		sensorGrp.addAll(param2);
		sensorGrp.addAll(param3);
		System.out.println("sensorGrp ===? " + sensorGrp);
		
		List<SdaDataWithDtctsda> totalSdaData = new ArrayList<SdaDataWithDtctsda>();	//센서에 해당하는 토탈 이상치데이터 (넘겨줄데이터)
		
		HashMap<String, String> dtctSdaParam = new HashMap<String, String>();	//이상치테이블 데이터를 가져올때, 파라미터
		
		List<Dtctsda> dtctSdaTotResult = new ArrayList<Dtctsda>();
		
		for(int i = 0; i < sensorGrp.size(); i++) {
			String sensorid = sensorGrp.get(i).toString();
			dtctSdaParam.put("fileNm", filenm);
			dtctSdaParam.put("sdaid", sdaid);
			dtctSdaParam.put("sensorid", sensorid);
			List<Dtctsda> dtctSdaResult = dtctsdaMapper.getDtctsda(dtctSdaParam);
			
			if(dtctSdaResult.size() > 0) {
				HashMap<String, String> sdaDataParam = new HashMap<String, String>();
				String snsrid = "a." + sensorid;
				sdaDataParam.put("tableNm", tablenm);
				sdaDataParam.put("fileNm", filenm);
				sdaDataParam.put("snsrid", snsrid);
				List<SdaDataWithDtctsda> sdaDataResult = sdadatawithdtctsdaMapper.getDataWithDtctsda(sdaDataParam);
				totalSdaData.addAll(sdaDataResult);
			
			} else {
				continue;
			}
			dtctSdaParam.clear();
		}		
		System.out.println(totalSdaData);
		return totalSdaData;
	}
}
