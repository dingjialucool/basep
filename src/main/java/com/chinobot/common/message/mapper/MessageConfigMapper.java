package com.chinobot.common.message.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.PersonListVo;
import com.chinobot.common.message.entity.MessageConfig;
import com.chinobot.common.message.entity.dto.MessageConfigDTO;
import com.chinobot.common.message.entity.dto.MessagesDTO;
import com.chinobot.common.message.entity.vo.MessageConfigVo;
import com.chinobot.common.message.entity.vo.MessageListVO;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 消息类型配置 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-12-11
 */
public interface MessageConfigMapper extends IBaseMapper<MessageConfig> {

	/**
	 * 获取模板配置列表
	 * @param page
	 * @param dto
	 * @return
	 */
	IPage<MessageConfigVo> getMessageConfigList(Page page,@Param("p") MessageConfigDTO dto);

	/**
	 * 获取模板消息配置
	 * @param uuid
	 * @return
	 */
	MessageConfigVo getMessageConfig(String uuid);

	/**
	 * 获取消息列表
	 * @param dto
	 * @return
	 */
	IPage<MessageListVO> getMessageList(Page page,@Param("p") MessagesDTO dto);

	/**
	 * 获取发起人集合
	 * @return
	 */
	List<PersonListVo> getSendByList();
	
	/**
	 * 获取接收人集合
	 * @return
	 */
	List<PersonListVo> getRecevieList();

}
