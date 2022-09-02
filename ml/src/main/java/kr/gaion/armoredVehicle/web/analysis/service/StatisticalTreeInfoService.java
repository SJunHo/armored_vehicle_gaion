package kr.gaion.armoredVehicle.web.analysis.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gaion.armoredVehicle.web.analysis.mapper.SdaMapper;
import kr.gaion.armoredVehicle.web.analysis.mapper.TreeInfoMapper;
import kr.gaion.armoredVehicle.web.analysis.model.Sda;
import kr.gaion.armoredVehicle.web.analysis.model.TreeInfo;

@Service
public class StatisticalTreeInfoService {

	@Autowired
	TreeInfoMapper treeInfoMapper;
	
	@Autowired
	SdaMapper sdaMapper;
	
	public JSONObject findTreeInfo() {

		TreeInfo headInfo = treeInfoMapper.findFirstHeader(1);
		
		List<TreeInfo> oneDepth = treeInfoMapper.findHeader(1);
		
		JSONObject headJson = new JSONObject();
		JSONArray oNodes = new JSONArray();
		for(TreeInfo o : oneDepth) {
			JSONObject oDJson = new JSONObject();
			
			JSONArray tNodes = new JSONArray();
			
			List<TreeInfo> twoDepth = treeInfoMapper.findHeader(o.getTrinfoid());
			
			for(TreeInfo t : twoDepth) {
				String treeInfoCode = t.getTrinfocode();
						
				JSONArray sNodes = new JSONArray();	
				List<Sda> sdaList = new ArrayList<Sda>();

				sdaList = sdaMapper.findSdaByBrgdbnCode(treeInfoCode);
				for(Sda s : sdaList) {
					JSONObject sDJson = new JSONObject();
					sDJson.put("key", s.getSdaid());
					sDJson.put("label", s.getSdanm());
					sDJson.put("url", s.getSdaid());
					sDJson.put("sda", true);
					sNodes.add(sDJson);
				}
				JSONObject tDJson = new JSONObject();
				tDJson.put("nodes", sNodes);
				tDJson.put("key", t.getTrinfoid());
				tDJson.put("label", t.getTrinfoname());
				tDJson.put("url", t.getTrinfoid());
				tNodes.add(tDJson);
			}
			oDJson.put("nodes",tNodes);
			oDJson.put("key", o.getTrinfoid());
			oDJson.put("label", o.getTrinfoname());
			oDJson.put("url", o.getTrinfoid());
			oNodes.add(oDJson);
		}
		headJson.put("key", headInfo.getTrinfoid());
		headJson.put("label", headInfo.getTrinfoname());
		headJson.put("url", headInfo.getTrinfoid());
		headJson.put("nodes", oNodes);
				
		return headJson;
	}
}
