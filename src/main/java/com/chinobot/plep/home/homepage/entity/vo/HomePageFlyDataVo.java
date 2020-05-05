package com.chinobot.plep.home.homepage.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页飞行次数 飞行里程")
@Data
public class HomePageFlyDataVo {
	
	
	
	@ApiModelProperty(value = "飞行次数")
	private int flyNum;
	
	@ApiModelProperty(value = "飞行里程")
	private double flyTotal;
}
