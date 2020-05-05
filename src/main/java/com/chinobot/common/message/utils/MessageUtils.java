package com.chinobot.common.message.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.cityle.base.mapper.PersonMapper;
import com.chinobot.common.message.entity.Message;
import com.chinobot.common.message.entity.MessageConfig;
import com.chinobot.common.message.entity.dto.MessageDto;
import com.chinobot.common.message.mapper.MessageConfigMapper;
import com.chinobot.common.message.mapper.MessageMapper;
import com.chinobot.plep.home.meeting.entity.vo.PersonVo;
import freemarker.template.TemplateException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息工具类
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2019-12-12 11:40
 */
@Component
public class MessageUtils {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MessageConfigMapper messageConfigMapper;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private PersonMapper personMapper;

    private static final String KAFKA_KEY = "global_msg";
    private static MessageUtils messageUtils;

    @PostConstruct
    public void init(){
        messageUtils = this;
        messageUtils.messageMapper = this.messageMapper;
        messageUtils.messageConfigMapper = this.messageConfigMapper;
        messageUtils.kafkaTemplate = this.kafkaTemplate;
        messageUtils.personMapper = this.personMapper;
    }

    /**
     * 发送消息
     * @Param: [dto, customParam]
     * @Return: void
     * @Author: shizt
     * @Date: 2019/12/12 15:03
     */
    public static Message send(MessageDto dto, Map<String, Object> customParam) throws IOException, TemplateException {
        QueryWrapper<MessageConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("code", dto.getCode());
        MessageConfig messageConfig = (MessageConfig) messageUtils.messageConfigMapper.selectOne(wrapper);
        PersonVo sendPerson = messageUtils.personMapper.personVoById(dto.getSendPid());
        PersonVo receivePerson = messageUtils.personMapper.personVoById(dto.getReceivePid());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        // 公共参数
        Map<String, Object> commonParam = new HashMap();
        commonParam.put("sendTime", now.format(formatter));
        commonParam.put("sendPerson", sendPerson.getPname());
        commonParam.put("receivePerson", receivePerson.getPname());
        commonParam.put("msgType", messageConfig.getName());
        // 消息参数
        Map<String, Map> params = new HashMap<>();
        params.put("commonParam", commonParam);
        params.put("customParam", customParam);
        // 解析模板，生成消息
        String content = FreeMarkerUtils.process(messageConfig.getTemplate(), params, null);
        // 消息入库
        Message message = new Message();
        message.setCreateTime(now);
        message.setCode(dto.getCode());
        message.setSendPid(dto.getSendPid());
        message.setReceivePid(dto.getReceivePid());
        if(StringUtils.isNotBlank(messageConfig.getUrl())) {
        	message.setUrl(messageConfig.getUrl() + "?" + dto.getUrlParams());
        }
        message.setContent(content);
        messageUtils.messageMapper.insert(message);
        // 推送消息
        messageUtils.kafkaTemplate.sendDefault(KAFKA_KEY, "{\"userId\":\"" + dto.getReceivePid() + "\"}");
        return message;
    }
}
