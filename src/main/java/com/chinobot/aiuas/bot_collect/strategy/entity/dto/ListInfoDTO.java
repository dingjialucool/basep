package com.chinobot.aiuas.bot_collect.strategy.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "获取对象参数")
@Data
public class ListInfoDTO {

	@ApiModelProperty(value = "领域",required = true)
	private String domainId;
	
	@ApiModelProperty(value = "对象名称",required = false)
	private String nameLike;
}
