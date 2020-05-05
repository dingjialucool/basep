package com.chinobot.aiuas.bot_collect.resource.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.resource.entity.Weather;
import com.chinobot.aiuas.bot_collect.resource.entity.dto.WeatherDTO;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.WeatherCityInfoVo;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.WeatherVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 天气预报表 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-02-25
 */
public interface IWeatherService extends IBaseService<Weather> {

	/**
	 * 获取城市编码
	 * @return
	 */
	List<WeatherCityInfoVo> getCityCode();

	/**
	 * 根据城市编码获取城市天气预报信息
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<WeatherVo> getCityWeartherByAdcode(Page page, Map<String, Object> param);

	void editCityWeartherByAdcode(WeatherDTO weatherDto);

	void addCityWeartherByAdcode(WeatherDTO weatherDto);


}
