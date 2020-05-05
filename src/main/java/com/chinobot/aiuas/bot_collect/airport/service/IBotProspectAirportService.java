package com.chinobot.aiuas.bot_collect.airport.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.airport.entity.BotProspectAirport;
import com.chinobot.aiuas.bot_collect.airport.entity.dto.AirportGeographyDTO;
import com.chinobot.aiuas.bot_collect.airport.entity.dto.UavAirportDTO;
import com.chinobot.aiuas.bot_collect.airport.entity.vo.AirportVO;
import com.chinobot.aiuas.bot_collect.airport.entity.vo.UavOfAirportVO;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 机场 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-15
 */
public interface IBotProspectAirportService extends IBaseService<BotProspectAirport> {

	/**
	 * 删除机场及机场下的无人机
	 * @param airportId
	 */
	void deleteAirport(String airportId);

	/**
	 * 新增机场
	 * @param dto
	 */
	void addAirport(AirportGeographyDTO dto);

	/**
	 * 机场查询
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
	IPage<UavOfAirportVO> getUavList(Page page, Map<String, Object> param);

	/**
	 * 增加机场下的无人机
	 * @param dto
	 */
	void addUavAirport(UavAirportDTO dto);

	/**
	 * 机场下无人机（分页）
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<UavOfAirportVO> getUavOfAirportList(Page page, Map<String, Object> param);

}
