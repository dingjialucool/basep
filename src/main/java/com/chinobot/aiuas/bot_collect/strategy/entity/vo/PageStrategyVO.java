package com.chinobot.aiuas.bot_collect.strategy.entity.vo;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略分页信息")
@Data
public class PageStrategyVO {

	@ApiModelProperty(value = "主键")
	private String strategyId;
	
	@ApiModelProperty(value = "策略名称")
	private String strategyName;
	
	@ApiModelProperty(value = "策略类型")
	private String strategyType;

	@ApiModelProperty(value = "策略值")
	private String strategyValue;
	
	@ApiModelProperty(value = "业务状态")
	private String businessStatus;
}
