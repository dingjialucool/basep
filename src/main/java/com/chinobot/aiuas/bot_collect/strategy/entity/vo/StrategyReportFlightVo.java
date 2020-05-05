package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告-航班规划情况")
@Data
public class StrategyReportFlightVo {

	@ApiModelProperty(value = "航班名称")
	private String flightName;
	
	@ApiModelProperty(value = "航线经纬度集合")
	private String routeLnglats;
}
