package com.chinobot.plep.home.event.service.impl;

import com.chinobot.plep.home.event.entity.PatrolRecord;
import com.chinobot.plep.home.event.mapper.PatrolRecordMapper;
import com.chinobot.plep.home.event.service.IPatrolRecordService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 巡查记录表 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-06-20
 */
@Service
public class PatrolRecordServiceImpl extends BaseService<PatrolRecordMapper, PatrolRecord> implements IPatrolRecordService {

}
