package com.chinobot.cityle.ddjk.controller;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.ddjk.entity.RobotFlight;
import com.chinobot.cityle.ddjk.service.IEquipmentOnlineInfoService;
import com.chinobot.cityle.ddjk.service.IRobotFlightService;
import com.chinobot.cityle.ddjk.service.ITaskEquipmentDispatchService;
import com.chinobot.cityle.ddjk.service.ITaskXcService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-03-16
 */
@Api(tags= {"运行设备信息 -N(未使用)"})
@RestController
@RequestMapping("/api/ddjk")
public class EquipmentOnlineInfoController extends BaseController {
    @Autowired
    private IEquipmentOnlineInfoService equipmentOnlineInfoService;
    @Autowired
    private ITaskEquipmentDispatchService taskEquipmentDispatchService;
    @Autowired
    private ITaskXcService taskXcService;
    @Autowired
    private IRobotFlightService robotFlightService;
    
	public static Map<String, Object> history = new ConcurrentHashMap<String, Object>();
	
    /**
     * 测试
     * @param page
     * @param viewName
     * @param status
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/getTestList", method = RequestMethod.GET)
    public Result getTestList(Page page,String viewName,String status,Date start,Date end){
        equipmentOnlineInfoService.page(page);
        return ResultFactory.success(page);
    }

    /**
     * 任务列表
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/getTaskList", method = RequestMethod.GET)
    public Result getTaskList(Page page, @RequestParam Map<String,Object> param){
        equipmentOnlineInfoService.getTaskList(page,param);
        return ResultFactory.success(page);
    }
    /**
     * 无人机调度列表
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/getUavList", method = RequestMethod.GET)
    public Result getUavList(Page page, @RequestParam Map<String,Object> param){
        equipmentOnlineInfoService.getUavList(page,param);
        return ResultFactory.success(page);
    }
    /**
     * 无人机调度列表(任务调度)
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/getUavListForTask", method = RequestMethod.GET)
    public Result getUavListForTask(Page page, @RequestParam Map<String,Object> param){
        if(CommonUtils.isNotEmpty((String) param.get("uavids"))){
            param.put("uids",((String) param.get("uavids")).split(","));
        }
        equipmentOnlineInfoService.getUavListForTask(page,param);
        return ResultFactory.success(page);
    }

    /**
     * 获取已分配的无人机Id集合
     * @param param
     * @return
     */
    @RequestMapping(value = "/getUavIdtList", method = RequestMethod.GET)
    public Result getUavIdtList(@RequestParam Map<String,Object> param){
        return ResultFactory.success(equipmentOnlineInfoService.getUavIdtList(param));
    }

    /**
     * 任务调度
     * @param taskId
     * @param uavValue
     * @return
     */
    @RequestMapping(value = "/doDispatch", method = RequestMethod.GET)
    public Result doDispatch(String taskId,String uavValue){
        String[] split = uavValue.split(",");
        taskEquipmentDispatchService.doDispatch(taskId,split);
        return ResultFactory.success();
    }

    /**
     * 不予调度
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/doCancel", method = RequestMethod.GET)
    public Result doCancel(String taskId){
        taskEquipmentDispatchService.doCancel(taskId);
        return ResultFactory.success();
    }

    /**
     * 监控页面获取场景列表
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/getSceneListForMonitor", method = RequestMethod.GET)
    public Result getSceneListForMonitor(Page page, @RequestParam Map<String,Object> param){

        equipmentOnlineInfoService.getSceneListForMonitor(page,param);
        return ResultFactory.success(page);
    }

    /**
     * 监控页面获取无人机列表
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/getUavListForMonitor", method = RequestMethod.GET)
    public Result getUavListForMonitor(Page page, @RequestParam Map<String,Object> param){
        equipmentOnlineInfoService.getUavListForMonitor(page,param);
        return ResultFactory.success(page);
    }

    /**
     * 监控页面任务完成情况
     * @param param
     * @return
     */
    @RequestMapping(value = "/getTaskCountForMonitor", method = RequestMethod.GET)
    public Result getTaskCountForMonitor(@RequestParam Map<String,Object> param){

        return ResultFactory.success(equipmentOnlineInfoService.getTaskCountForMonitor(param));
    }

    /**
     * 获取路径的点集合
     * @param param
     * @return
     */
    @RequestMapping(value = "/getRoad", method = RequestMethod.GET)
    public Result getRoad(@RequestParam Map<String,Object> param){
        param.put("time",new Date());
        return ResultFactory.success(equipmentOnlineInfoService.getRoad(param));
    }

    /**
     * 新增任务调度
     * @param xcTime
     * @param sid
     * @param uids
     * @return
     */
    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    public Result addTask(String xcTime,String sid,String uids){
        if(CommonUtils.isNotEmpty(sid) && CommonUtils.isNotEmpty(sid) && CommonUtils.isNotEmpty(uids)){
            equipmentOnlineInfoService.addTask(LocalDate.parse(xcTime),sid,uids.split(","));
            return ResultFactory.success();
        }
        return ResultFactory.error();
    }

    /**
     * 场景获取任务列表
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/getTaskListForScene", method = RequestMethod.GET)
    public Result getTaskListForScene(Page page, @RequestParam Map<String,Object> param){
        equipmentOnlineInfoService.getTaskListForScene(page,param);
        return ResultFactory.success(page);
    }
    /**
     * 日志获取场景列表
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/getSceneForJournal", method = RequestMethod.GET)
    public Result getSceneForJournal(Page page, @RequestParam Map<String,Object> param){
        taskXcService.getSceneForJournal(page,param);
        return ResultFactory.success(page);
    }

    /**
     * 日志页面获取
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "/getTaskForJournal", method = RequestMethod.GET)
    public Result getTaskForJournal(Page page, @RequestParam Map<String,Object> param){
        taskXcService.getTaskForJournal(page,param);
        return ResultFactory.success(page);
    }

    /**
     * 获取巡查历史（飞行轨迹）
     * @param
     * @return
     * @author shizt  
     * @date 2019年4月26日
     * @company chinobot
     */
//    @RequestMapping(value = "/getRobotFlight", method = RequestMethod.GET)
//    public void getRobotFlight(@RequestParam Map<String, String> param) {    	
//    	QueryWrapper<RobotFlight> queryWrapper = new QueryWrapper<>();
//    	if(CommonUtils.isNotEmpty(param.get("sceneId"))) {
//    		queryWrapper.eq("scene_uuid", param.get("sceneId"));
//    	}
//    	String taskUuid = param.get("taskId");
//    	if(CommonUtils.isNotEmpty(taskUuid)) {
//    		queryWrapper.eq("task_uuid", param.get("taskId"));
//    	}
//    	queryWrapper.orderByAsc("operate_time");
//    	List<RobotFlight> list = robotFlightService.list(queryWrapper);
//		for (String key : history.keySet()) {	//停止以前的消息推送
//			history.put(key, 0);
//		}
//		history.put(taskUuid, 1);
//		new KafkaPushThread(taskUuid, list).start();
//    }
    /**
     * 获取楼栋边界及楼层用于绘制3d
     * @return
     */
    @RequestMapping(value = "/getSceneShape", method = RequestMethod.GET)
    public Result getSceneShape(){
        return ResultFactory.success(equipmentOnlineInfoService.getSceneShape());
    }
}
