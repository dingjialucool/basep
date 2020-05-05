package com.chinobot.aiuas.bot_collect.strategy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "获取任务参数")
@Data
public class ListTaskDTO {

	@ApiModelProperty(value = "场景ID",required = true)
	private String sceneId;
	
	@ApiModelProperty(value = "任务名称",required = false)
	private String nameLike;
}
