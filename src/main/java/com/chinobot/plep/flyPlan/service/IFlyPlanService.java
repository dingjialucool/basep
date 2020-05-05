package com.chinobot.plep.flyPlan.service;

import com.chinobot.plep.flyPlan.entity.FlyPlan;
import com.chinobot.plep.home.building.entity.Building;
import com.chinobot.plep.home.plan.entity.Range;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 飞行计划表 服务类
 * </p>
 *
 * @author djl
 * @since 2019-06-18
 */
public interface IFlyPlanService extends IBaseService<FlyPlan> {

	/**
	 * 获取巡查计划列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage getFlyPlanList(Page page, Map<String, Object> param);

	/**
	 *  获取巡查范围
	 * @param param
	 * @return
	 */
	List<Range> getRangeData(Map<String, Object> param);

	/**
	 *  获取巡查建筑
	 * @param param
	 * @return
	 */
	List<Building> getBuildData(Map<String, Object> param);

	/**
	 * 更新任务
	 * @param param
	 * @return
	 */
	boolean saveOrUpdates(Map<String, Object> param);

	/**
	 *  删除飞行任务
	 * @param param
	 * @return
	 */
	boolean delFlyPlan(Map<String, Object> param);

	/**
	 *  获取生成飞行任务所需的区域
	 * @param uuid
	 * @return
	 */
	List<Map> getArea(String uuid);

	/**
	 * 获取生成飞行任务所需的建筑
	 * @param uuid
	 * @return
	 */
	List<Map> getBuild(String uuid);

	/**
	 *  改变计划的状态
	 * @param param
	 * @return
	 */
	boolean changeUseStatus(Map<String, Object> param);

	List getCenter(Map<String, Object> param);

}
