package com.chinobot.plep.home.routedd.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.routedd.entity.dto.UrgentDto;
import com.chinobot.plep.home.routedd.entity.vo.UrgentUavVo;
import com.chinobot.plep.home.routedd.service.IDispatchDetailRouteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 调度明细-路线表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-07-25
 */
@Api(tags="紧急调度接口")
@RestController
@RequestMapping("/api/routedd/urgent")
public class DispatchDetailRouteController extends BaseController {

	@Autowired
	private IDispatchDetailRouteService service;
	
	@ApiOperation(value = "获取所有的无人机", notes = "获取本部门下所有的无人机")
	@GetMapping("/getAllUav")
	public Result<List<UrgentUavVo>> getAllUav(){
		
		return ResultFactory.success(service.getAllUavForUrgent());
	}
	@ApiOperation(value = "保存紧急调度", notes = "保存紧急调度")
	@PostMapping("/save")
	public Result save(@RequestBody UrgentDto dto) throws Exception {
		service.saveUrgent(dto);
		return ResultFactory.success();
	}

}
