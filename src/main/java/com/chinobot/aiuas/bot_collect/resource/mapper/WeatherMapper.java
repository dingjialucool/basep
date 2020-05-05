package com.chinobot.aiuas.bot_collect.resource.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.resource.entity.Weather;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.WeatherCityInfoVo;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.WeatherVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 天气预报表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-02-25
 */
public interface WeatherMapper extends IBaseMapper<Weather> {

	/**
	 * 获取城市编码tab页信息
	 * @return
	 */
	List<WeatherCityInfoVo> getCityCode();

	/**
	 * 根据城市编码获取城市天气预报信息
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<WeatherVo> getCityWeartherByAdcode(Page page, @Param("p")Map<String, Object> param);

}
