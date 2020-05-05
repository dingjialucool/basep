package com.chinobot.plep.home.dataset.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class RuleGroupDto {

	private Integer groupNum;
	
	private String groupType;
	
	private List<RuleMetadataDto> fields;
}
