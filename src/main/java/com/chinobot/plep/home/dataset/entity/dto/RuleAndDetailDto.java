package com.chinobot.plep.home.dataset.entity.dto;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "规则和规则明细集合")
@Data
public class RuleAndDetailDto {

	@ApiModelProperty(value = "规则")
	private RuleSDto ruleDto;
	
	@ApiModelProperty(value = "规则明细集合")
	private List<DetailDto> ruleDetails;
}
