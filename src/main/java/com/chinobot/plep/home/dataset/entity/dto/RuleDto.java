package com.chinobot.plep.home.dataset.entity.dto;


import java.util.List;

import lombok.Data;

@Data
public class RuleDto {
	
	private String uuid;
	
	private String setId;
	
	private String taskId;
	
	private Integer priority;
	
	private String isGlobal;
	
	private List<RuleGroupDto> groups;

}
