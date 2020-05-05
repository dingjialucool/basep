package com.chinobot.aiuas.bot_collect.warning.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinobot.aiuas.bot_collect.warning.entity.dto.QuestionWarningDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfAreaLocation;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfBoundary;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.SearchParamVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.SearchParamsVo;
import com.chinobot.aiuas.bot_collect.warning.service.IEventFlowService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 预警事件流转记录 前端控制器
 * </p>
 *
 * @author djl
 * @since 2020-02-24
 */
@Api(tags = "问题预警总况接口")
@RestController
@RequestMapping("/api/bot/warning/event-flow")
public class EventFlowController extends BaseController {
	
	@Autowired
	private IEventFlowService eventFlowService;
	
	@ApiOperation(value = "问题预警总况筛选条件", notes = "问题预警总况筛选条件")
	@GetMapping("/getSerachParam")
	public Result<List<SearchParamsVo>> getSerachParam() {
		
		return ResultFactory.success(eventFlowService.getSerachParams());
	}
	
	@ApiOperation(value = "问题预警总况数据", notes = "问题预警总况数据")
	@GetMapping("/getWarningData")
	public Result<QuestionWarningVo> getWarningData(QuestionWarningDTO dto) {
		
		return ResultFactory.success(eventFlowService.getWarningData(dto));
	}
	
	@ApiOperation(value = "获取地图边界", notes = "获取地图边界")
	@PostMapping("/getBounday")
	public Result<List<QuestionWarningOfBoundary>> getBounday(@RequestBody List<String> areaIdList) {
		
		return ResultFactory.success(eventFlowService.getBounday(areaIdList));
	}
	
	@ApiOperation(value = "四个镇的经纬度", notes = "四个镇的经纬度")
	@GetMapping("/getArea")
	public Result<List<QuestionWarningOfAreaLocation>> getArea() {
		
		return ResultFactory.success(eventFlowService.getArea());
	}
}
