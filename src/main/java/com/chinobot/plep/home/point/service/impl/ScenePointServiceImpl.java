package com.chinobot.plep.home.point.service.impl;

import com.chinobot.plep.home.point.entity.ScenePoint;
import com.chinobot.plep.home.point.mapper.ScenePointMapper;
import com.chinobot.plep.home.point.service.IScenePointService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定点表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-08-22
 */
@Service
public class ScenePointServiceImpl extends BaseService<ScenePointMapper, ScenePoint> implements IScenePointService {

}
