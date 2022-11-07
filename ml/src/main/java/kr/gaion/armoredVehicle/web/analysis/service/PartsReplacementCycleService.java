package kr.gaion.armoredVehicle.web.analysis.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.gaion.armoredVehicle.web.analysis.mapper.*;
import kr.gaion.armoredVehicle.web.analysis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartsReplacementCycleService {

	@Autowired
	CmpntsrplcMapper cmpntsrplcMapper;

	@Autowired
	CmncdMapper cmncdMapper;

	@Autowired
	SdaMapper sdaMapper;

	@Autowired
	TreeInfoMapper treeinfoMapper;

	@Autowired
	PrinfoMapper prinfoMapper;

	@Autowired
	CmpntsplcDataMapper cmpntsrplcDataMapper;

	@Autowired
	CmpntsrplcinfoMapper cmpntsrplcinfoMapper;

	@Autowired
	CmpntsrplcHistryMapper cmpntsrplcHistryMapper;
	
	public List<CmpntsrplcResponse> findCmpntsrplcList(){
		List<CmpntsrplcResponse> cmpntsrplcList = cmpntsrplcMapper.findCmpntsrplc();
		
		return cmpntsrplcList;
	}
	
	public void updateCmpntsrplc(List<Cmpntsrplc> data) {
		
		for(Cmpntsrplc c : data) {
			cmpntsrplcMapper.updateCmpntsrplc(c);
		}
	}

	public List<Cmncd> getDivsList() {
		String code = "B";
		return cmncdMapper.findListByCode(code);
	}

	public List<TreeInfo> getBnList(String treeinfocode) {

		int treeinfoid = treeinfoMapper.findTreeInfoIdByCode(treeinfocode);
		List<TreeInfo> treeList = treeinfoMapper.findHeader(treeinfoid);
		String code = "C";
		return treeList;
	}

	public List<Sda> getVnList(String brgdbncode) {
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("brgdbncode",brgdbncode);
		List<Sda> sdaid = sdaMapper.findSda(search);
		return sdaid;
	}

	public List<Cmncd> getCmncdList(String data) {
		List<Cmncd> cmncd = cmncdMapper.findPartData();
		return cmncd;
	}

	public void insertData(CmpntsrplcData add) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date RPLC = add.getRplcdate();
		String srplc = null;
		Date resultDate = null;

		try {
			srplc = simpleDateFormat.format(RPLC);
			resultDate = simpleDateFormat.parse(srplc);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		add.setRplcdate(resultDate);
		cmpntsrplcDataMapper.insertData(add);
	}

	public List<Prinfo> getSearchResult(PrinfoRequest prinfo) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date start = prinfo.getStartDate();
		Date startResult = null;

		Date end = prinfo.getEndDate();
		Date endResult = null;

		String startst = null;
		String endst = null;

		try {
			startst = simpleDateFormat.format(start);
			startResult = simpleDateFormat.parse(startst);

			endst = simpleDateFormat.format(end);
			endResult = simpleDateFormat.parse(endst);

			System.out.println(startResult);

			System.out.println(endResult);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		prinfo.setStartDate(startResult);
		prinfo.setEndDate(endResult);
		return prinfoMapper.findSearchData(prinfo);
	}

	public List<CmpntsrplcInfo> search(CmpntsrplcInfo info) {
		List<CmpntsrplcInfo> searchList = cmpntsrplcinfoMapper.find(info);
		return searchList;
	}

	public List<CmpntsrplcHistry> getHistory(CmpntsrplcHistry param){
		return cmpntsrplcHistryMapper.find(param);
	}
}
