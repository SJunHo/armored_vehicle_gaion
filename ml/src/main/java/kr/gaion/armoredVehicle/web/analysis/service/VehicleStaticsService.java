package kr.gaion.armoredVehicle.web.analysis.service;

import java.util.*;

import kr.gaion.armoredVehicle.web.analysis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.analysis.mapper.CmncdMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.DtctsdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.SdaDataMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.SdaDataWithDtctsdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.SdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.UsersnsrMapper;


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

	@Value("${periodic.table}")
	private String periodic;

	public Sda getEachInfo(String id) {

		Map<String, Object> search = new HashMap<String, Object>();
		search.put("sdaid", id);
		List<Sda> sdaList = sdaMapper.findSda(search);
		Sda sda = sdaList.get(0);
		return sda;
	}

	public List<Sda> getAllVehicleInfo(){
		List<Sda> sda = sdaMapper.findSda(null);
		return sda;
	}

	public List<SdaData> getFileWithId(String id){
		String tableName = id + periodic;
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
		String tableName = id+periodic;
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

	public List<Cmncd> defaultBookmark(String userid){
		List<UserSnsr> userSnsr = usersnsrMapper.defaultBookmark(userid);	//모든 즐겨찾기 목록
		List<UserSnsr> bookmarkList = new ArrayList<UserSnsr>();	//1에 가까운 즐겨찾기 목록 ex) grpid = 1인 모든 usersnsr
		List<Cmncd> defaultBookmarkList = new ArrayList<Cmncd>();

		if(userSnsr.size() > 0) {
			int lowGrpid = Integer.parseInt(userSnsr.get(0).getGrpid().toString());
			for(int i = 1; i < userSnsr.size(); i++) {
				int afterGrpid = Integer.parseInt(userSnsr.get(i).getGrpid().toString());
				if(lowGrpid > afterGrpid) {
					lowGrpid = afterGrpid;
				}else {
					continue;
				}
			}
			for(UserSnsr each: userSnsr){
				int checkGrpid = Integer.parseInt(each.getGrpid().toString());
				if(lowGrpid == checkGrpid) {
					bookmarkList.add(each);
				}
			}
		}
		for(UserSnsr each : bookmarkList) {
			String code = each.getSnsrid().toString();
			defaultBookmarkList.add(cmncdMapper.getDefaultBookmark(code));

		}


		return defaultBookmarkList;

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
		String tablenm = sdaid+periodic;
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
		MiniComparator comp = new MiniComparator();
		Collections.sort(totalSdaData, comp);
		return totalSdaData;
	}

	public String findRecentFile(String sdaid) {
		String fileName = "null";
		String tablenm = sdaid+periodic;

		Map<String, String> param = new HashMap<String, String>();
		param.put("tablenm", tablenm);
		List<FilenmDttimeForDefaultBookmark> defaultBookmark = sdaDataMapper.findRecentFile(param);
		if(defaultBookmark.size() > 0 ) {
			String recentDate = defaultBookmark.get(0).getDttime().toString();
			String[] strArr = recentDate.split("-| |:");
			recentDate = strArr[0].concat(strArr[1]).concat(strArr[2]).concat(strArr[3]).concat(strArr[4]).concat(strArr[5]).concat(strArr[6]);

			Long rcntDate = Long.parseLong(recentDate);

			for(int i = 1; i < defaultBookmark.size(); i++) {
				String afterDate = defaultBookmark.get(i).getDttime().toString();
				String[] afterArr = afterDate.split("-| |:");
				afterDate = afterArr[0].concat(afterArr[1]).concat(afterArr[2]).concat(afterArr[3]).concat(afterArr[4]).concat(afterArr[5]).concat(afterArr[6]);

				Long aftDate = Long.parseLong(afterDate);
				if(rcntDate > aftDate) {
					continue;
				}else {
					rcntDate = aftDate;
				}
			}

			for(FilenmDttimeForDefaultBookmark each : defaultBookmark) {
				String compareForFindFile = each.getDttime().toString();
				String[] compareForFindFileArr = compareForFindFile.split("-| |:");
				compareForFindFile = compareForFindFileArr[0].concat(compareForFindFileArr[1]).concat(compareForFindFileArr[2]).concat(compareForFindFileArr[3]).concat(compareForFindFileArr[4]).concat(compareForFindFileArr[5]).concat(compareForFindFileArr[6]);

				Long selectedDttime = Long.parseLong(compareForFindFile);
				if(rcntDate.equals(selectedDttime)) {
					fileName = each.getFilenm().toString();
				}
			}
		}
		return fileName;
	}
}

class MiniComparator implements Comparator<SdaDataWithDtctsda>{

	@Override
	public int compare(SdaDataWithDtctsda first, SdaDataWithDtctsda second) {
		// TODO Auto-generated method stub
		double firstValue = Double.parseDouble(first.getTime());
		double secondValue = Double.parseDouble(second.getTime());

		if(firstValue > secondValue) {
			return 1;
		} else if(firstValue < secondValue) {
			return -1;
		} else {
			return 0;
		}
	}

}
