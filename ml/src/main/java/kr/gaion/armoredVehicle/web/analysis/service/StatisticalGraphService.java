package kr.gaion.armoredVehicle.web.analysis.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.analysis.mapper.SdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.StatsdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.TreeInfoMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Sda;
import kr.gaion.armoredVehicle.web.analysis.model.Statsda;
import kr.gaion.armoredVehicle.web.analysis.model.StatsdaGroup;
import kr.gaion.armoredVehicle.web.analysis.model.TreeInfo;

@Service
public class StatisticalGraphService {

	@Autowired
	StatsdaMapper statsdaMapper;

	@Autowired
	SdaMapper sdaMapper;

	@Autowired
	TreeInfoMapper treeInfoMapper;

	public JSONObject findGraph(String level, String url, Date date) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String operdate = dateFormat.format(date);

		JSONObject json = new JSONObject();

		JSONArray graphNode = new JSONArray();
		List<Sda> sdaList = new ArrayList<Sda>();
		List<String> sdaidList = new ArrayList<String>();

		TreeInfo treeInfo = treeInfoMapper.findFirstHeader(Integer.parseInt(url));
		JSONObject avgspd = new JSONObject();
		JSONObject engnnldnrate = new JSONObject();
		JSONObject mvmtdstc = new JSONObject();
		JSONObject mvmttime = new JSONObject();
		List<TreeInfo> treeInfoList = treeInfoMapper.findHeader(Integer.parseInt(url));
		if(level.equals("0")) {
			List<StatsdaGroup> statGroupData = statsdaMapper.findStatsdaByGroupDivs(operdate);
			if(!statGroupData.isEmpty()) {
				for(StatsdaGroup s : statGroupData) {
					avgspd.put(s.getGroupName(),s.getAvgspd());
					engnnldnrate.put(s.getGroupName(), s.getEngnnldnrate());
					mvmtdstc.put(s.getGroupName(), s.getMvmtdstc());
					mvmttime.put(s.getGroupName(), s.getMvmttime());
				}
			}else {
				for(TreeInfo ti : treeInfoList) {
					Sda sdaGroupData = sdaMapper.findByDivsCodeGroup(ti.getTrinfocode());
					if(sdaGroupData != null) {
						avgspd.put(sdaGroupData.getDivs(),0);
						engnnldnrate.put(sdaGroupData.getDivs(), 0);
						mvmtdstc.put(sdaGroupData.getDivs(), 0);
						mvmttime.put(sdaGroupData.getDivs(), 0);
					}
				}
			}
		}else if(level.equals("1")) {
			List<StatsdaGroup> statGroupData = statsdaMapper.findStatsdaByGroupBN(operdate,treeInfo.getTrinfocode());
			if(!statGroupData.isEmpty()) {
				for(StatsdaGroup s : statGroupData) {
					avgspd.put(s.getGroupName(),s.getAvgspd());
					engnnldnrate.put(s.getGroupName(), s.getEngnnldnrate());
					mvmtdstc.put(s.getGroupName(), s.getMvmtdstc());
					mvmttime.put(s.getGroupName(), s.getMvmttime());
				}
			}else {
				for(TreeInfo ti : treeInfoList) {
					Sda sdaGroupData = sdaMapper.findByBrgdbnCodeGroup(ti.getTrinfocode());
					if(sdaGroupData != null) {
						avgspd.put(sdaGroupData.getBn(),0);
						engnnldnrate.put(sdaGroupData.getBn(), 0);
						mvmtdstc.put(sdaGroupData.getBn(), 0);
						mvmttime.put(sdaGroupData.getBn(), 0);
					}
				}
			}
		}else if(level.equals("2")) {
			Map<String, Object> search = new HashMap<String, Object>();
			search.put("brgdbncode", treeInfo.getTrinfocode());
			sdaList = sdaMapper.findSda(search);
			for(Sda s : sdaList) {
				sdaidList.add(s.getSdaid());
			}

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sdaidList",sdaidList);
			param.put("operdate", operdate);
			List<Statsda> statsdaList = statsdaMapper.findStatsdaByListSdaid(param);

			if(!statsdaList.isEmpty()) {
				for(Statsda s : statsdaList) {
					Map<String, Object> idSearch = new HashMap<String, Object>();
					idSearch.put("sdaid", s.getSdaid());
					List<Sda> getSdaList = sdaMapper.findSda(idSearch);
					Sda sda = getSdaList.get(0);
					avgspd.put(sda.getSdanm(),s.getAvgspd());
					engnnldnrate.put(sda.getSdanm(), s.getEngnnldnrate());
					mvmtdstc.put(sda.getSdanm(), s.getMvmtdstc());
					mvmttime.put(sda.getSdanm(), s.getMvmttime());
				}
			}else {
				for(String sdaid : sdaidList) {
					Map<String, Object> idSearch = new HashMap<String, Object>();
					idSearch.put("sdaid", sdaid);
					List<Sda> getSdaList = sdaMapper.findSda(idSearch);
					Sda sda = getSdaList.get(0);
					avgspd.put(sda.getSdanm(),0);
					engnnldnrate.put(sda.getSdanm(), 0);
					mvmtdstc.put(sda.getSdanm(),0);
					mvmttime.put(sda.getSdanm(),0);
				}
			}
		}


		json.put("avgspd", avgspd);
		json.put("engnnldnrate", engnnldnrate);
		json.put("mvmtdstc", mvmtdstc);
		json.put("mvmttime", mvmttime);

		return json;
	}
}
