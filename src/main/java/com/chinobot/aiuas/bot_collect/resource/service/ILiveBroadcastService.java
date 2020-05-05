package com.chinobot.aiuas.bot_collect.resource.service;

import com.chinobot.aiuas.bot_collect.resource.entity.LiveBroadcast;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.LiveBroadcastVO;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 直播 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-16
 */
public interface ILiveBroadcastService extends IBaseService<LiveBroadcast> {

	void saveLive(LiveBroadcastVO vo);

	LiveBroadcastVO getLive();
	
	String getSendUrl(String streamName, int days);

}
