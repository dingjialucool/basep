package com.chinobot.plep.home.point.service;

import com.chinobot.plep.home.point.entity.FixedFlyPoint;

import java.util.List;

import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 航点表 服务类
 * </p>
 *
 * @author djl
 * @since 2019-11-14
 */
public interface IFixedFlyPointService extends IBaseService<FixedFlyPoint> {

	/**
	 * 获取存量任务的所有航点
	 * @param uuid任务主键
	 * @return
	 */
	List<FixedFlyPoint> listFlyPointsByTaskId(String uuid);
}
