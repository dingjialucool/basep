package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "专项策略节点信息")
@Data
public class SpecialStrategyNodeVo {

	@ApiModelProperty(value = "计划节点主键")
	private String nodeId;
	
	@ApiModelProperty(value = "飞行日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate flyDate;
	
	@ApiModelProperty(value = "飞行次数")
	private Integer count;
	
	@ApiModelProperty(value = "航班信息")
	private FlightDeatilVO flight;
}
