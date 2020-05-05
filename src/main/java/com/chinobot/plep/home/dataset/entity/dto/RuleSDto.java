package com.chinobot.plep.home.dataset.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "规则")
@Data
public class RuleSDto {

	@ApiModelProperty(value = "主键")
    private String uuid;

	@ApiModelProperty(value = "数据集主键")
    private String setId;

	@ApiModelProperty(value = "规则名称")
    private String name;

	@ApiModelProperty(value = "规则描述")
    private String decribe;

	@ApiModelProperty(value = "规则提示")
    private String tips;
    
	@ApiModelProperty(value = "场景主键，废弃")
    private String sceneId;
	
	@ApiModelProperty(value = "任务主键")
    private String taskId;

	@ApiModelProperty(value = "优先级")
    private Integer priority;
	
	@ApiModelProperty(value = "是否全局")
    private String isGlobal;
}
