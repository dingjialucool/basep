package com.chinobot.plep.home.route.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.route.entity.CheckPoint;
import com.chinobot.plep.home.route.entity.Route;
import com.chinobot.plep.home.route.entity.vo.VoPointRelation;
import com.chinobot.plep.home.route.entity.vo.VoRoute;
import com.chinobot.plep.home.route.service.ICheckPointService;
import com.chinobot.plep.home.route.service.IRouteBuildingService;
import com.chinobot.plep.home.route.service.IRouteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 路线表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-07-16
 */
@Api(tags= {"路线表接口"})
@RestController
@RequestMapping("/api/route/route")
public class RouteController extends BaseController {
	@Autowired
	private IRouteService routeService;
	@Autowired
	private ICheckPointService checkPointService;
	
	@ApiOperation(value = "获取建筑列表-N(暂未使用)", notes = "参数- 分页page,Map param")
	@GetMapping("/getBuildingPage")
	public Result getBuildingPage(Page page, @RequestParam Map<String, String> param) {
//		if(!ThreadLocalUtil.isShenz() && !ThreadLocalUtil.isSystemRole()) {
//			param.put("deptId",ThreadLocalUtil.getResources().getDept().getUuid());
//		}
		if(!ThreadLocalUtil.isShenz()) {
			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
		}else if(String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
			param.remove("deptId");
		}
		return ResultFactory.success(routeService.getIllegalBuilding(page, param));
	}
	
	@ApiOperation(value = "获取所有建筑-N(暂未使用)", notes = "参数- Map param")
	@GetMapping("/getBuildingAll")
	public Result getBuildingAll(@RequestParam Map<String, String> param) {
//		if(!ThreadLocalUtil.isShenz() && !ThreadLocalUtil.isSystemRole()) {
//			param.put("deptId",ThreadLocalUtil.getResources().getDept().getUuid());
//		}
		if(!ThreadLocalUtil.isShenz()) {
			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
		}else if(String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
			param.remove("deptId");
		}
		return ResultFactory.success(routeService.getIllegalBuilding(param));
	}

	@ApiOperation(value = "保存起飞点、航线、路线建筑", notes = "参数-起飞点，航线，路线建筑包装类 vo")
	@PostMapping("/save")
//	@RequestMapping("/save")
	public Result save(@RequestBody VoRoute vo) {
		
		return ResultFactory.success(routeService.save(vo));
	}
	/**
	 * 获取起飞点
	 * @return
	 */
	@ApiOperation(value = "获取起飞点", notes = "参数-param")
	@GetMapping("/getCheckPoint")
//	@RequestMapping("/getCheckPoint")
	public Result getCheckPoint(@RequestParam Map<String, String> param) {
//		QueryWrapper<CheckPoint> queryWrapper = new QueryWrapper<CheckPoint>();
//		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//		if(!ThreadLocalUtil.isShenz() && !ThreadLocalUtil.isSystemRole()) {
//			queryWrapper.eq("dept_id", ThreadLocalUtil.getResources().getDept().getUuid());
//		}
//		if(!ThreadLocalUtil.isShenz()) {
//			queryWrapper.eq("dept_id", ThreadLocalUtil.getResources().getDept().getUuid());
//		}else if(!String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
//			queryWrapper.eq("dept_id", param.get("deptId"));
//		}
		if(StringUtils.isBlank(param.get("deptId"))) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		return ResultFactory.success(checkPointService.getAllCheckPointBydept(param));
	}
	
	@ApiOperation(value = "获取起飞点-N(暂未使用)", notes = "参数-起飞点id")
	@GetMapping("/getCheckPointByid")
	public Result getCheckPointByid(String id) {
		return ResultFactory.success(checkPointService.getById(id));
	}
	
	@ApiOperation(value = "获取路线列表", notes = "参数-Map param")
	@GetMapping("/getFlyRouteList")
//	@RequestMapping("/getFlyRouteList")
	public Result getFlyRouteList(@RequestParam Map<String, Object> param) {
//		QueryWrapper<Route> queryWrapper = new QueryWrapper<Route>();
//		String nameLike = param.get("name");
//		if(StringUtils.isNotEmpty(nameLike)) {
//			queryWrapper.like("route_name", nameLike);
//		}
//		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//
//		if(!ThreadLocalUtil.isShenz()) {
//			queryWrapper.eq("dept_id", ThreadLocalUtil.getResources().getDept().getUuid());
//		}else if(!String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
//			queryWrapper.eq("dept_id", param.get("deptId"));
//		}
//		queryWrapper.orderByAsc("create_time");
		if(param.get("deptId") !=null && StringUtils.isBlank(param.get("deptId").toString())) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		return ResultFactory.success(routeService.getRouteByDeptAndName(param));
	}
	
