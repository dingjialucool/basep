package com.chinobot.aiuas.bot_collect.airport.mapper;

import java.util.List;
import java.util.Map;

import com.chinobot.aiuas.bot_collect.airport.entity.vo.AirportUavPersonVo;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.airport.entity.BotProspectAirport;
import com.chinobot.aiuas.bot_collect.airport.entity.dto.AirportGeographyDTO;
import com.chinobot.aiuas.bot_collect.airport.entity.vo.AirportVO;
import com.chinobot.aiuas.bot_collect.airport.entity.vo.UavOfAirportVO;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 机场 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-15
 */
public interface BotProspectAirportMapper extends IBaseMapper<BotProspectAirport> {

	/**
	 * 查询机场
	 * @return
	 */
	List<AirportVO> selectAirport();

	/**
	 * 机场回显
	 * @param airportId
	 * @return
	 */
	AirportGeographyDTO getAirport(String airportId);

	/**
	 * 无人机分页
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<UavOfAirportVO> getUavList(Page page, @Param("p")Map<String, Object> param);

	/**
	 * 机场下无人机分页
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<UavOfAirportVO> getUavOfAirportList(Page page, @Param("p")Map<String, Object> param);

	/**
	 * 机场无人机人员 列表
	 * @Author: shizt
	 * @Date: 2020/3/6 15:44
	 */
	List<AirportUavPersonVo> getAirportUavPersonVo(@Param("p")Map<String, Object> param);

}
