package com.chinobot.aiuas.bot_collect.strategy.entity.dto;

import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "专项策略保存")
@Data
public class StrategySpecialDto {

	@ApiModelProperty(value = "主键")
	private String strategyId;
	
	@ApiModelProperty(value = "策略名称")
	private String strategyName;
	
	@ApiModelProperty(value = "策略类型")
	private String strategyType;

	@ApiModelProperty(value = "业务状态")
	private String businessStatus;
	
	@ApiModelProperty(value = "策略描述")
	private String strategyDesc;
	
	@ApiModelProperty(value = "删除的计划id")
	private List<String> delUuids;
	
	@ApiModelProperty(value = "新增-add/修改-update")
	private String mode;
	
	@ApiModelProperty(value = "新增的计划节点")
	private List<SpecialStrategyNodeDto> addNodes;
}
