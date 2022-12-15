package kr.gaion.armoredVehicle.web.analysis.mapper;

import kr.gaion.armoredVehicle.web.analysis.model.Dashboard;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
@Mapper
public interface DashboardMapper {

    public Dashboard findDashboardInfo(HashMap<String, Object> param);
    public List<String> findAbnormalVehicle(HashMap<String, Object> param);

}
