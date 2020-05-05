package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告列表对象")
@Data
public class StrategyReportVo {

	@ApiModelProperty(value = "策略主键")
	private String uuid;
	
	@ApiModelProperty(value = "策略名称")
	private String strategyName;
	
	@ApiModelProperty(value = "策略类型")
	private String strategyType;
	
	@ApiModelProperty(value = "对象数")
	private Long objectCount;
	
	@ApiModelProperty(value = "任务数")
	private Long taskCount;
	
	@ApiModelProperty(value = "航班数")
	private Long flightCount;
}
