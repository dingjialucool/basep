package com.chinobot.plep.home.area.controller;


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

import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.area.entity.GovArea;
import com.chinobot.plep.home.area.service.IGovAreaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-09-10
 */
@Api(tags= {"行政区划接口"})
@RestController
@RequestMapping("/api/area/gov-area")
public class GovAreaController extends BaseController {
	@Autowired
	private IGovAreaService govAreaService;

	@ApiOperation(value = "保存行政区划", notes = "参数- 行政区划govArea")
	@PostMapping("/save")
//	@RequestMapping("/save")
	public Result save(@RequestBody GovArea govArea) {
		govAreaService.saveGovArea(govArea);
		return ResultFactory.success(govArea);
	}
	
	@ApiOperation(value = "获取所有行政区划", notes = "参数- Map param")
	@GetMapping("/getListByParent")
//	@RequestMapping("/getListByParent")
	public Result getListByParent(@RequestParam Map<String, Object> param) {
		List<Map> list = govAreaService.getListByParent(param);
		if(list.size()==1 && StringUtils.isBlank(String.valueOf(param.get("parent_id")))) {
			param.put("parent_id", list.get(0).get("id"));
			List<Map> sons = govAreaService.getListByParent(param);
			list.addAll(sons);
		}
		return ResultFactory.success(list);
	}
	
	@ApiOperation(value = "获取行政区划信息", notes = "参数- 行政区划id")
	@GetMapping("/getInfoByid")
//	@RequestMapping("/getInfoByid")
	public Result getInfoByid(String id) {
		GovArea entity = govAreaService.getById(id);
		GovArea parent = govAreaService.getById(entity.getParentId());
		Map map = new HashMap();
		map.put("entity", entity);
		map.put("parent", parent);
		return ResultFactory.success(map);
	}
	
	@ApiOperation(value = "删除行政区划", notes = "参数- 行政区划govArea")
	@PostMapping("/del")
//	@RequestMapping("/del")
	public Result del(@RequestBody GovArea govArea) {
		govAreaService.del(govArea);
		return ResultFactory.success();
	}
}
