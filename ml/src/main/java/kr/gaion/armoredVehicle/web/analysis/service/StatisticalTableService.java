package kr.gaion.armoredVehicle.web.analysis.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.analysis.mapper.BkdsdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.DtctsdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.SdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.StatsdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.TreeInfoMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Sda;
import kr.gaion.armoredVehicle.web.analysis.model.TreeInfo;

@Service
public class StatisticalTableService {

	@Autowired
	StatsdaMapper statsdaMapper;
	
	@Autowired
	SdaMapper sdaMapper;
	
	@Autowired
	TreeInfoMapper treeInfoMapper;
	
	@Autowired
	BkdsdaMapper bkdsdaMapper;
	
	@Autowired
	DtctsdaMapper dtctsdaMapper;
	
	public JSONObject findTable(String level, String url, Date date) {
		
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
		String operdate = dateFormat.format(date);
		
		JSONObject json = new JSONObject();
		
		TreeInfo treeInfo = treeInfoMapper.findFirstHeader(Integer.parseInt(url));
		List<TreeInfo> childs = treeInfoMapper.findHeader(Integer.parseInt(url));
		JSONArray jsonArray = new JSONArray();
		
		if(level.equals("0")) {
			Integer countAll = sdaMapper.countSdaAll();
			int all = 0;
			if(countAll != null) {
				all = countAll;			
			}
			
			int drive = statsdaMapper.countStatssdaAll(operdate);
			
			int outlier = dtctsdaMapper.countAll(operdate);
			
			int broken = bkdsdaMapper.countAll(operdate);
			
			int sumOutlierBroken = 0;
			
			if(outlier > 0 && broken > 0) {
				if(broken > outlier) {
					sumOutlierBroken += broken;
				}else {
					sumOutlierBroken += outlier;
				}
			}else if(outlier > 0 && broken == 0) {
				sumOutlierBroken += outlier;
			}else if(broken > 0 && outlier == 0) {
				sumOutlierBroken += broken;
			}
			JSONObject top = new JSONObject();
			
			top.put("bn", "총괄");
			top.put("allcount", all);
			top.put("ndrive", all-drive);
			top.put("drive", drive);
			top.put("normal", all-sumOutlierBroken);
			top.put("outlier", outlier);
			top.put("broken", broken);
			jsonArray.add(top);
			for(TreeInfo c : childs) {
				JSONObject child = new JSONObject();
				List<Sda> sdaList = sdaMapper.findSdaByDivCode(c.getTrinfocode());
				
				Integer countDiv = sdaMapper.countSdaByDivCode(c.getTrinfocode());
				int cAll = 0;
				if(countDiv != null) {
					cAll = countDiv;
				}
				
				int cDrive = 0;
				int cOutlier = 0;
				int cBroken = 0;
				int cSumOutlierBroken = 0;
				
				for(Sda s : sdaList) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("sdaid", s.getSdaid());
					param.put("operdate", operdate);
					param.put("dttime", operdate);
					int dr = statsdaMapper.countStatssda(param);
					int ol = dtctsdaMapper.countDtctsdaBySdaid(param);
					int bk = bkdsdaMapper.countBkdsdaBySdaid(param);
					
					cDrive += dr;
					cOutlier += ol;
					cBroken += bk;
					if(ol > 0 && bk > 0) {
						cSumOutlierBroken += 1;
					}else if(ol > 0 && bk == 0) {
						cSumOutlierBroken += 1;
					}else if(bk > 0 && ol == 0) {
						cSumOutlierBroken += 1;
					}
				}
				child.put("bn", c.getTrinfoname());
				child.put("allcount", cAll);
				child.put("ndrive", cAll - cDrive);
				child.put("drive", cDrive);
				child.put("normal", cAll-cSumOutlierBroken);
				child.put("outlier", cOutlier);
				child.put("broken", cBroken);
				jsonArray.add(child);
			}
			
		}else if(level.equals("1")) {
			int all = 0;
			Integer countdivcode = sdaMapper.countSdaByDivCode(treeInfo.getTrinfocode());
			if(countdivcode != null) {
				all = countdivcode;
			}
			List<Sda> sdaList = sdaMapper.findSdaByDivCode(treeInfo.getTrinfocode());
			int drive = 0;
			
			int outlier = 0;
			
			int broken = 0;
			
			int sumOutlierBroken = 0;
			for(Sda s : sdaList) {
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("sdaid", s.getSdaid());
				param.put("operdate", operdate);
				param.put("dttime", operdate);
				int dr = statsdaMapper.countStatssda(param);
				int ol = dtctsdaMapper.countDtctsdaBySdaid(param);
				int bk = bkdsdaMapper.countBkdsdaBySdaid(param);
				
				drive += dr;
				outlier += ol;
				broken += bk;
				if(ol > 0 && bk > 0) {
					sumOutlierBroken += 1;
				}else if(ol > 0 && bk == 0) {
					sumOutlierBroken += 1;
				}else if(bk > 0 && ol == 0) {
					sumOutlierBroken += 1;
				}
			}
			
			JSONObject top = new JSONObject();
			
			top.put("bn", "총괄");
			top.put("allcount", all);
			top.put("ndrive", all-drive);
			top.put("drive", drive);
			top.put("normal", all-sumOutlierBroken);
			top.put("outlier", outlier);
			top.put("broken", broken);
			jsonArray.add(top);
			
			for(TreeInfo c : childs) {
				JSONObject child = new JSONObject();
				List<Sda> csdaList = sdaMapper.findSdaByBrgdbnCode(c.getTrinfocode());
				
				int cAll = sdaMapper.countSdaByBrgdbnCode(c.getTrinfocode());
				
				int cDrive = 0;
				int cOutlier = 0;
				int cBroken = 0;
				int cSumOutlierBroken = 0;
				if(!csdaList.isEmpty()) {
					for(Sda s : csdaList) {
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("sdaid", s.getSdaid());
						param.put("operdate", operdate);
						param.put("dttime", operdate);
						int dr = statsdaMapper.countStatssda(param);
						int ol = dtctsdaMapper.countDtctsdaBySdaid(param);
						int bk = bkdsdaMapper.countBkdsdaBySdaid(param);
						
						cDrive += dr;
						cOutlier += ol;
						cBroken += bk;
						if(ol > 0 && bk > 0) {
							cSumOutlierBroken += 1;
						}else if(ol > 0 && bk == 0) {
							cSumOutlierBroken += 1;
						}else if(bk > 0 && ol == 0) {
							cSumOutlierBroken += 1;
						}
					}
				}
				child.put("bn", c.getTrinfoname());
				child.put("allcount", cAll);
				child.put("ndrive", cAll - cDrive);
				child.put("drive", cDrive);
				child.put("normal", cAll-cSumOutlierBroken);
				child.put("outlier", cOutlier);
				child.put("broken", cBroken);
				jsonArray.add(child);
			}
		}else if(level.equals("2")) {
			Integer countbrgdbn = sdaMapper.countSdaByBrgdbnCode(treeInfo.getTrinfocode());
			int all = 0;
			if(countbrgdbn != null) {
				all = countbrgdbn;
			}
			List<Sda> sdaList = sdaMapper.findSdaByBrgdbnCode(treeInfo.getTrinfocode());
			int drive = 0;
			
			int outlier = 0;
			
			int broken = 0;
			int sumOutlierBroken = 0;
			JSONObject state = new JSONObject();
			
			if(!sdaList.isEmpty()) {
				for(Sda s : sdaList) {
					String stateString = "";
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("sdaid", s.getSdaid());
					param.put("operdate", operdate);
					param.put("dttime", operdate);
					int dr = statsdaMapper.countStatssda(param);
					int ol = dtctsdaMapper.countDtctsdaBySdaid(param);
					int bk = bkdsdaMapper.countBkdsdaBySdaid(param);
					
					drive += dr;
					outlier += ol;
					broken += bk;
					if(ol > 0) {
						stateString += "O";
					}else {
						stateString += "N";
					}
					if(bk > 0) {
						stateString += "B";
					}else {
						stateString += "N";
					}
					state.put(s.getSdaid(), stateString);
					if(ol > 0 && bk > 0) {
						sumOutlierBroken += 1;
					}else if(ol > 0 && bk == 0) {
						sumOutlierBroken += 1;
					}else if(bk > 0 && ol == 0) {
						sumOutlierBroken += 1;
					}
				}
			}
			JSONObject top = new JSONObject();
			
			top.put("bn", "총괄");
			top.put("allcount", all);
			top.put("ndrive", all-drive);
			top.put("drive", drive);
			top.put("normal", all-sumOutlierBroken);
			top.put("outlier", outlier);
			top.put("broken", broken);
			jsonArray.add(top);
			jsonArray.add(state);
		}
		
		json.put("table", jsonArray);
		return json;
	}
}
