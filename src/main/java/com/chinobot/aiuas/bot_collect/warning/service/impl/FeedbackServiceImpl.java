package com.chinobot.aiuas.bot_collect.warning.service.impl;

import com.chinobot.aiuas.bot_collect.warning.entity.Feedback;
import com.chinobot.aiuas.bot_collect.warning.mapper.FeedbackMapper;
import com.chinobot.aiuas.bot_collect.warning.service.IFeedbackService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 反馈记录 服务实现类
 * </p>
 *
 * @author djl
 * @since 2020-02-27
 */
@Service
public class FeedbackServiceImpl extends BaseService<FeedbackMapper, Feedback> implements IFeedbackService {

}
