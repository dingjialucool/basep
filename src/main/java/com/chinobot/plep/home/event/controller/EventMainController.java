package com.chinobot.plep.home.event.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.event.entity.EventMain;
import com.chinobot.plep.home.event.service.IEventMainService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 事件主表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-06-11
 */
@Api(tags= {"事件主表接口"})
@RestController
@RequestMapping("/api/event")
public class EventMainController extends BaseController {
	
	@Autowired
	private IEventMainService eventMainService;
	
	
	@ApiOperation(value = "预警事件列表 -N(暂未使用)", notes = "参数-分页page,Map param")
	@GetMapping("/ewlist")
	public Result getEventList(Page page, @RequestParam Map<String, Object> param) {
		return ResultFactory.success(eventMainService.getEventList(page, param));
	}
	
	
	@ApiOperation(value = "获取事件详细 -N(暂未使用)", notes = "参数-事件id")
	@GetMapping("/detail")
	public Result getEventDetail(String eventId) {
		return ResultFactory.success(eventMainService.getEventDetail(eventId));
	}
	
//	@RequestMapping("/patrolList")
//	public Result getPatrolList(Page page, @RequestParam Map<String, Object> param) {
//		return ResultFactory.success(eventMainService.getPatrolList(page, param));
//	}
//
//	@RequestMapping("/reformList")
//	public Result getReformList(Page page, @RequestParam Map<String, Object> param) {
//		return ResultFactory.success(eventMainService.getReformList(page, param));
//	}

	@ApiOperation(value = "addInit -N(暂未使用)", notes = "无参数")
	@GetMapping("/addInit")
	public Result addInit() {
		return ResultFactory.success(eventMainService.addInit());
	}
	
	@ApiOperation(value = "事件处理 -N(暂未使用)", notes = "参数 -Map param")
	@PostMapping("/handle")
	public Result handle(@RequestBody Map<String, Object> param) {
		eventMainService.handle(param);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "事件添加 -N(暂未使用)", notes = "参数 -Map param")
	@PostMapping("/add")
	public Result add(@RequestBody Map<String, Object> param) {
		eventMainService.add(param);
		return ResultFactory.success();
	}
	
	/**
	 *  关联小区
	 * @param eventMain
	 * @return
	 */
	@ApiOperation(value = "关联小区 -N(暂未使用)", notes = "参数 -事件 eventMain")
	@PostMapping("/association")
	public Result association(@RequestBody EventMain eventMain) {
		
		return ResultFactory.success(eventMainService.updateById(eventMain));
	}
	
}
