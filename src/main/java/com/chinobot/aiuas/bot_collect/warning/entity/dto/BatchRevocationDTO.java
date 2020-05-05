package com.chinobot.aiuas.bot_collect.warning.entity.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "批量撤销")
@Data
public class BatchRevocationDTO {
	
	@ApiModelProperty(value = "预警事件id集合")
	private List<String> idList;
	
	@ApiModelProperty(value = "撤销人")
	private String  personId;
	
}
