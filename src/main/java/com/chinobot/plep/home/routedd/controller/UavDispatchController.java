package com.chinobot.plep.home.routedd.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.routedd.entity.DispatchDetail;
import com.chinobot.plep.home.routedd.entity.UavDispatch;
import com.chinobot.plep.home.routedd.entity.vo.DispatchVo;
import com.chinobot.plep.home.routedd.service.IUavDispatchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 无人机调度表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-07-24
 */
@Api(tags= {"无人机调度表接口"})
@RestController
@RequestMapping("/api/routedd/uav-dispatch")
public class UavDispatchController extends BaseController {
	@Autowired
	private IUavDispatchService uavDispatchService;
	
	@ApiOperation(value = "获取所有无人机", notes = "参数- Map param")
	@GetMapping("/getAllUav")
//	@RequestMapping("/getAllUav")
	public Result getAllUav(@RequestParam Map<String, String> param) throws Exception {
		if(param.get("deptId") !=null && StringUtils.isBlank(param.get("deptId").toString())) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		return ResultFactory.success(uavDispatchService.getAllUav(param));
	}
	
	//获取禁飞区
	@ApiOperation(value = "获取禁飞区 - N(暂未使用)", notes = "参数- Map param")
	@GetMapping("/getNotFlyPoints")
//	@RequestMapping("/getNotFlyPoints")
	public Result getNotFlyPoints(@RequestParam Map<String, Object> param) {
		if(!ThreadLocalUtil.isShenz() && !ThreadLocalUtil.isSystemRole()) {
			param.put("deptId",ThreadLocalUtil.getResources().getDept().getUuid());
		}
		return ResultFactory.success(uavDispatchService.getNotFlyPoints(param));
	}
	
	@ApiOperation(value = "获取航线-区域-起飞点", notes = "参数- Map param")
	@GetMapping("/getFlyPoints")
//	@RequestMapping("/getFlyPoints")
	public Result getFlyPoints(@RequestParam Map<String, Object> param) {
//		if(!ThreadLocalUtil.isShenz() && !ThreadLocalUtil.isSystemRole()) {
//			param.put("deptId",ThreadLocalUtil.getResources().getDept().getUuid());
//		}
//		if(!ThreadLocalUtil.isShenz()) {
//			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
//		}else if(String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
//			param.remove("deptId");
//		}
		if(param.get("deptId") !=null && StringUtils.isBlank(param.get("deptId").toString())) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		return ResultFactory.success(uavDispatchService.getFlyPoints(param));
	}
	
	
	@ApiOperation(value = "获取单个无人机某天调度信息-N(暂未使用)", notes = "参数- 无人机主键uavId，时间time")
	@GetMapping("/getOneDay")
//	@RequestMapping("/getOneDay")
	public Result getOneDay(String uavId, String time) {
		
		return ResultFactory.success(uavDispatchService.getOneDay(uavId, time));
	}
	
	@ApiOperation(value = "计划添加飞行路线", notes = "参数 - 无人机调度，调度明细对象等包装类")
	@PostMapping("/addPoint")
//	@RequestMapping("/addPoint")
	public Result addPoint(@RequestBody DispatchVo vo) {
		uavDispatchService.addPoint(vo);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "删除计划中的定点", notes = "参数 - 调度明细对象dispatchDetail")
	@PostMapping("/delPoint")
//	@RequestMapping("/delPoint")
	public Result delPoint(@RequestBody DispatchDetail dispatchDetail) {
		uavDispatchService.delPoint(dispatchDetail);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "删除计划", notes = "参数 - 无人机调度对象uavDispatch")
	@PostMapping("/cleanOneDay")
//	@RequestMapping("/cleanOneDay")
	public Result cleanOneDay(@RequestBody UavDispatch uavDispatch) {
		uavDispatchService.cleanOneDay(uavDispatch);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "收藏计划", notes = "参数 - 无人机调度主键uuid ")
	@GetMapping("/doCollect")
//	@RequestMapping("/doCollect")
	public Result doCollect(String uuid) {
		uavDispatchService.doCollect(uuid);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "历史引用路线列表", notes = "参数- 分页page，Map  param")
	@GetMapping("/getHistoryPage")
//	@RequestMapping("/getHistoryPage")
	public Result getHistoryPage(Page page,@RequestParam Map<String, Object> param) {
		if(!ThreadLocalUtil.isShenz()) {
			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
		}else if(String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
			param.remove("deptId");
		}
		return ResultFactory.success(uavDispatchService.getHistoryList(page, param));
	}
	
