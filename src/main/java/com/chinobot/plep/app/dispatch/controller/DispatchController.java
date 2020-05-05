package com.chinobot.plep.app.dispatch.controller;

import com.alibaba.fastjson.JSON;
import com.chinobot.aiuas.bot_event.urgent.entity.Urgent;
import com.chinobot.aiuas.bot_event.urgent.service.IUrgentService;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightWorkService;
import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.app.dispatch.entity.AppDispatchFile;
import com.chinobot.plep.app.dispatch.service.IAppDispatchFileService;
import com.chinobot.plep.home.route.service.IRouteService;
import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;
import com.chinobot.plep.home.routedd.entity.DispatchDetailRoute;
import com.chinobot.plep.home.routedd.service.IDispatchDetailPathService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailRouteService;
import com.chinobot.plep.home.routedd.service.IUavDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app 无人机调度
 * @author shizt
 * @date 2019年7月31日
 */
@RestController
@RequestMapping("/api/app/dispatch")
public class DispatchController extends BaseController {
	@Autowired
	private IUavDispatchService uavDispatchService; 
	@Autowired
	private IRouteService routeService;
	@Autowired
	private IAppDispatchFileService appDispatchFileService;
	@Autowired
	private IDispatchDetailPathService dispatchDetailPathService;
	@Autowired
	private IDispatchDetailRouteService dispatchDetailRouteService;

	@Autowired
	private IUrgentService iUrgentService;
	@Autowired
	private IReUavService iReUavService;
	@Autowired
	private IFlightWorkService iFlightWorkService;
	
	/**
	 * 获取起飞点列表
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年7月31日
	 * @company chinobot
	 */
	@RequestMapping("/pointList")
	public Result getPointList(@RequestParam Map<String, String> param) {
		// 查询条件： 今天
//		String now = LocalDate.now().toString();
//		param.put("time", now);
//		param.put("time", "2019-07-31");

		Map result = new HashMap();
		List<Map> uavs = uavDispatchService.getUavByFlyPerson(param.get("userId"));
		if(uavs.size() == 0){
			return ResultFactory.success();
		}else if(null == param.get("uavId")){
			param.put("uavId", (String) uavs.get(0).get("uuid"));
			result.put("uavs", uavs);
		}
		result.put("points", uavDispatchService.getPointByParam(param));

		return ResultFactory.success(result);
	}

	/**
	 * 根据飞行员获取当日巡查无人机
	 * @Param: [userId]
	 * @Return: com.chinobot.common.domain.Result
	 * @Author: shizt
	 * @Date: 2019/10/28 19:12
	 */
	@RequestMapping("/uavs")
	public Result getUavByFlyPerson(String userId){
//		return ResultFactory.success(uavDispatchService.getUavByFlyPerson(userId));
		return ResultFactory.success(iReUavService.lambdaQuery()
				.select(Uav::getUuid, Uav::getEname)
				.eq(Uav::getIsDeleted, GlobalConstant.IS_DELETED_NO)
				.list());
	}
	
	/**
	 * 获取航点列表
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年8月1日
	 * @company chinobot
	 */
	@RequestMapping("/routeList")
	public Result getRouteList(@RequestParam Map<String, String> param) {
//		QueryWrapper<Route> queryWrapper = new QueryWrapper<Route>();
//		queryWrapper.eq("check_point_id", pointId)
//					.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		
		return ResultFactory.success(routeService.getRouteList(param));
	}
	
	/**
	 * 获取航线节点
	 * @param routeId
	 * @return
	 * @author shizt  
	 * @date 2019年8月2日
	 * @company chinobot
	 */
	@RequestMapping("/routePoint")
	public Result getRoutePoint(String routeId) {
		return ResultFactory.success(routeService.getRoutePoint(routeId));
	}
	
	/**
	 * 保存调度巡查文件
	 * @param appDispatchFiles
	 * @return
	 * @author shizt  
	 * @date 2019年8月13日
	 * @company chinobot
	 */
	@RequestMapping("/save")
	public Result save(@RequestBody List<AppDispatchFile> appDispatchFiles,
					   @RequestParam(required = false) String status,
					   @RequestParam(required = false) String lineType,
					   @RequestParam(required = false) String taskId) {
		if(null != status){
			// 执行完航线，修改任务状态
			if(lineType.equals("area")){
				DispatchDetailPath dispatchDetailPath = new DispatchDetailPath();
				dispatchDetailPath.setUuid(taskId);
				dispatchDetailPath.setStatus(status);

				dispatchDetailPathService.updateById(dispatchDetailPath);
			}else if(lineType.equals("track")){
				DispatchDetailRoute dispatchDetailRoute = new DispatchDetailRoute();
				dispatchDetailRoute.setUuid(taskId);
				dispatchDetailRoute.setStatus(status);

				dispatchDetailRouteService.updateById(dispatchDetailRoute);
			}
		}

//        System.out.println("appDispatchFiles: " + JSON.toJSONString(appDispatchFiles));
//		Result result = appDispatchFileService.saveOrUpdateAppDispatchFiles(appDispatchFiles);

		return ResultFactory.success();
	}

	/**
	 * 航班 列表
	 * @Author: shizt
	 * @Date: 2020/3/24 16:17
	 */
	@GetMapping("/getUrgentList")
	public Result getUrgentList(@RequestParam Map<String, String> param){
		return ResultFactory.success(iUrgentService.getAppUrgentList(param));
	}

	/**
	 * 紧急调度 航线
	 * @Author: shizt
	 * @Date: 2020/3/24 16:47
	 */
	@GetMapping("/getRouteByUrgent")
	public Result getRouteByUrgent(String urgentId){
		return ResultFactory.success(iUrgentService.getRouteByUrgent(urgentId));
	}

	/**
	 * 航班 航线
	 * @Author: shizt
	 * @Date: 2020/3/24 16:47
	 */
	@GetMapping("/getRouteByFlightWork")
	public Result getRouteByFlightWork(String flightWorkId){
		return ResultFactory.success(iFlightWorkService.getRouteByFlightWork(flightWorkId));
	}

}
