package kr.gaion.armoredVehicle.web.analysis.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.mapper.CmpntsrplcHistryMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.CmpntsrplcMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.DriverattitdinfoMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.SdaMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Cmpntsrplc;
import kr.gaion.armoredVehicle.web.analysis.model.CmpntsrplcHistry;
import kr.gaion.armoredVehicle.web.analysis.model.Sda;
import kr.gaion.armoredVehicle.web.analysis.model.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PopUpInfoService {

	@Autowired
	CmpntsrplcHistryMapper cmpntsrplcHistryMapper;

	@Autowired
	CmpntsrplcMapper cmpntsrplcMapper;

	@Autowired
	SdaMapper sdaMapper;

	@Autowired
	DriverattitdinfoMapper driverattitdInfoMapper;


	public List<String> getPopUpInfo(String userid){
		List<String> result = new ArrayList<String>();
		List<String> cmpntsrplcInfo = getCmpntsrplcInfo(userid);
		List<String> driverattitdInfo = getDriverattitdInfo(userid);

		result.addAll(cmpntsrplcInfo);
		result.addAll(driverattitdInfo);

		return result;
	}

	public List<String> getCmpntsrplcInfo(String userid) {
		List<String> result = new ArrayList<String>();

		List<CmpntsrplcHistry> cmpntsrplcHistryList = new ArrayList<CmpntsrplcHistry>();

		List<Sda> sdaList = new ArrayList<Sda>();

		List<Cmpntsrplc> cmpntsrplcList = new ArrayList<Cmpntsrplc>();

		Calendar getToday = Calendar.getInstance();
		getToday.setTime(new Date());
		Calendar cmpDate = Calendar.getInstance();

		Map<String, Object> search = new HashMap<String, Object>();
		search.put("userid", userid);

		sdaList = sdaMapper.findSda(search);

		cmpntsrplcList = cmpntsrplcMapper.findCmpntsrplcbyGrid(null);

		for(Sda s : sdaList) {
			for(Cmpntsrplc cmpntsrplc : cmpntsrplcList) {
				HashMap<String, String> param = new HashMap<String,String>();
				param.put("sdaid", s.getSdaid());
				param.put("grid", cmpntsrplc.getGrid());
				CmpntsrplcHistry cmpHistry = cmpntsrplcHistryMapper.findCmpntsrplchistryBySdaidAndGrid(param);
				if(cmpHistry != null) {

					String rplcDate = new SimpleDateFormat("yyyy-MM-dd").format(cmpHistry.getRplcdate());
					try {
						Date date = new SimpleDateFormat("yyyy-MM-dd").parse(rplcDate);
						cmpDate.setTime(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
					long diffDays = diffSec / (24*60*60); //일자수 차이

					Long dDay = Long.parseLong(cmpntsrplc.getPrdval()) - diffDays; //교환 주기까지 남은 일자
					if(dDay < 0) {
						result.add(getCmpntsrplcNotice(s.getSdanm(), cmpntsrplc.getMsg(), dDay.intValue() * (-1), 0));
					}else if(dDay >0 && dDay <= 7) {
						result.add(getCmpntsrplcNotice(s.getSdanm(), cmpntsrplc.getMsg(), dDay.intValue(), 1));
					}else if(dDay == 0) {
						result.add(getCmpntsrplcNotice(s.getSdanm(), cmpntsrplc.getMsg(), dDay.intValue(), 2));
					}
				}
			}
		}
		return result;
	}


	public List<String> getDriverattitdInfo(String userid){
		List<String> result = new ArrayList<String>();

		Calendar  cal  =  Calendar.getInstance();

		SimpleDateFormat  sdf  =  new SimpleDateFormat("yyyy-MM-dd");      // 데이터 출력 형식 지정

		String  toDate  =  sdf.format (cal.getTime());      // 오늘 날짜 변수에 저장

		cal.set (cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)-7);     // 7일전 날짜 Set

		String  lastDate  =  sdf.format (cal.getTime());    // 7일전 날짜 변수에 저장

		Date dToday = null;
		Date dCmpDate = null;
		try {
			dToday = sdf.parse(toDate);
			dCmpDate = sdf.parse(lastDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Sda> sdaList = new ArrayList<Sda>();
		Map<String, Object> uSearch = new HashMap<String, Object>();
		uSearch.put("userid", userid);
		sdaList = sdaMapper.findSda(uSearch);

		for(Sda s : sdaList) {
			SearchRequest search = new SearchRequest();

			search.setSdaid(s.getSdaid());
			search.setStartDate(dCmpDate);
			search.setEndDate(dToday);

			int value = driverattitdInfoMapper.countDriverInfoBySearch(search);

			if(value > 0) {
				String comment = s.getSdanm() + "차량의 운전자 자세교정 정보가 "+ value + "건 확인되었습니다.";
				result.add(comment);
			}
		}

		return result;
	}

	public String getCmpntsrplcNotice(String sdanm, String msg, int dDay, int type) {
		String result = null;

		switch(type) {
			case 0:
				result = "경고 : " + sdanm + "차량의" + msg + "가 " + String.valueOf(dDay) + "일 지났습니다.";
				break;
			case 1:
				result = "주의 : " + sdanm + "차량의" + msg + "가 " + String.valueOf(dDay) + "일 남았습니다.";
				break;
			case 2:
				result = "주의 : " + sdanm + "차량의" + msg + "가 오늘 입니다.";
				break;
			default:
				result = "ERROR";
				break;
		}
		return result;
	}


}
