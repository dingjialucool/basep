package com.chinobot.aiuas.bot_collect.warning.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.aiuas.bot_collect.warning.entity.EventFlow;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.QuestionWarningDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfBoundary;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfDeptStatusVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfFlightVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfFlyVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfWarnDeptVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfWarnLocationVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfWarnSceneVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 预警事件流转记录 Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2020-02-24
 */
public interface EventFlowMapper extends IBaseMapper<EventFlow> {

	/**
	 * 问题预警总况页面----飞行采查情况
	 * @param dto
	 * @return
	 */
	List<QuestionWarningOfFlyVo> getFlyData(@Param("p")QuestionWarningDTO dto);

	/**
	 * 场景下的预警数
	 * @return
	 */
	List<QuestionWarningOfWarnSceneVo> getWarnData(@Param("p")QuestionWarningDTO dto);

	/**
	 * 地图上 预警经纬度及类型
	 * @param dto
	 * @return
	 */
	List<QuestionWarningOfWarnLocationVo> getWarnLocation(@Param("p") QuestionWarningDTO dto);

	/**
	 * 部门--分拨数量
	 * @param dto
	 * @return
	 */
	List<QuestionWarningOfWarnDeptVo> getWarnAllocate(@Param("p") QuestionWarningDTO dto);

	/**
	 * 查询所有场景
	 * @return
	 */
	List<Map> getSceneList();

	/**
	 * 查询指定区划
	 * @return
	 */
	List<Map> getAreaList();

	/**
	 * 查询部门
	 * @return
	 */
	List<Map> getDeptList();

	/**
	 * 预警处理情况
	 * @param dto
	 * @return
	 */
	List<QuestionWarningOfDeptStatusVo> getWarnDeptStatus(@Param("p") QuestionWarningDTO dto);

	/**
	 * 在飞航班总数
	 * @param dto
	 * @return
	 */
	Long getFlightingCount(@Param("p") QuestionWarningDTO dto);
	
	/**
	 * 已飞航班总数
	 * @param dto
	 * @return
	 */
	Long getFlightedCount(@Param("p") QuestionWarningDTO dto);
	
	/**
	 * 获取场景数
	 * @param dto
	 * @return
	 */
	Long getSceneCount(@Param("p") QuestionWarningDTO dto);
	
	/**
	 * 获取任务总数
	 * @param dto
	 * @return
	 */
	Long getFlightTaskObjectCount(@Param("p") QuestionWarningDTO dto);

	/**
	 * 获取边界
	 * @param areaIdList
	 * @return
	 */
	List<QuestionWarningOfBoundary> getBounday(@Param("areaIdList")List<String> areaIdList);
	
	/**
	 * 获取对象总数
	 * @param dto
	 * @return
	 */
	Long getObjectCount(@Param("p") QuestionWarningDTO dto);

}
