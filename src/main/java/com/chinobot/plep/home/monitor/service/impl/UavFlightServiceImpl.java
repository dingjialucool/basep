package com.chinobot.plep.home.monitor.service.impl;

import com.chinobot.plep.home.monitor.entity.UavFlight;
import com.chinobot.plep.home.monitor.mapper.UavFlightMapper;
import com.chinobot.plep.home.monitor.service.IUavFlightService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 无人机飞行信息表 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-08-14
 */
@Service
public class UavFlightServiceImpl extends BaseService<UavFlightMapper, UavFlight> implements IUavFlightService {

}