	@ApiOperation(value = "引用路线列表", notes = "参数- 分页page，Map  param")
	@GetMapping("/getCollectPage")
//	@RequestMapping("/getCollectPage")
	public Result getCollectPage(Page page,@RequestParam Map<String, Object> param) {
		return ResultFactory.success(uavDispatchService.getCollectList(page, param));
	}
	
	@ApiOperation(value = "使用-引用路线", notes = "参数- 无人机调度主键targetid，sourceId")
	@GetMapping("/doQuote")
//	@RequestMapping("/doQuote")
	public Result doQuote(String targetId, String sourceId) {
		uavDispatchService.doQuote(targetId, sourceId);
		return ResultFactory.success();
	}
	
	
	@ApiOperation(value = "获取各日期状态及无人机、飞行员列表", notes = "参数- Map  param")
	@GetMapping("/init")
//	@RequestMapping("/init")
	public Result init(@RequestParam Map<String, String> param) throws Exception{
//		if(!ThreadLocalUtil.isShenz()) {
//			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
//		}else if(String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
//			param.remove("deptId");
//		}
		if(param.get("deptId") !=null && StringUtils.isBlank(param.get("deptId").toString())) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		return ResultFactory.success(uavDispatchService.init(param));
	}
	
	@ApiOperation(value = "获取某天的路线列表", notes = "参数 - Map param")
	@GetMapping("/getRouteListByDate")
//	@RequestMapping("/getRouteListByDate")
	public Result getRouteListByDate(@RequestParam Map<String, Object> param) {
//		QueryWrapper queryWrapper = new QueryWrapper();
//		queryWrapper.eq("time", date);
//		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//		if(!ThreadLocalUtil.isShenz()) {
//			queryWrapper.eq("dept_id", ThreadLocalUtil.getResources().getDept().getUuid());
//		}else if(!deptId.equals(GlobalConstant.SUPER_DEPT_ID)){
//			queryWrapper.eq("dept_id", deptId);
//		}
//		queryWrapper.orderByDesc("operate_time");
//		List<UavDispatch> uavDispatchs = uavDispatchService.list(queryWrapper);
		if(param.get("deptId") !=null && StringUtils.isBlank(param.get("deptId").toString())) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		List<UavDispatch> routeListByDate = uavDispatchService.getRouteListByDate(param);
		return ResultFactory.success(routeListByDate);
	}
	
	@ApiOperation(value = "保存调度路线信息", notes = "参数 - 无人机调度对象uavDispatch")
	@PostMapping("/saveRoute")
//	@RequestMapping("/saveRoute")
	public Result saveRoute(@RequestBody UavDispatch uavDispatch) {
		uavDispatch.setDeptId(ThreadLocalUtil.getResources().getDeptId());
		uavDispatchService.saveOrUpdate(uavDispatch);
		return ResultFactory.success(uavDispatch.getUuid());
	}
	
	@ApiOperation(value = "获取一条调度路线明细", notes = "参数 - 无人机调度主键 uuid ")
	@GetMapping("/getRouteDetail")
//	@RequestMapping("/getRouteDetail")
	public Result getRouteDetail(String uuid) {
		return ResultFactory.success(uavDispatchService.getRouteDetail(uuid));
	}

}
