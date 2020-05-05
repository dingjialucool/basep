package com.chinobot.plep.home.monitor.service;

import com.chinobot.plep.home.monitor.entity.PersonOnline;
import com.chinobot.plep.home.monitor.entity.PersonTrajectory;

import java.util.List;
import java.util.Map;

import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author djl
 * @since 2019-11-22
 */
public interface IPersonTrajectoryService extends IBaseService<PersonTrajectory> {

	/**
	 * 获取在线人员
	 * @return
	 */
	List<PersonOnline> getList();

}
