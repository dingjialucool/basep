package com.chinobot.plep.home.event.service.impl;

import com.chinobot.plep.home.event.entity.PatrolIllegal;
import com.chinobot.plep.home.event.mapper.PatrolIllegalMapper;
import com.chinobot.plep.home.event.service.IPatrolIllegalService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 巡查违章类型表 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-06-20
 */
@Service
public class PatrolIllegalServiceImpl extends BaseService<PatrolIllegalMapper, PatrolIllegal> implements IPatrolIllegalService {

}
