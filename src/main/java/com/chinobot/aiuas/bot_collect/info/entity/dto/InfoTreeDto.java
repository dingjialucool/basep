package com.chinobot.aiuas.bot_collect.info.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查对象树")
@Data
public class InfoTreeDto {

	@ApiModelProperty(value = "对象ID")
	private String infoId;
	
	@ApiModelProperty(value = "对象名称")
	private String infoName;

	@ApiModelProperty(value = "对象经纬度集合")
	private String infoLnglats;

	@ApiModelProperty(value = "对象中心点经度")
	private String infoLng;

	@ApiModelProperty(value = "对象中心点纬度")
	private String infoLat;

	@ApiModelProperty(value = "领域ID")
	private String domainId;

	@ApiModelProperty(value = "领域名称")
	private String domainName;
}
