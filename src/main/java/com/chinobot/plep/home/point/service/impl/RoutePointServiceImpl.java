package com.chinobot.plep.home.point.service.impl;

import com.chinobot.plep.home.point.entity.RoutePoint;
import com.chinobot.plep.home.point.mapper.RoutePointMapper;
import com.chinobot.plep.home.point.service.IRoutePointService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 路线定点关系表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-08-23
 */
@Service
public class RoutePointServiceImpl extends BaseService<RoutePointMapper, RoutePoint> implements IRoutePointService {

}
