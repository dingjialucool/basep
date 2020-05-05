package com.chinobot.aiuas.bot_collect.resource.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinobot.aiuas.bot_collect.resource.entity.vo.HolidayVo;
import com.chinobot.aiuas.bot_collect.resource.service.IHolidayService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 节日时点表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-02-25
 */
@Api(tags="工作日时点接口")
@RestController
@RequestMapping("api/bot/resource/holiday")
public class HolidayController extends BaseController {
	
	@Autowired
	private IHolidayService holidayService;
	
	
	@ApiOperation(value = "根据月份获取日期列表信息", notes = "返回日期信息集合")
	@ApiImplicitParams({
		@ApiImplicitParam(name="month",value="月份，格式yyyy-MM",required=true,paramType="query")
	})
	@GetMapping("/listHolidayByMonth")
	public Result<List<HolidayVo>> listHolidayByMonth(String month){
		
		return ResultFactory.success(holidayService.listHolidayByMonth(month));
	}
	
	@ApiOperation(value = "保存日期信息", notes = "返回日期信息主键")
	@PostMapping("/saveHoliday")
	public Result<String> saveHoliday(@RequestBody HolidayVo vo){
		
		return ResultFactory.success(holidayService.saveHoliday(vo));
	}
}
