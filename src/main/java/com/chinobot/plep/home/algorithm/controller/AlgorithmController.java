package com.chinobot.plep.home.algorithm.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.algorithm.entity.Algorithm;
import com.chinobot.plep.home.algorithm.service.IAlgorithmService;
import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.entity.RoutePoint;
import com.chinobot.plep.home.point.service.IRoutePointService;
import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;
import com.chinobot.plep.home.routedd.service.IDispatchDetailPathService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 算法表 前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-08-25
 */
@Api(tags= {"算法表接口"})
@RestController
@RequestMapping("/api/algorithm")
public class AlgorithmController extends BaseController {
	
	@Autowired
	private IAlgorithmService algorithmService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IRoutePointService routePointService;
	@Autowired
	private IDispatchDetailPathService dispatchDetailPathService;

	@ApiOperation(value = "算法列表", notes = "参数- 分页Page,Map param")
	@GetMapping("/list")
//	@RequestMapping("/list")
	public Result getAlgorithmList(Page page, @RequestParam Map<String, Object> param) {
		return ResultFactory.success(algorithmService.getAlgorithmList(page, param));
	}

	@ApiOperation(value = "保存算法", notes = "参数-Map param")
	@PostMapping("/save")
//	@RequestMapping("/save")
	public Result saveAlgorithm(@RequestBody Map<String, Object> param) {
		algorithmService.saveAlgorithm(param);
		return ResultFactory.success();
	}

	@ApiOperation(value = "提交算法", notes = "参数-Map param")
	@PostMapping("/commit")
//	@RequestMapping("/commit")
	public Result commit(@RequestBody Map<String, Object> param) {
		algorithmService.commitAlgorithm(param);
		return ResultFactory.success();
	}
	
	/**
	 *  保存基准图
	 * @param fileBus
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "保存基准图", notes = "参数-文件业务对象fileBus")
	@PostMapping("/saveFileBus")
//	@RequestMapping("/saveFileBus")
	public Result saveFileBus(@RequestBody FileBus fileBus) {
		//将已经关联的删除
		UpdateWrapper<FileBus> updateWrapper = new UpdateWrapper<FileBus>();
		updateWrapper.eq("bus_id", fileBus.getBusId()).eq("module", fileBus.getModule());
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		fileBusService.update(updateWrapper);
		//保存关联
		fileBusService.save(fileBus);
		return ResultFactory.success();
	}
	
	
	@ApiOperation(value = "查询该定点所在航线下的所有定点", notes = "参数- Map param")
	@GetMapping("/getPointsByFlyId")
//	@RequestMapping("/getPointsByFlyId")
	public Result getPointsByFlyId(@RequestParam Map<String,Object> param) {
		String pointId = (String) param.get("pointId");
		QueryWrapper<RoutePoint> queryWrapper = new QueryWrapper<RoutePoint>();
		queryWrapper.eq("point_id", pointId);
		RoutePoint routePoint = routePointService.getOne(queryWrapper);
		if(routePoint==null) {
			return null;
		}
		List<FixedPoint> list = algorithmService.getPointsByFlyId(routePoint.getRouteId());
		return ResultFactory.success(list);
	}
	
	
	@ApiOperation(value = "获取任务对应的算法", notes = "参数- Map param")
	@PostMapping("/getAlgorithmByTask")
//	@RequestMapping("/getAlgorithmByTask")
	public Result getAlgorithmByTask(@RequestBody Map<String,Object> param) {
		List<Algorithm> list = new ArrayList<Algorithm>();
		String uuid = param.get("uuid").toString();
		DispatchDetailPath dispatchDetailPath = dispatchDetailPathService.getById(uuid);
		if(dispatchDetailPath == null) {	//判断是区域巡查还是定点跟踪场景
			list = algorithmService.getRouterAlgorithmByTask(param);
		}else {
			list = algorithmService.getPathAlgorithmByTask(param);
		}
		return ResultFactory.success(list);
	}
}
