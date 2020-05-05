package com.chinobot.plep.home.routedd.service;

import com.chinobot.plep.home.routedd.entity.DispatchDetailRoute;
import com.chinobot.plep.home.routedd.entity.dto.UrgentDto;
import com.chinobot.plep.home.routedd.entity.vo.UrgentUavVo;

import java.util.List;
import java.util.Map;

import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 调度明细-路线表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-07-25
 */
public interface IDispatchDetailRouteService extends IBaseService<DispatchDetailRoute> {
	List<Map> getRouteFlightTotal();
	
	List<UrgentUavVo> getAllUavForUrgent();

	void saveUrgent(UrgentDto dto) throws Exception;
}
