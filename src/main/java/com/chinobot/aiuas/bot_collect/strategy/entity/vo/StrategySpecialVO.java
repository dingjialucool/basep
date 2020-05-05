package com.chinobot.aiuas.bot_collect.strategy.entity.vo;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "专项策略信息")
@Data
public class StrategySpecialVO {

	@ApiModelProperty(value = "主键")
	private String strategyId;
	
	@ApiModelProperty(value = "策略名称")
	private String strategyName;
	
	@ApiModelProperty(value = "策略类型")
	private String strategyType;

	@ApiModelProperty(value = "业务状态")
	private String businessStatus;
	
	@ApiModelProperty(value = "策略描述")
	private String strategyDesc;
	
	@ApiModelProperty(value = "计划节点")
	List<SpecialStrategyNodeVo> nodes;
}
