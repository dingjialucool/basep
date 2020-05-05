package com.chinobot.aiuas.bot_collect.strategy.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略搜索条件参数")
@Data
public class PageStrategyDTO {

	@ApiModelProperty(value = "名称",required = false)
	private String nameLike;
	
	@ApiModelProperty(value = "策略类型",required = false)
	private String typeEq;
	
	@ApiModelProperty(value = "状态",required = false)
	private String statusEq;
}
