package com.chinobot.aiuas.bot_collect.strategy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "删除策略参数")
@Data
public class DelStategyDTO {

	@ApiModelProperty(value = "策略主键", required = true)
	private String strategyId;
}
