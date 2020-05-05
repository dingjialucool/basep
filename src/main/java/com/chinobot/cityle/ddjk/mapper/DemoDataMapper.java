package com.chinobot.cityle.ddjk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.cityle.ddjk.entity.DemoData;
import com.chinobot.framework.web.mapper.IBaseMapper;

public interface DemoDataMapper extends IBaseMapper<DemoData> {
	
    public List<Map> getDemoDataList(@Param("param") Map param);
    
    public Map getFilePath(@Param("param") Map param);

    public void deleteRobotFlight(@Param("param") Map param);

    public void deleteRobotGather(@Param("param") Map param);
    
    public void deleteRobotTrail(@Param("param") Map param);
    
    public void deleteTaskEquipmentDispatch(@Param("param") Map param);
}
