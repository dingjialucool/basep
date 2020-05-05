package com.chinobot.plep.home.plan.controller;


import java.util.ArrayList;
import java.util.HashMap;
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
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.plan.entity.FlyArea;
import com.chinobot.plep.home.plan.entity.FlyConfig;
import com.chinobot.plep.home.plan.entity.vo.VoPathPlan;
import com.chinobot.plep.home.plan.service.IFlyAreaService;
import com.chinobot.plep.home.plan.service.IFlyConfigService;
import com.chinobot.plep.home.plan.service.IRangeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-06-17
 */
@Api(tags= {"区域类接口"})
@RestController
@RequestMapping("/api/plan/fly-area")
public class FlyAreaController extends BaseController {
	@Autowired
	private IFlyAreaService flyAreaService;
	@Autowired
	private IRangeService rangeService;
	@Autowired 
	private IFlyConfigService flyConfigService;
	
	@ApiOperation(value = "获取区域分页列表 -N(暂未使用)", notes = "参数 - 分页page,范围uuid")
	@GetMapping("/getPage")
	public Result getFlyAreaPage(Page page, String uuid) {
		
//		QueryWrapper<FlyArea> queryWrapper = new QueryWrapper<FlyArea>();
//		queryWrapper.eq("rang_id", uuid);
//		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//		if(StringUtils.isNotEmpty(name)) {
//			queryWrapper.like("name", name);
//		}
//		return ResultFactory.success(flyAreaService.page(page, queryWrapper));
		Map param = new HashMap();
		param.put("rangId", uuid);
		return ResultFactory.success(flyAreaService.getFlyAreaPage(page,param));
	}
	
	@ApiOperation(value = "获取所有区域数据", notes = "参数-范围主键uuid")
	@PostMapping("/getList")
//	@RequestMapping("/getList")
	public Result getFlyAreaList(String uuid) {
		QueryWrapper<FlyArea> queryWrapper = new QueryWrapper<FlyArea>();
		queryWrapper.eq("rang_id", uuid);
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<Map<String,Object>> listMaps = flyAreaService.listMaps(queryWrapper);
		List listMapsAll = new ArrayList();
		Map mapAll = new HashMap();
		for (Map<String, Object> map : listMaps) {
			Map param = new HashMap();
			param.put("rangeId", uuid);
			param.put("areaId", map.get("uuid"));
			Map pathConfigMap = flyAreaService.getPathConfig(param);
			map.put("flyPaths", pathConfigMap.get("flyPaths"));
			map.put("outputParamters", pathConfigMap.get("outputParamters"));
		}
		VoAddressBase<Map> rangeAddr = rangeService.getRangeAddrById(uuid);
		mapAll.put("range", rangeAddr);//范围
		//飞行参数
		QueryWrapper<FlyConfig> queryFlyconfig = new QueryWrapper<FlyConfig>();
		queryFlyconfig.eq("area_id", uuid).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		FlyConfig flyConfig = flyConfigService.getOne(queryFlyconfig);
		mapAll.put("flyConfig", flyConfig);//飞行参数
		
		mapAll.put("flyAreasCollect",listMaps);//区域集合
		return ResultFactory.success(mapAll);
	}
	
	@ApiOperation(value = "保存飞行区域 -N(暂未使用)", notes = "参数-飞行区域area")
	@PostMapping("/save")
	public Result save(@RequestBody FlyArea area) {
		flyAreaService.saveOrUpdate(area);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "删除飞行区域 -N(暂未使用)", notes = "参数-飞行区域area")
	@PostMapping("/del")
	public Result del(@RequestBody FlyArea area) {
		area.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		flyAreaService.updateById(area);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "保存路线及配置 -N(暂未使用)", notes = "参数-路线配置包装类vo")
	@PostMapping("/savePathConfig")
	public Result saveConfigAndPath(@RequestBody VoPathPlan vo) {
		flyAreaService.saveConfigAndPath(vo);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "获取路线及配置 -N(暂未使用)", notes = "参数-区域id")
	@PostMapping("/getConfigAndPath")
	public Result getConfigAndPath(String areaId) {
		
		return ResultFactory.success(flyAreaService.getConfigAndPath(areaId));
	}
	
	
	@ApiOperation(value = "获取航线、输出参数、飞行参数 -N(暂未使用)", notes = "参数- Map param")
	@GetMapping("/getPathConfig")
	public Result getPathConfig(@RequestParam Map<String, Object> param) {
		
		return ResultFactory.success(flyAreaService.getPathConfig(param));
	}
}
