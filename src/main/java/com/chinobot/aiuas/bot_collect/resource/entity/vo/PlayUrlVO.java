package com.chinobot.aiuas.bot_collect.resource.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "直播信息")
@Data
public class PlayUrlVO {

	@ApiModelProperty(value = "清晰度")
	private String rateName;
	
	@ApiModelProperty(value = "播放地址")
	private String playUrl;
}
