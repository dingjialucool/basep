package com.chinobot.common.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.mapper.PersonMapper;
import com.chinobot.common.message.entity.Message;
import com.chinobot.common.message.entity.MessageConfig;
import com.chinobot.common.message.entity.dto.MessageDto;
import com.chinobot.common.message.entity.vo.MessageVO;
import com.chinobot.common.message.mapper.MessageConfigMapper;
import com.chinobot.common.message.mapper.MessageMapper;
import com.chinobot.common.message.service.IMessageService;
import com.chinobot.common.message.utils.FreeMarkerUtils;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.meeting.entity.vo.PersonVo;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-12-11
 */
@Service
public class MessageServiceImpl extends BaseService<MessageMapper, Message> implements IMessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MessageConfigMapper messageConfigMapper;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private PersonMapper personMapper;

    private static final String KAFKA_KEY = "global_msg";

    @Override
    public void sendMessage(MessageDto dto, Map<String, Object> customParam) throws IOException, TemplateException {
        QueryWrapper<MessageConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("code", dto.getCode());
        MessageConfig messageConfig = (MessageConfig) messageConfigMapper.selectOne(wrapper);
        PersonVo sendPerson = personMapper.personVoById(dto.getSendPid());
        PersonVo receivePerson = personMapper.personVoById(dto.getReceivePid());
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
        message.setUrl(messageConfig.getUrl() + "?" + dto.getUrlParams());
        message.setContent(content);
        messageMapper.insert(message);
        // 推送消息
        kafkaTemplate.sendDefault(KAFKA_KEY, "{\"userId\":\"" + dto.getReceivePid() + "\"}");
    }

//    public static void main(String[] args) throws IOException, TemplateException {
//        String s ="11_${commonParam.sendTime}, \n22_${commonParam.sendPid}, 33_${customParam.aa!}";
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        Map<String, Object> commonParam = new HashMap();
//        commonParam.put("sendTime", now.format(formatter));
//        commonParam.put("sendPid", "sendPid");
//
//        Map<String, Object> customParam = new HashMap<>();
//        customParam.put("a", "aaa");
//
//        Map params = new HashMap();
//        params.put("commonParam", commonParam);
//        params.put("customParam", customParam);
//        String content = FreeMarkerUtils.process(s, params, null);
//        System.out.println(content);
//    }

	@Override
	public IPage<MessageVO> pageMyMessage(Page page, Map<String, Object> param) {
		param.put("receivePid", ThreadLocalUtil.getResources().getUuid());
		return messageMapper.getMyMessage(page, param);
	}

	@Override
	public List<MessageVO> listMyMessageUnRead() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("receivePid", ThreadLocalUtil.getResources().getUuid());
		param.put("isRead", "0");
		return messageMapper.getMyMessage(param);
	}

	@Override
	public void readMessage(String uuid) {
		UpdateWrapper<Message> updateWrapper = new UpdateWrapper<Message>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.eq("data_status", "1");
		updateWrapper.eq("is_read", 0);
		Message entity = new Message();
		entity.setIsRead("1");
		this.update(entity, updateWrapper);
	}

	@Override
	public MessageVO getMessageVOById(String uuid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uuid", uuid);
		List<MessageVO> myMessage = messageMapper.getMyMessage(param);
		if(myMessage.size()>0) {
			return myMessage.get(0);
		}
		return null;
	}

}
