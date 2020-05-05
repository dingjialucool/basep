package com.chinobot.aiuas.bot_collect.warning.service;

import java.util.List;

import com.chinobot.aiuas.bot_collect.warning.entity.EventFlow;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.QuestionWarningDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfAreaLocation;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfBoundary;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.SearchParamVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.SearchParamsVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 预警事件流转记录 服务类
 * </p>
 *
 * @author djl
 * @since 2020-02-24
 */
public interface IEventFlowService extends IBaseService<EventFlow> {

	/**
	 * 问题预警总况页面筛选条件
	 * @return
	 */
	SearchParamVo getSerachParam();

	/**
	 * 问题预警总况数据
	 * @param dto
	 * @return
	 */
	QuestionWarningVo getWarningData(QuestionWarningDTO dto);

	/**
	 * 问题预警总况页面筛选条件
	 * @return
	 */
	List<SearchParamsVo> getSerachParams();

	/**
	 * 获取边界
	 * @param areaIdList
	 * @return
	 */
	List<QuestionWarningOfBoundary> getBounday(List<String> areaIdList);

	/**
	 * 四个镇的经纬度
	 * @return
	 */
	List<QuestionWarningOfAreaLocation> getArea();

}
