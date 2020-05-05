package com.chinobot.aiuas.bot_collect.strategy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "改变策略状态参数")
@Data
public class ChangeStatusDTO {

	@ApiModelProperty(value = "策略主键", required = true)
	private String strategyId;
	
	@ApiModelProperty(value = "状态", required = true)
	private String businessStatus;
}
