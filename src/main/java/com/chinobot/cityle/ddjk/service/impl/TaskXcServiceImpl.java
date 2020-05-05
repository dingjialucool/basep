package com.chinobot.cityle.ddjk.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.ddjk.entity.TaskXc;
import com.chinobot.cityle.ddjk.mapper.EquipmentOnlineInfoMapper;
import com.chinobot.cityle.ddjk.mapper.TaskXcMapper;
import com.chinobot.cityle.ddjk.service.IEquipmentOnlineInfoService;
import com.chinobot.cityle.ddjk.service.ITaskXcService;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-04-02
 */
@Service
public class TaskXcServiceImpl extends BaseService<TaskXcMapper, TaskXc> implements ITaskXcService {
    @Autowired
    private TaskXcMapper taskXcMapper;
    @Autowired
    private EquipmentOnlineInfoMapper equipmentOnlineInfoMapper;
    @Autowired
    private IEquipmentOnlineInfoService equipmentOnlineInfoService;

    @Override
    public IPage<Map> getSceneForJournal(Page page, Map<String, Object> param){
        if(param.get("bus_type")!=null && param.get("bus_type")!="" ) {
        	String str = (String) param.get("bus_type");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("bus_type", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("bus_type", split);
			}
        }
        if(param.get("stype")!=null && param.get("stype")!="" ) {
        	String str = (String) param.get("stype");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("stype", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("stype", split);
			}
        }
        
    	return taskXcMapper.getSceneForJournal(page,param);
    }
    @Override
    public IPage<Map> getTaskForJournal(Page page,Map param){

        IPage<Map> iPage = taskXcMapper.getTaskForJournal(page, param);
        if(iPage.getRecords()!=null){
            for(Map map : iPage.getRecords()){
                List<Map> points = equipmentOnlineInfoMapper.getTaskPoints((String) map.get("uuid"));
                if(points != null && points.size()>0) {
                    Map formatXcData = equipmentOnlineInfoService.formatXcData(points);
                    map.put("points", formatXcData.get("points"));
                    map.put("is_warning", formatXcData.get("is_warning"));
                }
            }
        }
        return iPage;
    }
    
    @Override
    public Map getPatrolCount(Map<String, String> param) {
    	
    	return taskXcMapper.getPatrolCount(param);
    }
    
	@Override
	public List<Map> getAttendanceCount(Map<String, String> param) {
		
		return taskXcMapper.getAttendanceCount(param);
	}
	@Override
	public List<Map> getBusTypeCount(Map<String, String> param) {
		
		return taskXcMapper.getBusTypeCount(param);
	}
	@Override
	public List<Map> getTaskXcList(Map<String, Object> param) {
		
		return taskXcMapper.getTaskXcList(param);
	}
	@Override
	public Map getTaskMonitorCount(Map<String, Object> param) {
		return taskXcMapper.getTaskMonitorCount(param);
	}
	@Override
	public List<LinkedHashMap> getTaskMonitorList(Map<String, Object> param) {
		return taskXcMapper.getTaskMonitorList(param);
	}
	@Override
	public IPage<LinkedHashMap> getTaskMonitorPage(Page page, Map<String, Object> param) {
		return taskXcMapper.getTaskMonitorList(page, param);
	}
}
