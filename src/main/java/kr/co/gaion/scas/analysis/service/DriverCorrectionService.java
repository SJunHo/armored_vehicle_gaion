package kr.co.gaion.scas.analysis.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gaion.scas.analysis.mapper.DriverattitdMapper;
import kr.co.gaion.scas.analysis.mapper.SnsrMapper;
import kr.co.gaion.scas.analysis.model.DriverAttitd;
import kr.co.gaion.scas.analysis.model.Snsr;
import kr.co.gaion.scas.analysis.mapper.CmncdMapper;
import kr.co.gaion.scas.analysis.mapper.DriverattitdinfoMapper;
import kr.co.gaion.scas.analysis.mapper.SdaMapper;
import kr.co.gaion.scas.analysis.mapper.TreeInfoMapper;
import kr.co.gaion.scas.analysis.model.Cmncd;
import kr.co.gaion.scas.analysis.model.DriverCorrectInfo;
import kr.co.gaion.scas.analysis.model.Sda;
import kr.co.gaion.scas.analysis.model.SearchRequest;
import kr.co.gaion.scas.analysis.model.TreeInfo;
import kr.co.gaion.scas.utils.Paging;

@Service
public class DriverCorrectionService {

	@Autowired
	DriverattitdMapper driverAttitdMapper;
	
	@Autowired
	SnsrMapper snsrMapper;
	
	@Autowired
	CmncdMapper cmncdMapper;
	
	@Autowired
	SdaMapper sdaMapper;
	
	@Autowired
	TreeInfoMapper treeinfoMapper;
	
	@Autowired
	DriverattitdinfoMapper driverattitdInfoMapper;
	
	public List<Cmncd> getDivsList() {
		String code="B";
		return cmncdMapper.findListByCode(code);
	}

	public List<TreeInfo> getBnList(String treeinfocode) {
		
		int treeinfoid = treeinfoMapper.findTreeInfoIdByCode(treeinfocode);
		List<TreeInfo> treeList = treeinfoMapper.findHeader(treeinfoid);

		return treeList;
	}

	public List<Sda> getSdaList(String brgdbncode) {
	
		List<Sda> sdaid = sdaMapper.findSdaByBrgdbnCode(brgdbncode);
		return sdaid;
	}


	public Map<String,Object> getSearchResult(SearchRequest search){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date start = search.getStartDate();
		Date startResult = null;
		
		Date end = search.getEndDate();
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
		
		search.setStartDate(startResult);
		search.setEndDate(endResult);
		Paging paging = new Paging();
		int page = search.getPage();
		int pageSize = search.getSize();

		paging.setTotalcount(driverattitdInfoMapper.countDriverInfoBySearch(search));
		paging.setPagenum(page-1);
		paging.setContentnum(pageSize);
		paging.setCurrentblock(page);
		paging.setLastblock(paging.getTotalcount());
		
		paging.prevnext(page);
		paging.setStartPage(paging.getCurrentblock());
		paging.setEndPage(paging.getLastblock(), paging.getCurrentblock());
		paging.setTotalPageCount();
		
		search.setPage(paging.getPagenum()*pageSize);
		search.setSize(paging.getContentnum());
		List<DriverCorrectInfo> driverCorrectInfoList = driverattitdInfoMapper.findDriverInfoBySearch(search);
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("driverCorrectInfoList",driverCorrectInfoList);
		map.put("paging", paging);
		return map;
		
	}
	
	public List<DriverAttitd> findDriverAttitdList(){
		return driverAttitdMapper.findDriverattitdAll();
	}
	
	public void updateThreshold(List<DriverAttitd> data) {
		for(DriverAttitd da : data) {
			driverAttitdMapper.updateDriverAttitd(da);			
		}
	}
	
	public List<Snsr> findSnsrList(){
		return snsrMapper.findSnsrList();
	}
}
