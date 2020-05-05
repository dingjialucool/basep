package com.chinobot.aiuas.bot_collect.strategy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinobot.aiuas.bot_collect.strategy.entity.StrategyTask;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.StrategySerachDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportAllVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 采查策略任务关联表 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface IStrategyTaskService extends IBaseService<StrategyTask> {

	/**
	 * 策略报告列表
	 * @param dto
	 * @return
	 */
	IPage<StrategyReportVo> getStrategyList(StrategySerachDto dto);

	/**
	 * 策略报告-查看
	 * @param uuid
	 * @return
	 */
	StrategyReportAllVo getStrategyReportDetail(String uuid);
	
	/**
	 * 人员在线离线状态改变
	 * @param uuid
	 * @return
	 */
	void personOnlineStatus();
	
	/**
	 * 无人机在线离线更改
	 * @param uuid
	 * @return
	 */
	void uavOnlineStatus();
	
	/**
	 * 生成本年及下一年的周末
	 * @param uuid
	 * @return
	 */
	void createWeekends();

}
