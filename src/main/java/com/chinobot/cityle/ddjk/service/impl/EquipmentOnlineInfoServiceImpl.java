package com.chinobot.cityle.ddjk.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Uav;
import com.chinobot.cityle.base.mapper.UavMapper;
import com.chinobot.cityle.ddjk.entity.EquipmentOnlineInfo;
import com.chinobot.cityle.ddjk.entity.TaskEquipmentDispatch;
import com.chinobot.cityle.ddjk.entity.TaskXc;
import com.chinobot.cityle.ddjk.mapper.EquipmentOnlineInfoMapper;
import com.chinobot.cityle.ddjk.mapper.TaskEquipmentDispatchMapper;
import com.chinobot.cityle.ddjk.mapper.TaskXcMapper;
import com.chinobot.cityle.ddjk.service.IEquipmentOnlineInfoService;
import com.chinobot.cityle.ddjk.service.ITaskEquipmentDispatchService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.TaskCode;
import com.chinobot.framework.web.service.impl.BaseService;

import com.chinobot.common.constant.GlobalConstant;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-03-16
 */
@Service
public class EquipmentOnlineInfoServiceImpl extends BaseService<EquipmentOnlineInfoMapper, EquipmentOnlineInfo> implements IEquipmentOnlineInfoService {
    @Autowired
    private EquipmentOnlineInfoMapper equipmentOnlineInfoMapper;
    @Autowired
    private UavMapper uavMapper;
    @Autowired
    private TaskXcMapper taskXcMapper;
    @Autowired
    private TaskEquipmentDispatchMapper taskEquipmentDispatchMapper;
    @Autowired
    private ITaskEquipmentDispatchService taskEquipmentDispatchService;

    @Override
    public IPage<Map> getTaskList(Page page, Map param){
    	if(param.get("task_status")!=null && param.get("task_status")!="" ) {
        	String str = (String) param.get("task_status");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("task_status", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("task_status", split);
			}
        }
        if(param.get("sceneType")!=null && param.get("sceneType")!="" ) {
        	String str = (String) param.get("sceneType");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("sceneType", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("sceneType", split);
			}
        }
    	
        return equipmentOnlineInfoMapper.getTaskList(page,param);
    }
    @Override
    public IPage<Map> getUavList(Page page, Map param){
        //先查询
        IPage<Map> uavList = equipmentOnlineInfoMapper.getUavList(page, param);
        //判断结果是否为空
        if(uavList.getRecords() != null && uavList.getRecords().size()>0){
            return uavList;
        }
        //判断是否查询今天的调度信息
        if(CommonUtils.getDateyyyyMMdd().equals(param.get("date"))){
            //将所有无人机的历史最新调度信息复制一遍到今天的调度信息
            equipmentOnlineInfoMapper.insertUavDispatch();
            uavList = equipmentOnlineInfoMapper.getUavList(page, param);
            return uavList;
        }
        return null;
    }
    @Override
    public IPage<Map> getUavListForTask(Page page, Map param){
        return equipmentOnlineInfoMapper.getUavListForTask(page,param);
    }

    /**
     * 获取已分配的无人机Id集合
     * @param param
     * @return
     */
    @Override
    public List<String> getUavIdtList(Map param){
        return equipmentOnlineInfoMapper.getUavIdtList(param);
    }

