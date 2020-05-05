package com.chinobot.aiuas.bot_collect.info.service.impl;

import com.chinobot.aiuas.bot_collect.info.entity.Tag;
import com.chinobot.aiuas.bot_collect.info.mapper.TagMapper;
import com.chinobot.aiuas.bot_collect.info.service.ITagService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 采查对象标签 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Service
public class TagServiceImpl extends BaseService<TagMapper, Tag> implements ITagService {

}
