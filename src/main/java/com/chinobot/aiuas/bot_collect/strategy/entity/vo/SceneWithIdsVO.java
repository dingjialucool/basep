package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "带有选中id的场景")
@Data
public class SceneWithIdsVO {

	@ApiModelProperty(value = "领域主键")
	private String domainId;
	
	@ApiModelProperty(value = "场景主键")
	private String sceneId;
	
	@ApiModelProperty(value = "场景名称")
	private String sceneName;
	
	@ApiModelProperty(value = "任务ID集合")
	private List<String> taskIds;
	
	@ApiModelProperty(value = "对象ID集合")
	private List<String> infoIds;
}
