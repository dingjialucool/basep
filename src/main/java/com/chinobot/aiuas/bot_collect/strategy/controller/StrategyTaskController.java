package com.chinobot.aiuas.bot_collect.strategy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.StrategySerachDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportAllVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportVo;
import com.chinobot.aiuas.bot_collect.strategy.service.IStrategyTaskService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 采查策略任务关联表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Api(tags="策略报告接口")
@RestController
@RequestMapping("/api/bot/strategy/strategy-task")
public class StrategyTaskController extends BaseController {
	
	@Autowired
	private IStrategyTaskService strategyTaskService;

	@ApiOperation(value = "策略报告列表与查询", notes = "策略报告列表与查询")
	@GetMapping("/getStrategyList")
	public Result<IPage<StrategyReportVo>> getStrategyList(StrategySerachDto dto) {
		
		return ResultFactory.success(strategyTaskService.getStrategyList(dto));
	}
	
	@ApiOperation(value = "策略报告查看", notes = "策略报告查看")
	@GetMapping("/getStrategyReportDetail")
	public Result<StrategyReportAllVo> getStrategyReportDetail(@ApiParam(name = "uuid", value = "策略报告主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(strategyTaskService.getStrategyReportDetail(uuid));
	}
}