    @Override
    public IPage<Map> getSceneListForMonitor(Page page, Map param){
        IPage<Map> pageResult = equipmentOnlineInfoMapper.getSceneListForMonitor(page,param);
        for(Map map : pageResult.getRecords()){
            List taskList = new ArrayList();//任务信息
            //List uavList = new ArrayList();//设备信息
            List warningList = null;//预警信息
            String taskIds = (String) map.get("task_ids");
            // 查询预警信息
            Map paramIn = new HashMap();
            paramIn.put("scene_id",map.get("uuid"));
            warningList = equipmentOnlineInfoMapper.getWarningListForMonitor(paramIn);
            if(warningList != null && warningList.size()>0){
                map.put("isWarning","1");
                map.put("warningList",warningList);
            }else{
                map.put("isWarning","0");
            }
            //String uavIds = (String) map.get("uav_ids");
            for(String taskId : taskIds.split(",")){
                QueryWrapper taskWrapper = new QueryWrapper();
                taskWrapper.eq("uuid",taskId);
                Map taskXc = (Map) taskXcMapper.selectMaps(taskWrapper).get(0);
                //设备
                QueryWrapper<TaskEquipmentDispatch> queryWrapper = new QueryWrapper<TaskEquipmentDispatch>();
                Map eqMap = new HashMap();
                eqMap.put("data_status","1");
                eqMap.put("task_id",taskId);
                queryWrapper.allEq(eqMap);
                List<TaskEquipmentDispatch> uavdList = taskEquipmentDispatchMapper.selectList(queryWrapper);
                List<Uav> uavList = new ArrayList<Uav>();
                for(TaskEquipmentDispatch d : uavdList){
                    uavList.add((Uav) uavMapper.selectById(d.getEquipmentId()));
                }
                taskXc.put("uavList",uavList);
                //人员
                taskXc.put("personList",equipmentOnlineInfoMapper.getTaskUser(taskId));
                //预警时间
                paramIn.put("task_id",taskId);
                List<Map> taskWarningList = equipmentOnlineInfoMapper.getWarningListForMonitor(paramIn);
                if(taskWarningList != null && taskWarningList.size()>0){
                    taskXc.put("isWarning",true);

                    taskXc.put("warning_time", taskWarningList.get(0).get("warning_time"));

                    taskXc.put("warningList",taskWarningList);
                }
                taskList.add(taskXc);
            }
//            for(String uavId : uavIds.split(",")){
//                Uav uav = (Uav) uavMapper.selectById(uavId);
//                uavList.add(uav);
//            }

            map.put("taskList",taskList);
            //map.put("uavList",uavList);
        }

        return pageResult;
    }

    @Override
    public IPage<Map> getUavListForMonitor(Page page, Map param){
        IPage<Map> pageResult = equipmentOnlineInfoMapper.getUavListForMonitor(page,param);
        for(Map map : pageResult.getRecords()){
            //无人机分配的任务
            Map paramIn = new HashMap();
            paramIn.put("xc_time",new Date());
            paramIn.put("e_id",map.get("uuid"));
            map.put("taskList",equipmentOnlineInfoMapper.getTaskListForEquipment(paramIn));
            map.put("personList",equipmentOnlineInfoMapper.getEquipmentPerson(map.get("uuid").toString()));
        }

        return pageResult;
    }



