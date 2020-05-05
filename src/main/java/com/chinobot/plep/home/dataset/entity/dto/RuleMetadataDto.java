package com.chinobot.plep.home.dataset.entity.dto;

import lombok.Data;

@Data
public class RuleMetadataDto {
	
	private String detailId;

	private String relationType;
	
	private String compareType;
	
	private String compareValue;
	
	private String field;
	
	private Integer fieldType;
	
	private Integer innerSort;
}
