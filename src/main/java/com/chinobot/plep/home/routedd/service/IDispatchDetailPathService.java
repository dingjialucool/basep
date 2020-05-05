package com.chinobot.plep.home.routedd.service;

import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;

import java.util.List;
import java.util.Map;

import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangw
 * @since 2019-08-05
 */
public interface IDispatchDetailPathService extends IBaseService<DispatchDetailPath> {
	List<Map> getPathFlightTotal();
}
