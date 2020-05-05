package com.chinobot.aiuas.bot_collect.strategy.entity.vo;




import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告-事件|线索统计")
@Data
public class StrategyReportOfEventVo {

	@ApiModelProperty(value = "采查对象")
	private String objectName;
	
	@ApiModelProperty(value = "事件名称")
	private String eventName;
	
	@ApiModelProperty(value = "事件类型（1.事件2.线索）")
	private String eventType;
	
	@ApiModelProperty(value = "总数")
	private Integer eventCount;
	
	@ApiModelProperty(value = "已确认数")
	private Integer qrCount;
	
	@ApiModelProperty(value = "已核查数(线索没有)")
	private Integer hcCount;
	
	@ApiModelProperty(value = "已治理数(线索没有)")
	private Integer zlCount;
	
	@ApiModelProperty(value = "已办结数(线索没有)")
	private Integer bjCount;
}
