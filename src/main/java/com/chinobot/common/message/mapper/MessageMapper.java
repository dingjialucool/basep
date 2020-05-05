package com.chinobot.common.message.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.message.entity.Message;
import com.chinobot.common.message.entity.vo.MessageVO;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 消息 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-12-11
 */
public interface MessageMapper extends IBaseMapper<Message> {

	IPage<MessageVO> getMyMessage(Page page, @Param("p") Map<String, Object> param);
	
	List<MessageVO> getMyMessage(@Param("p") Map<String, Object> param);
}
