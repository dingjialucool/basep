package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("航班明细")
@Data
public class FlightDeatilVO {
	
	@ApiModelProperty(value = "航班主键")
	private String flightId;
	
	@ApiModelProperty(value = "航班名称")
	private String flightName;

	@ApiModelProperty(value = "任务集合")
	private List<IdNameVo> tasks;
	
	@ApiModelProperty(value = "对象集合")
	private List<IdNameVo> infos;
}
