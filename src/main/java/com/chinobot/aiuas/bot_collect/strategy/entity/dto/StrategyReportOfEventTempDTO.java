package com.chinobot.aiuas.bot_collect.strategy.entity.dto;



import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告-事件|线索统计-中间")
@Data
public class StrategyReportOfEventTempDTO {

	@ApiModelProperty(value = "采查对象")
	private String objectName;
	
	@ApiModelProperty(value = "事件名称")
	private String eventName;
	
	@ApiModelProperty(value = "事件类型（1.事件2.线索）")
	private String eventType;
	
	@ApiModelProperty(value = "事件|线索状态）")
	private List<EventAndClueStatusDTO> eventAndClueDto;
	
}
