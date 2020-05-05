package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略详细")
@Data
public class StrategyDetailVO {

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
	
	@ApiModelProperty(value = "生效时间")
	private String[] strategyPlan;
	
	@ApiModelProperty(value = "次数")
	private Integer workCount;
	
	private String strategyPlanStr;
	
	@ApiModelProperty(value = "策略描述")
	private String strategyDesc;
	
	@ApiModelProperty(value = "场景集合")
	private List<SceneWithIdsVO> scenes;
}
