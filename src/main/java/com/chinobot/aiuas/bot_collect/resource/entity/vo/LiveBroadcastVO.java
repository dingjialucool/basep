package com.chinobot.aiuas.bot_collect.resource.entity.vo;


import com.chinobot.aiuas.bot_collect.resource.entity.LiveBroadcast;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "直播信息")
@Data
public class LiveBroadcastVO {

	@ApiModelProperty(value = "推流对象")
	private LiveBroadcast send;
	
	@ApiModelProperty(value = "播放对象")
	private LiveBroadcast play;
}
