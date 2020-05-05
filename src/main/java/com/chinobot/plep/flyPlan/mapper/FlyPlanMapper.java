package com.chinobot.plep.flyPlan.mapper;

import com.chinobot.plep.flyPlan.entity.FlyPlan;
import com.chinobot.plep.home.building.entity.Building;
import com.chinobot.plep.home.plan.entity.Range;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 飞行计划表 Mapper 接口
 * </p>
 *
 * @author djl
 * @param 
 * @since 2019-06-18
 */
public interface FlyPlanMapper extends IBaseMapper<FlyPlan> {

	/**
	 *  获取巡查计划列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage getFlyPlanList(Page page, @Param("param")Map<String, Object> param);

	/**
	 * 获取生成飞行任务所需的区域
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
	 *  获取当前部门下的巡查范围
	 * @param param
	 * @return
	 */
	List<Range> getRangeData(@Param("p")Map<String, Object> param);

	/**
	 * 获取当前部门下的巡查建筑
	 * @param param
	 * @return
	 */
	List<Building> getBuildingData(@Param("p")Map<String, Object> param);

}
