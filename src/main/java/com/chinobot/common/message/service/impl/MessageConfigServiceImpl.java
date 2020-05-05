package com.chinobot.common.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.message.entity.MessageConfig;
import com.chinobot.common.message.entity.dto.MessageConfigDTO;
import com.chinobot.common.message.entity.dto.MessagesDTO;
import com.chinobot.common.message.entity.vo.MessageConfigVo;
import com.chinobot.common.message.entity.vo.MessageListVO;
import com.chinobot.common.message.mapper.MessageConfigMapper;
import com.chinobot.common.message.service.IMessageConfigService;
import com.chinobot.framework.web.service.impl.BaseService;
import com.drew.lang.StringUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息类型配置 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-12-11
 */
@Service
public class MessageConfigServiceImpl extends BaseService<MessageConfigMapper, MessageConfig> implements IMessageConfigService {

	@Autowired
	private MessageConfigMapper messageConfigMapper;
	@Autowired
    private IMessageConfigService messageConfigService;
	
	@Override
	public IPage<MessageConfigVo> getMessageConfigList(MessageConfigDTO dto) {
		
		if (dto == null) {
			dto = new MessageConfigDTO();
		}
		Page page  = new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		
		return messageConfigMapper.getMessageConfigList(page,dto);
	}

	@Override
	public Result saveMessageConfig(MessageConfigVo dto) {
		Result result = new Result();
		if (dto == null) {
			result.setCode(400);
			result.setMsg("保存失败");
			return result;
		}
		
		List<MessageConfig> list = messageConfigService.list(new LambdaQueryWrapper<MessageConfig>().eq(MessageConfig::getDataStatus, GlobalConstant.DATA_STATUS_VALID).eq( MessageConfig::getCode, dto.getModuleCode()));
		
		if (StringUtils.isEmpty(dto.getUuid())) {
			if (list.size() > 0) {
				result.setCode(400);
				result.setMsg("保存失败,模板编码已存在");
				return result;
			}
		}else {
			MessageConfig messageConfig = messageConfigService.getById(dto.getUuid());
			if(!messageConfig.getCode().equals(dto.getModuleCode())) {
				if (list.size() > 0) {
					result.setCode(400);
					result.setMsg("保存失败,模板编码已存在");
					return result;
				}
			}
		}
		
		MessageConfig messageConfig = new MessageConfig();
		messageConfig.setUuid(dto.getUuid())
		.setCode(dto.getModuleCode())
		.setName(dto.getModuleName())
		.setOpenType(dto.getOpenType())
		.setTemplate(dto.getTemplate())
		.setUrl(dto.getUrl());
		
		messageConfigService.saveOrUpdate(messageConfig);
		
		result.setCode(200);
		result.setMsg("保存成功");
		return result;
	}

	@Override
	public MessageConfigVo getMessageConfig(String uuid) {
		
		return messageConfigMapper.getMessageConfig(uuid);
	}

	@Override
	public IPage<MessageListVO> getMessageList(MessagesDTO dto) {
		
		if (dto == null) {
			dto = new MessagesDTO();
		}
		Page page  = new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		return messageConfigMapper.getMessageList(page,dto);
	}

}
