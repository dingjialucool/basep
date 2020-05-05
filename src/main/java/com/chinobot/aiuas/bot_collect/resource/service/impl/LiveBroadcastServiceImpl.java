package com.chinobot.aiuas.bot_collect.resource.service.impl;

import com.chinobot.aiuas.bot_collect.resource.entity.LiveBroadcast;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.LiveBroadcastVO;
import com.chinobot.aiuas.bot_collect.resource.mapper.LiveBroadcastMapper;
import com.chinobot.aiuas.bot_collect.resource.service.ILiveBroadcastService;
import com.chinobot.aiuas.bot_collect.resource.utils.QcloudUtils;
import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.service.impl.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 直播 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-16
 */
@Service
public class LiveBroadcastServiceImpl extends BaseService<LiveBroadcastMapper, LiveBroadcast> implements ILiveBroadcastService {

	@Autowired
	private IReUavService uavService;
	
	@Override
	public void saveLive(LiveBroadcastVO vo) {
		LiveBroadcast entity = new LiveBroadcast();
		entity.setIsDeleted(true);
		this.update(entity , CommonUtils.getEqUpdateWrapper("is_deleted", 0));
		LiveBroadcast play = vo.getPlay();
		play.setUuid(null);
		play.setCreateBy(null);
		play.setCreateTime(null);
		play.setType("2");
		this.save(play);
		LiveBroadcast send = vo.getSend();
		send.setUuid(null);
		send.setCreateBy(null);
		send.setCreateTime(null);
		send.setType("1");
		this.save(send);
	}

	@Override
	public LiveBroadcastVO getLive() {
		LiveBroadcastVO vo = new LiveBroadcastVO();
		vo.setSend(this.getOne(CommonUtils.getEqQueryWrapper("is_deleted","type",0, "1")));
		vo.setPlay(this.getOne(CommonUtils.getEqQueryWrapper("is_deleted","type",0, "2")));
		return vo;
	}

	@Override
	public String getSendUrl(String streamName, int days) {
		Uav uav = uavService.getById(streamName);
		if(uav != null) {
			LiveBroadcast live = this.getOne(CommonUtils.getEqQueryWrapper("is_deleted","type",0, "1"));
			return QcloudUtils.getQcloudUrl(live.getApiKey(), live.getDomainName(), streamName, days);
		}
		return null;
	}

}
