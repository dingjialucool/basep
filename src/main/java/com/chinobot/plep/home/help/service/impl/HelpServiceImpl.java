package com.chinobot.plep.home.help.service.impl;

import com.chinobot.plep.home.help.entity.Help;
import com.chinobot.plep.home.help.mapper.HelpMapper;
import com.chinobot.plep.home.help.service.IHelpService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-08-31
 */
@Service
public class HelpServiceImpl extends BaseService<HelpMapper, Help> implements IHelpService {

}
