package com.chinobot.aiuas.bot_collect.strategy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.strategy.entity.StrategyTask;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.StrategyReportOfEventTempDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.StrategySerachDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportFlightVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportFlyVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportOfWarnDetailVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportTaskVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 采查策略任务关联表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface StrategyTaskMapper extends IBaseMapper<StrategyTask> {

	/**
	 * 策略报告查询列表
	 * @param page
	 * @param dto
	 * @return
	 */
	IPage<StrategyReportVo> getStrategyList(Page page, @Param("p")StrategySerachDto dto);

	/**
	 * 策略报告-任务策略信息
	 * @param uuid
	 */
	StrategyReportTaskVo getReportOfStrategyTask(String uuid);

	/**
	 * 策略报告-航线信息
	 * @param uuid
	 * @return
	 */
	List<StrategyReportFlightVo> getReportOfFlight(String uuid);

	/**
	 * 策略报告-飞行巡查情况
	 * @param uuid
	 */
	List<StrategyReportFlyVo> getReportOfFly(String uuid);

	/**
	 * 策略报告-事件|线索统计
	 * @return
	 */
	List<StrategyReportOfEventTempDTO> getReportOfEventAndClue(String uuid);
	
	/**
	 * 策略报告-预警明细
	 * @return
	 */
	List<StrategyReportOfWarnDetailVo> getReportOfWarnDetail(String uuid);

	/**
	 * 策略报告-数量监测
	 * @param uuid
	 * @return
	 */
	List<CollectResultVo> getResultList(String uuid);
	
}
