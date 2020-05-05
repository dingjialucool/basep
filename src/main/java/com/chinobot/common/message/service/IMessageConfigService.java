package com.chinobot.common.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinobot.common.domain.Result;
import com.chinobot.common.message.entity.MessageConfig;
import com.chinobot.common.message.entity.dto.MessageConfigDTO;
import com.chinobot.common.message.entity.dto.MessagesDTO;
import com.chinobot.common.message.entity.vo.MessageConfigVo;
import com.chinobot.common.message.entity.vo.MessageListVO;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 消息类型配置 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-12-11
 */
public interface IMessageConfigService extends IBaseService<MessageConfig> {

	/**
	 * 获取模板配置列表
	 * @param dto
	 * @return
	 */
	IPage<MessageConfigVo> getMessageConfigList(MessageConfigDTO dto);

	/**
	 * 保存模板配置
	 * @param dto
	 */
	Result saveMessageConfig(MessageConfigVo dto);

	/**
	 * 获取模板配置信息
	 * @param uuid
	 * @return
	 */
	MessageConfigVo getMessageConfig(String uuid);

	/**
	 * 获取消息列表
	 * @param dto
	 * @return
	 */
	IPage<MessageListVO> getMessageList(MessagesDTO dto);

}