	@ApiOperation(value = "删除航线-N(暂未使用)", notes = "参数-航线route")
	@PostMapping("/del")
	public Result delRoute(@RequestBody Route route) {
		routeService.delRoute(route);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "获取单个路线路径", notes = "参数- 航线id，起飞点主键uuid ")
	@GetMapping("/getRouteLine")
//	@RequestMapping("/getRouteLine")
	public Result getRouteLine(String uuid,String id) {
		HashMap map = new HashMap();
		map.put("line",routeService.getRouteLine(uuid));
		map.put("point",checkPointService.getById(id));
		return ResultFactory.success(map);
	}
	
	
	@ApiOperation(value = "获取所有路线路径", notes = "参数- Map param ")
	@GetMapping("/getAllRouteLine")
//	@RequestMapping("/getAllRouteLine")
	public Result getAllRouteLine(@RequestParam Map<String, String> param) {
//		if(!ThreadLocalUtil.isShenz()) {
//			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
//		}else if(String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
//			param.remove("deptId");
//		}
		if(StringUtils.isBlank(param.get("deptId"))) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		return ResultFactory.success(routeService.getAllRouteLine(param));
	}
	
	@ApiOperation(value = "新增起飞点    关联起飞点", notes = "参数-起飞点，关联点集合等包装类 vo")
	@PostMapping("/addPointAndRelations")
//	@RequestMapping("/addPointAndRelations")
	public Result addPointAndRelations(@RequestBody VoPointRelation vo) {
		
		return ResultFactory.success(routeService.addPointAndRelations(vo));
	}
	
	
	@ApiOperation(value = "删除起飞点 和 将范围内 的 关联点置为空", notes = "参数-范围主键rangeId,起飞点主键pointId")
	@GetMapping("/delPoint")
//	@RequestMapping("/delPoint")
	public Result deletePoint(String rangId,String pointId) {
		
		return ResultFactory.success(routeService.deletePoint(rangId,pointId));
	}
	
	/**
	 * 获取航线列表
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "获取航线列表", notes = "参数- 分页page，Map param")
	@GetMapping("/flyRouteList")
//	@RequestMapping("/flyRouteList")
	public Result flyRouteList(Page page,@RequestParam Map<String, Object> param) {
		
		Person person = ThreadLocalUtil.getResources();
//		if(!ThreadLocalUtil.isSystemRole()) {
			param.put("deptId", person.getDeptId());
//		}
		return ResultFactory.success(routeService.flyRouteList(page,param));
	}
	
	
	@ApiOperation(value = "获取飞行任务列表", notes = "参数- 分页page，Map param")
	@GetMapping("/flyTaskList")
//	@RequestMapping("/flyTaskList")
	public Result flyTaskList(Page page,@RequestParam Map<String, Object> param) {
		Person person = ThreadLocalUtil.getResources();
//		if(!ThreadLocalUtil.isSystemRole()) {
			param.put("deptId", person.getDeptId());
//		}
		return ResultFactory.success(routeService.flyTaskList(page,param));
	}
	
	
	@ApiOperation(value = "删除飞行任务", notes = "参数- Map param")
	@GetMapping("/delFlyTask")
//	@RequestMapping("/delFlyTask")
	public Result delFlyTask(@RequestParam Map<String, Object> param) {
		
		return ResultFactory.success(routeService.delFlyTask(param));
	}
	
	@ApiOperation(value = "获取起飞点数量,航线数量", notes = "无参数")
	@GetMapping("/getCount")
//	@RequestMapping("/getCount")
	public Result getCount() {
		Map map =  new HashMap();
		QueryWrapper<Route> routeWrapper = new QueryWrapper<Route>();
		routeWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		int routeCount = routeService.count(routeWrapper);
		QueryWrapper<CheckPoint> pointWrapper = new QueryWrapper<CheckPoint>();
		pointWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		int pointCount = checkPointService.count(pointWrapper);
		map.put("routeCount", routeCount);
		map.put("pointCount", pointCount);
		return ResultFactory.success(map);
	}
	
}
