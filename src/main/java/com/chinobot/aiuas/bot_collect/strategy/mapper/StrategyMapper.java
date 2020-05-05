package com.chinobot.aiuas.bot_collect.strategy.mapper;

import java.util.List;
import java.util.Map;

import com.chinobot.aiuas.bot_collect.strategy.entity.vo.*;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.strategy.entity.Strategy;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ListInfoDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ListTaskDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.PageStrategyDTO;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 采查策略 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface StrategyMapper extends IBaseMapper<Strategy> {

	IPage<PageStrategyVO> pageStrategy(Page page, @Param("dto")PageStrategyDTO dto);

	List<DomainWithSceneVO> listAllDomainAndScene();

	List<TaskVO> listTask(@Param("dto")ListTaskDTO dto);

	List<InfoVO> listInfo(@Param("dto")ListInfoDTO dto);
	
	StrategyDetailVO getStrategyDetail(@Param("strategyId")String strategyId);

	/**
	 * 策略列表
	 * @Author: shizt
	 * @Date: 2020/2/21 15:53
	 */
	List<StrategyListVO> getStrategyList(@Param("name")String strategyName);
	
	/**
	 * 查询所有上线且有航班的非专项策略
	 * @return
	 */
	List<Strategy> listStrategyOnline();

	/**
	 * 根据航班作业id获取策略信息
	 * @param flightWorkId
	 * @return
	 */
	Map<String, Object> getInfoByFlightWorkId(@Param("flightWorkId") String flightWorkId);
	
	/**
	 * 根据任务id获取策略信息
	 * @param flightWorkId
	 * @return
	 */
	List<Strategy> getStrategyInfoByTaskId(@Param("p") Map<String,Object> param);
	
	StrategySpecialVO getSpecialStrategyDetail(@Param("strategyId") String strategyId);
	
	List<FlightDeatilVO> listFlight(@Param("p") Map<String,Object> param);
}
