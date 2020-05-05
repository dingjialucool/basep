package com.chinobot.aiuas.bot_collect.strategy.entity.vo;



import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "带场景集合的领域")
@Data
public class DomainWithSceneVO {

	@ApiModelProperty(value = "主键")
	private String domainId;
	
	@ApiModelProperty(value = "领域名称")
	private String domainName;
	
	@ApiModelProperty(value = "场景集合")
	private List<SceneOfDomainVo> scenes;
}
