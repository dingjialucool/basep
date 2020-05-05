package com.chinobot.plep.home.point.mapper;

import com.chinobot.plep.home.point.entity.FixedFlyPoint;

import java.util.List;

import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 航点表 Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2019-11-14
 */
public interface FixedFlyPointMapper extends IBaseMapper<FixedFlyPoint> {

	/**
	 *  查询该定点下的所有航点
	 * @param uuid
	 * @return 航点集合
	 */
	List<String> getAllFlyPointByFixPointId(String uuid);
	
	/**
	 * 获取存量任务的所有航点
	 * @param uuid任务主键
	 * @return
	 */
	List<FixedFlyPoint> listFlyPointsByTaskId(String uuid);

}
