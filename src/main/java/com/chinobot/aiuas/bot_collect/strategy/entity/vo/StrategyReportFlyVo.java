package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告-飞行巡查情况")
@Data
public class StrategyReportFlyVo {

	@ApiModelProperty(value = "采查对象")
	private String objectName;
	
	@ApiModelProperty(value = "任务总数")
	private Long taskCount;
	
	@ApiModelProperty(value = "飞行计划次数")
	private Long flyPlanCount;
	
	@ApiModelProperty(value = "完成飞行次数，飞行执行比例= 完成飞行次数/飞行计划次数*100")
	private Long flyCompletedCount;
	
	@ApiModelProperty(value = "预警数量")
	private Long warnCount;
}
