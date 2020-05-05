package com.chinobot.plep.home.dataset.controller;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.dataset.entity.WarningInfo;
import com.chinobot.plep.home.dataset.entity.WhiteList;
import com.chinobot.plep.home.dataset.entity.vo.FilterWarningVO;
import com.chinobot.plep.home.dataset.entity.vo.WhiteListVO;
import com.chinobot.plep.home.dataset.service.IWhiteListService;
import com.chinobot.plep.home.event.entity.EventMain;
import com.chinobot.plep.home.event.service.IEventMainService;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 白名单 前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-10-11
 */
@Api(tags= {"白名单接口"})
@RestController
@RequestMapping("/api/dataset/white-list")
public class WhiteListController extends BaseController {

	@Autowired
	private IWhiteListService whiteListService;
	@Autowired
	private IEventMainService eventMainService;
//	@Autowired
//	private IWarningInfoService warningInfoService;
	
	@ApiOperation(value = "获取白名单列表", notes = "参数 - 分页page,Map param")
	@GetMapping("/getWhiteList")
	public Result<IPage<WhiteListVO>> getWhiteList(Page page,@RequestParam  Map<String, Object> param) {
		
		Dept dept = ThreadLocalUtil.getResources().getDept();
		param.put("deptId", dept.getUuid());
		
		return ResultFactory.success(whiteListService.getWhiteList(page,param));
		
	}
	
	@ApiOperation(value = "删除白名单", notes = "参数 - 白名单uuid")
	@GetMapping("/delWhite")
	public Result delWhite(@ApiParam(name = "uuid", value = "白名单主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(whiteListService.delWhite(uuid));
		
	}
	
	@ApiOperation(value = "白名单查看", notes = "参数 - 白名单uuid")
	@GetMapping("/showWhite")
	public Result showWhite(@ApiParam(name = "uuid", value = "白名单主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(whiteListService.showWhite(uuid));
	}
	
	@ApiOperation(value = "添加白名单", notes = "参数 - 白名单whiteList(用于事件分派添加白名单功能)")
	@PostMapping("/addWhite")
	public Result addWhite(@RequestBody WhiteList whiteList) {
		
		return ResultFactory.success(whiteListService.addWhite(whiteList));
	}
	
	@ApiOperation(value = "过滤预警列表", notes = "参数 - 分页page,Map param")
	@GetMapping("/getFilterList")
	public Result<IPage<FilterWarningVO>> getFilterList(Page page,@RequestParam  Map<String, Object> param) {
		
		
		return ResultFactory.success(whiteListService.getFilterList(page,param));
	}
	
	@ApiOperation(value = "过滤预警移除", notes = "参数 - 事件uuid")
	@GetMapping("/delFilterWarn")
	public Result delFilterWarn(@ApiParam(name = "uuid", value = "事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) throws Exception {
		
		eventMainService.warningFilterRemove(uuid);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "过滤预警查看", notes = "参数 - 事件uuid")
	@GetMapping("/showFilterWarn")
	public Result showFilterWarn(@ApiParam(name = "uuid", value = "事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(whiteListService.showFilterWarn(uuid));
	}
	
	@ApiOperation(value = "修改白名单截至时间", notes = "参数 - 白名单主键uuid,截至时间invalidTime")
	@PostMapping("/updateWhite")
	public Result updateWhite(@RequestBody WhiteList whiteList) {
		
		return ResultFactory.success(whiteListService.updateById(whiteList));
	}
	
	@ApiOperation(value = "查询所有飞行员", notes = "参数 - 无")
	@GetMapping("/getFlyPersons")
	public Result getFlyPersons() {
		
		Person person = ThreadLocalUtil.getResources();
		HashMap param = Maps.newHashMap();
		param.put("deptId", person.getDeptId());
		return ResultFactory.success(whiteListService.getFlyPersons(param));
	}
}
