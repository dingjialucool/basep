package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略中的采查任务")
@Data
public class TaskVO {

	@ApiModelProperty(value = "任务主键")
	private String taskId;
	
	@ApiModelProperty(value = "任务名称")
	private String taskName;
}
