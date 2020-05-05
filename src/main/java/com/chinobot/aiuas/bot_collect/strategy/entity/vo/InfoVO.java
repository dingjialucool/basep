package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略中的采查对象")
@Data
public class InfoVO {

	@ApiModelProperty(value = "对象主键")
	private String infoId;
	
	@ApiModelProperty(value = "对象名称")
	private String infoName;
	
	@ApiModelProperty(value = "对象经度")
	private Double infoLng;
	
	@ApiModelProperty(value = "对象纬度")
	private Double infoLat;
}
