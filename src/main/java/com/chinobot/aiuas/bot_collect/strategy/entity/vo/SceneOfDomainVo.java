package com.chinobot.aiuas.bot_collect.strategy.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "领域下的场景")
@Data
public class SceneOfDomainVo {

	@ApiModelProperty(value = "领域主键")
	private String domainId;
	
	@ApiModelProperty(value = "场景主键")
	private String sceneId;
	
	@ApiModelProperty(value = "场景名称")
	private String sceneName;
}
