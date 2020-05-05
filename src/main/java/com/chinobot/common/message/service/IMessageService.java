package com.chinobot.common.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.message.entity.Message;
import com.chinobot.common.message.entity.dto.MessageDto;
import com.chinobot.common.message.entity.vo.MessageVO;
import com.chinobot.framework.web.service.IBaseService;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-12-11
 */
public interface IMessageService extends IBaseService<Message> {

    /**
     * 发送消息
     * @Param: [message]
     * @Return: void
     * @Author: shizt
     * @Date: 2019/12/12 9:30
     */
    void sendMessage(MessageDto dto, Map<String, Object> customParam) throws IOException, TemplateException;
    
    IPage<MessageVO> pageMyMessage(Page page, Map<String, Object> param);
    
    List<MessageVO> listMyMessageUnRead();
    
    void readMessage(String uuid);
    
    MessageVO getMessageVOById(String uuid);
}
