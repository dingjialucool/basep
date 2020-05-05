package com.chinobot.aiuas.bot_collect.warning.service.impl;

import com.chinobot.aiuas.bot_collect.warning.entity.WarningInfo;
import com.chinobot.aiuas.bot_collect.warning.mapper.WarningInfoMapper;
import com.chinobot.aiuas.bot_collect.warning.service.IWarningInfoService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预警事件消息关联表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-03-03
 */
@Service
public class WarningInfoServiceImpl extends BaseService<WarningInfoMapper, WarningInfo> implements IWarningInfoService {

}
