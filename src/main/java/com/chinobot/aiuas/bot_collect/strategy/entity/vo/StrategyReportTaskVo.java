package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告-任务策略信息")
@Data
public class StrategyReportTaskVo {

	@ApiModelProperty(value = "策略名称")
	private String strategyName;
	
	@ApiModelProperty(value = "策略类型")
	private String strategyType;
	
	@ApiModelProperty(value = "策略描述")
	private String strategyDesc;
	
	@ApiModelProperty(value = "采查场景-----包含了采查任务和采查对象")
	private List<StrategyReportSceneVo> sceneVo;
	
}
