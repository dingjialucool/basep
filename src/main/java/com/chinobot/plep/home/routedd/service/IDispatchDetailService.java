package com.chinobot.plep.home.routedd.service;

import java.util.Map;

import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.routedd.entity.DispatchDetail;

/**
 * <p>
 * 调度明细 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-07-24
 */
public interface IDispatchDetailService extends IBaseService<DispatchDetail> {
	
	Map getDispatchUav(Map<String, Object> param);
	
}