    @Override
    public Map getTaskCountForMonitor(Map param){
        String[] arry = {"4"};
        param.put("status_list",arry);
        Integer successCount = equipmentOnlineInfoMapper.getTaskCountByForMonitor(param);
        String[] arryAll = {"2","3","4","6"};
        param.put("status_list",arryAll);
        Integer allCount = equipmentOnlineInfoMapper.getTaskCountByForMonitor(param);
        Map result = new HashMap();
        result.put("successCount",successCount);
        result.put("allCount",allCount);
        return result;
    }
    @Override
    public List<String[]> getRoad(Map param){
        List<String> list = null;
        if("uav".equals(param.get("type"))){
            list = equipmentOnlineInfoMapper.getUavRoad(param);
        }
        if("task".equals(param.get("type"))){
            list = equipmentOnlineInfoMapper.getTaskRoad(param);
        }

        List<String[]> road = new ArrayList<String[]>();
        for(String s : list){

            road.add(s.split(","));
        }
        return road;
    }
    @Override
    public void addTask(LocalDate xcTime,String sid,String[] uids){
        //新增一个任务
        TaskXc task = new TaskXc();
        task.setRefSlience(sid);
        task.setXcTime(xcTime);
        task.setNormalPro("3");
        //任务编号
        task.setTaskCode(TaskCode.getTaskCode());
        taskXcMapper.insert(task);
        //新增调度记录
        taskEquipmentDispatchService.doDispatch(task.getUuid(),uids);
    }
    @Override
    public IPage<Map> getTaskListForScene(Page page,Map param){
    	IPage<Map> iPage = null;
    	if(param.containsKey("equipmentId") && !CommonUtils.isEmpty(param.get("equipmentId").toString())) {
    		iPage = taskXcMapper.getTaskXcListByEquipment(page, param);
    	}else {
    		QueryWrapper wrapper = new QueryWrapper();
    		wrapper.eq("ref_slience",(String)param.get("sid"));
    		wrapper.eq("data_status",'1');
    		wrapper.orderByDesc("xc_time");
    		iPage = taskXcMapper.selectMapsPage(page, wrapper);
    	}
        if(iPage.getRecords()!=null){
            for(Map map : iPage.getRecords()){
                List<Map> points = equipmentOnlineInfoMapper.getTaskPoints((String) map.get("uuid"));
                if(points != null && points.size()>0) {
                    Map formatXcData = formatXcData(points);
                    map.put("points", formatXcData.get("points"));
                    map.put("is_warning", formatXcData.get("is_warning"));
                }
            }
        }
        return iPage;
    }
    /**
     * 组装任务巡查指标数据
     * @param source
     * @return
     */
    public Map formatXcData(List<Map> source){
        Map sMap = new HashMap();
        sMap.put("is_warning","0");
        List<Map> result = new ArrayList<Map>();
        Map<String,Map> map = new HashMap<String,Map>();
        for(Map m : source){
            String tname = (String) m.get("tname");
            Map task = map.get(tname);
            if(task == null){
                task = new HashMap();
                task.put("tname",tname);
                task.put("content",m.get("content"));
                task.put("tindex",m.get("tindex"));
                task.put("tvalue",m.get("tvalue"));
                task.put("dname",m.get("dname"));
                task.put("did",m.get("did"));
                task.put("level",m.get("level"));
                task.put("rounds_uuid",m.get("rounds_uuid"));
                task.put("is_warning","0");
                task.put("warn_content","");
                task.put("words","");
            }
            //是否预警
            if(((String)m.get("is_warning")).contains("1")){
                task.put("is_warning","1");
                sMap.put("is_warning","1");
            }
            //预警内容
            String warnContents = (String)m.get("warn_content");
            if(CommonUtils.isNotEmpty(warnContents)){
                String w = (String) task.get("warn_content");
                for(String warnContent : warnContents.split("##")){
                    w += warnContent + "  ";
                }
                task.put("warn_content",w);
            }
            String type = (String) m.get("trail_type");
            //视频
            if("1".equals(type)){
                List<String> ids = new ArrayList<String>();
                for(String s : ((String) m.get("fileid")).split(",")){
                    ids.add(GlobalConstant.IO_READ_IMAGE_PREFIX+s);
                }
                task.put("vedio",ids);
            }
            //图片
            if("2".equals(type)){
                List<String> ids = new ArrayList<String>();
                for(String s : ((String) m.get("fileid")).split(",")){
                    ids.add(GlobalConstant.IO_READ_IMAGE_PREFIX+s);
                }
                task.put("img",ids);
            }
            //文字说明
            String words = (String)m.get("words");
            if(CommonUtils.isNotEmpty(words)){
                String w = (String) task.get("words");
                for(String word : words.split("##")){
                    w += word + "  ";
                }
                task.put("words",w);
            }
            map.put(tname,task);
        }


        for(String key : map.keySet()){
            result.add(map.get(key));
        }
        sMap.put("points",result);
        return sMap;
    }
	@Override
	public List<Map> getUavTaskFinishPercent(Map param) {
		
		return equipmentOnlineInfoMapper.getUavTaskFinishPercent(param);
	}
    @Override
	public Map getSceneShape(){
        Map map = new HashMap();
        map.put("type","FeatureCollection");
        Map crs = new HashMap();
        Map crsProperties = new HashMap();
        crsProperties.put("name","urn:ogc:def:crs:OGC:1.3:CRS84");
        crs.put("type","name");
        crs.put("properties",crsProperties);
        map.put("crs",crs);
        List<Map> sceneShape = equipmentOnlineInfoMapper.getSceneShape();
        List<Map> features = new ArrayList<Map>();
        for(Map m : sceneShape){
            Map feature = new HashMap();
            feature.put("type","Feature");
            Map geometry = new HashMap();
            geometry.put("type","MultiPolygon");
            String polyline = (String) m.get("polyline");
            List cod = new ArrayList();
            for(String c : polyline.split(";")){
                cod.add(c.split(","));
            }
            //要是一个环
            cod.add(cod.get(0));

            List polygon = new ArrayList();
            polygon.add(cod);
            List coordinates = new ArrayList();
            coordinates.add(polygon);
            geometry.put("coordinates",coordinates);
            feature.put("geometry",geometry);
            m.remove("polyline");
            feature.put("properties",m);
            features.add(feature);
        }
        map.put("features",features);
        return map;
    }
}
