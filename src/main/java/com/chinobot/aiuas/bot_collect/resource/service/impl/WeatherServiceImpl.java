package com.chinobot.aiuas.bot_collect.resource.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.resource.entity.Weather;
import com.chinobot.aiuas.bot_collect.resource.entity.dto.WeatherDTO;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.WeatherCityInfoVo;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.WeatherVo;
import com.chinobot.aiuas.bot_collect.resource.mapper.WeatherMapper;
import com.chinobot.aiuas.bot_collect.resource.service.IWeatherService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.HttpUtils;
import com.chinobot.framework.web.service.impl.BaseService;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * <p>
 * 天气预报表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-02-25
 */
@Service
public class WeatherServiceImpl extends BaseService<WeatherMapper, Weather> implements IWeatherService {

	@Autowired
	private WeatherMapper weatherMapper;
	
	@Override
	public List<WeatherCityInfoVo> getCityCode() {
		return weatherMapper.getCityCode();
	}

	@Override
	public IPage<WeatherVo> getCityWeartherByAdcode(Page page, Map<String,Object> param) {
		IPage<WeatherVo>  weatherList = weatherMapper.getCityWeartherByAdcode(page,param);
		for (WeatherVo weather : weatherList.getRecords()) {
			weather.setDayTemp(weather.getDayTemp()+"℃");
			weather.setNightTemp(weather.getNightTemp()+"℃");
		}
		return weatherList;
	}

	@Override
	public void editCityWeartherByAdcode(WeatherDTO weatherDto) {
		if(!CommonUtils.isObjEmpty(weatherDto.getAdcode())) {
			UpdateWrapper<Weather> updateWrapper = new UpdateWrapper<Weather>();
			updateWrapper.set("is_deleted", 1);
			updateWrapper.eq("is_deleted", 0);
			updateWrapper.eq("adcode", weatherDto.getAdcode());
			this.update(updateWrapper);
		}
		
	}

	@Override
	public void addCityWeartherByAdcode(WeatherDTO weatherDto) {
		if(!CommonUtils.isObjEmpty(weatherDto.getAdcode())) {
			try {
				JSONObject amapWeather = HttpUtils.amapWeather("all", String.valueOf(weatherDto.getAdcode()));
				if(amapWeather != null) {
					UpdateWrapper<Weather> updateWrapper = new UpdateWrapper<Weather>();
					updateWrapper.set("is_deleted", 1);
					updateWrapper.eq("is_deleted", 0);
					updateWrapper.eq("adcode", weatherDto.getAdcode());
					//把之前天气置为无效
					this.update(updateWrapper);
					String province = amapWeather.getString("province");
					String city = amapWeather.getString("city");
					String adcode = amapWeather.getString("adcode");
					String reportTimeString = amapWeather.getString("reporttime");
					DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime reportTime = LocalDateTime.parse(reportTimeString, dateTimeFormatter);
					JSONArray casts = amapWeather.getJSONArray("casts");
					for(int i=0; i<casts.size(); i++) {
						JSONObject cast = casts.getJSONObject(i);
						Weather weather = new Weather();
						weather.setAdcode(adcode);
						weather.setCity(city);
						weather.setProvince(province);
						weather.setReportTime(reportTime);
						weather.setCastDate(LocalDate.parse(cast.getString("date"), dateFormatter));
						weather.setDayPower(cast.getString("daypower"));
						weather.setDayTemp(cast.getString("daytemp"));
						weather.setDayWeather(cast.getString("dayweather"));
						weather.setDayWind(cast.getString("daywind"));
						weather.setNightPower(cast.getString("nightpower"));
						weather.setNightTemp(cast.getString("nighttemp"));
						weather.setNightWeather(cast.getString("nightweather"));
						weather.setNightWind(cast.getString("nightwind"));
						this.save(weather);
					}
				}
			} catch (Exception e) {
				XxlJobLogger.log("查询天气时出错：{}", e);
			}
		}
		
	}

}
