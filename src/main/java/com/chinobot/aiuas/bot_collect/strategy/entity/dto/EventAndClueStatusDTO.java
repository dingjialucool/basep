package com.chinobot.aiuas.bot_collect.strategy.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告-事件|线索统计-中间")
@Data
public class EventAndClueStatusDTO {

	@ApiModelProperty(value = "事件主键")
	private String eventId;
	
	@ApiModelProperty(value = "事件|线索状态")
	private String businessStatus;
	
}
