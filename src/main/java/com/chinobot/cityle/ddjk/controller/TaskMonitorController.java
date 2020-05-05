package com.chinobot.cityle.ddjk.controller;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.service.IUavService;
import com.chinobot.cityle.ddjk.service.ITaskEquipmentDispatchService;
import com.chinobot.cityle.ddjk.service.ITaskXcService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.plan.service.IFlyTaskService;

import io.swagger.annotations.Api;

/**
 * <p>
 *  飞行监控 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-04-02
 */
@Api(tags= {"飞行监控接口 -N(未使用)"})
@RestController
@RequestMapping("/api/ddjk/taskMonitor")
public class TaskMonitorController extends BaseController {

	@Autowired
	private IUavService uavService;
	@Autowired
	private ITaskXcService taskXcService;
	@Autowired
	private ITaskEquipmentDispatchService taskEquipmentDispatchService;
	@Autowired
	private IFlyTaskService flyTaskService;
	
	/**
	 * 巡查监控
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年5月22日
	 * @company chinobot
	 */
	@RequestMapping("")
	public Result taskMonitor(Page page, @RequestParam Map<String,Object> param) {
		Map result = new HashMap();
		String type = (String) param.get("listType");
		if("init".equals(type) || "uav".equals(type)) {
			param.put("etype", "1");
			// 无人机列表
			result.put("listData", uavService.getUavList(page, param));
		}
		else if("noCompleted".equals(type)) {
			param.put("completed", "0");
			IPage<Map> pageMap = uavService.getUavTask(page, param);
			for (Map map : pageMap.getRecords()) {
				if(CommonUtils.isObjEmpty(map.get("center"))) {
					map.put("center", getCenter(map.get("polyline").toString()));
				}
			}
			// 未完成任务
			result.put("listData", pageMap);
		}
		else if("completed".equals(type)) {
			param.put("completed", "1");
			// 已完成任务
			IPage<Map> pageMap = uavService.getUavTask(page, param);
			for (Map map : pageMap.getRecords()) {
				if(CommonUtils.isObjEmpty(map.get("center"))) {
					map.put("center", getCenter(map.get("polyline").toString()));
				}
			}
			result.put("listData", pageMap);
		}
//		if("init".equals(type)) {
//			Map<String, Object> listParam = new HashMap<String, Object>();
//			// 查询条件： 今天
//			String now = LocalDate.now().toString();
//			listParam.put("xcTime", now);
//			// 巡查任务总数
//			result.put("taskMonitorCount", taskXcService.getTaskMonitorCount(listParam));
//			// 最多10条实时动态
//			listParam.put("limit", 10);
//			// 实时动态
//			result.put("taskMonitorList", taskXcService.getTaskMonitorList(listParam));
//		}
		
		
		return ResultFactory.success(result);
	}
	//根据边界获取中心点
	private String getCenter(String polyline) {
		String[] polylineArray = polyline.split(";");
		float lng = 0;
		float lat = 0;
		 for (int f=0;f< polylineArray.length;f++) {
           String[] f2Arr = polylineArray[f].split(",");
           lng += Float.valueOf(f2Arr[0]);
           lat += Float.valueOf(f2Arr[1]);
       }
		 String center = String.valueOf(lng/polylineArray.length) +','+String.valueOf(lat/polylineArray.length);
		return center;
	}

	/**
	 * 巡查任务总数/实时动态
	 * @return
	 * @author shizt  
	 * @date 2019年5月28日
	 * @company chinobot
	 */
	@RequestMapping("/statistics")
	public Result getStatistics() {
		Map result = new HashMap();
		Map<String, Object> param = new HashMap<String, Object>();
		// 查询条件： 今天
		String now = LocalDate.now().toString();
		param.put("xcTime", now);
		// 巡查任务总数
		result.put("taskMonitorCount", flyTaskService.getTaskByTime(param));
		// 最多10条实时动态
		param.put("limit", 10);
		// 实时动态
		result.put("taskMonitorList", null);
		
		return ResultFactory.success(result);
	}
	
	@RequestMapping("/news")
	public Result getNews(Page page){
		Map<String, Object> param = new HashMap<String, Object>();
		// 查询条件： 今天
		String now = LocalDate.now().toString();
		param.put("xcTime", now);
		
		return ResultFactory.success(taskXcService.getTaskMonitorPage(page, param));
	}
	@RequestMapping("/getHistory")
	public Result getHistory(Page page, @RequestParam Map<String,Object> param) {
		//Map result = new HashMap();
			param.put("etype", "1");
			if(!CommonUtils.isObjEmpty(param.get("start")))
			{
				param.put("start", param.get("start")+" 00:00:00");
			}
			if(!CommonUtils.isObjEmpty(param.get("end")))
			{
				param.put("end", param.get("end")+" 23:59:59");
			}
			IPage<Map> uavHistroy = uavService.getHistoryUavList(page,param);
		//	result.put("uavList", uavHistroy);
		return ResultFactory.success(uavHistroy);
	}
	
	@RequestMapping("/getHistoryGui")
	public Result getHistoryGui(Page page, @RequestParam Map<String,Object> param) {
		Map result = new HashMap();
		if(!CommonUtils.isObjEmpty(param.get("searchTime.start")))
		{
			param.put("start", param.get("searchTime.start")+" 00:00:00");
		}
		if(!CommonUtils.isObjEmpty(param.get("searchTime.end")))
		{
			param.put("end", param.get("searchTime.end")+" 23:59:59");
		}
		List<Map> uavHistory = uavService.getHistoryGui(param);
		result.put("uavList", uavHistory);
		return ResultFactory.success(result);
	}
	
	
	
}
