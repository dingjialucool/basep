package com.chinobot.plep.home.cycle.entity.vo;


import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "路线信息")
@Data
public class RouteVo {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "路线名称")
	private String routeName;
	
	@ApiModelProperty(value = "调度时间")
	private LocalDate routeDate;
	
	@ApiModelProperty(value = "总耗时")
	private Float timeAll;
	
	@ApiModelProperty(value = "无人机名称")
	private String uavName;
	
	@ApiModelProperty(value = "飞行员名称")
	private String personName;
	
	/*
	 * @ApiModelProperty(value = "周期名称") private String cycleName;
	 */
	
	@ApiModelProperty(value = "路线明细")
	private List<RouteDetailVo> list;
}
