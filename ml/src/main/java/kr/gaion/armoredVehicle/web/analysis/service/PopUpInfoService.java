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

import kr.gaion.armoredVehicle.web.analysis.mapper.*;
import kr.gaion.armoredVehicle.web.analysis.model.*;
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

	@Autowired
	BkdsdaMapper bkdsdaMapper;


	public List<String> getPopUpInfo(String userid){
		List<String> result = new ArrayList<String>();

		Map<String, Object> search = new HashMap<String, Object>();
		List<Sda> sdaList = new ArrayList<Sda>();
		search.put("userid", userid);

		sdaList = sdaMapper.findSda(search);
		List<String> cmpntsrplcInfo = getCmpntsrplcInfo(sdaList);
		List<String> driverattitdInfo = getDriverattitdInfo(sdaList);
		List<String> bkdInfo = getBkdInfo(userid);

		result.addAll(cmpntsrplcInfo);
		result.addAll(driverattitdInfo);
		result.addAll(bkdInfo);

		return result;
	}

	public List<String> getCmpntsrplcInfo(List<Sda> sdaList) {
		List<String> result = new ArrayList<String>();

		List<CmpntsrplcHistry> cmpntsrplcHistryList = new ArrayList<CmpntsrplcHistry>();

		List<Cmpntsrplc> cmpntsrplcList = new ArrayList<Cmpntsrplc>();

		Calendar getToday = Calendar.getInstance();
		getToday.setTime(new Date());
		Calendar cmpDate = Calendar.getInstance();

		cmpntsrplcList = cmpntsrplcMapper.findCmpntsrplcAll(null);

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


	public List<String> getDriverattitdInfo(List<Sda> sdaList){
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

		for(Sda s : sdaList) {
			SearchRequest search = new SearchRequest();

			search.setSdaid(s.getSdaid());
			search.setStartDate(dCmpDate);
			search.setEndDate(dToday);

			int value = driverattitdInfoMapper.countDriverPopUpInfo(search);

			if(value > 0) {
				String comment = s.getSdanm() + "차량의 운전자 자세교정 정보가 "+ value + "건 확인되었습니다.";
				result.add(comment);
			}
		}

		return result;
	}

	public List<String> getBkdInfo(String userid){
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

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userid", userid);
		param.put("endtime", dToday);
		param.put("sttime", dCmpDate);

		List<BkdResponse> bkdMsgList = bkdsdaMapper.findBkdMsg(param);

		if(bkdMsgList != null) {
			for(BkdResponse b : bkdMsgList) {
				result.add(getBkdMsg(b));
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

	public String getBkdMsg(BkdResponse param) {

		String result = null;

		result = param.getSdanm() + "차량의" + param.getGrnm() + "의 고장진단이" + param.getFilenm() +"파일에"
				+ param.getCntgr() + "회 발생했습니다 / (최초)발생시간 : " + param.getSttime();

		return result;
	}

}
