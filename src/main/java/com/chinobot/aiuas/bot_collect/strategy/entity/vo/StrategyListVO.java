package com.chinobot.aiuas.bot_collect.strategy.entity.vo;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略列表信息")
@Data
public class StrategyListVO {

	@ApiModelProperty(value = "主键")
	private String strategyId;
	
	@ApiModelProperty(value = "策略名称")
	private String strategyName;
	
	@ApiModelProperty(value = "策略类型")
	private String strategyType;

	@ApiModelProperty(value = "航班数")
	private Integer flightCount;
}
