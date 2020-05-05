package com.chinobot.aiuas.bot_collect.resource.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.resource.entity.dto.WeatherDTO;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.WeatherCityInfoVo;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.WeatherVo;
import com.chinobot.aiuas.bot_collect.resource.service.IWeatherService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 天气预报表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-02-25
 */
@Api(tags = "天气预报表 前端控制器")
@RestController
@RequestMapping("/api/resource/weather")
public class WeatherController extends BaseController {

	@Autowired
	private IWeatherService weatherService;

	@ApiOperation(value = "获取标签页城市信息", notes = "获取标签页城市信息")
	@GetMapping("/getCityCode")
	public Result<List<WeatherCityInfoVo>> getCityCode() {
		List<WeatherCityInfoVo> rs = weatherService.getCityCode();
		return ResultFactory.success(rs);
	}

	@ApiOperation(value = "根据城市编码获取城市天气信息", notes = "根据城市编码获取城市天气信息")
	@GetMapping("/getCityWeartherByAdcode")
	public Result<IPage<WeatherVo>> getCityWeartherByAdcode(Page page,
			@ApiParam(name = "param", value = "城市编码", required = false) @RequestParam(required = false) Map<String, Object> param) {
		IPage<WeatherVo> rs = weatherService.getCityWeartherByAdcode(page, param);
		return ResultFactory.success(rs);
	}

	@ApiOperation(value = "根据城市编码删除城市天气信息", notes = "根据城市编码删除城市天气信息")
	@PostMapping("/editCityWeartherByAdcode")
	public Result editCityWeartherByAdcode(
			@ApiParam(name = "weatherDto", value = "城市编码", required = true) @RequestBody(required = true) WeatherDTO weatherDto) {
		weatherService.editCityWeartherByAdcode(weatherDto);
		return ResultFactory.success();
	}

	@ApiOperation(value = "根据城市编码增加城市天气信息", notes = "根据城市编码增加城市天气信息")
	@PostMapping("/addCityWeartherByAdcode")
	public Result addCityWeartherByAdcode(
			@ApiParam(name = "weatherDto", value = "城市编码", required = true) @RequestBody(required = true) WeatherDTO weatherDto) {
		weatherService.addCityWeartherByAdcode(weatherDto);
		return ResultFactory.success();
	}

}
