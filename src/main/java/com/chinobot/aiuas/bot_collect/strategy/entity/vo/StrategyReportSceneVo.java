package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告-任务策略信息-任务策略")
@Data
public class StrategyReportSceneVo {

	@ApiModelProperty(value = "采查场景")
	private String sceneName;
	
	@ApiModelProperty(value = "采查任务集合")
	private List<String> taskName;
	
	@ApiModelProperty(value = "采查对象集合")
	private List<String> objectName;
	
}
