package com.chinobot.plep.home.monitor.service;

import com.chinobot.plep.home.monitor.entity.UavFlight;
import com.chinobot.plep.home.monitor.entity.UavOnline;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author laihb
 * @since 2019-07-26
 */
public interface IUavOnlineService extends IBaseService<UavOnline> {

	void updateUav(UavFlight flight);

	List<Map> uavList(Page page,Map<String, Object> param);

	List<UavOnline> getUavOnlines();

}
