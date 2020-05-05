package com.chinobot.plep.home.cycle.entity.dto;


import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "周期查询参数")
@Data
public class RouteSearchDto {

	@ApiModelProperty(value = "部门", required = false)
	private String deptId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "路线日期最大值", required = false)
	private LocalDate timeMax;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "路线日期最小值", required = false)
	private LocalDate timeMin;
	
	@ApiModelProperty(value = "路线名称", required = false)
	private String routeName;
	
	@ApiModelProperty(value = "飞手名称", required = false)
	private String personName;
	
	@ApiModelProperty(value = "无人机名称", required = false)
	private String uavName;
	
	@ApiModelProperty(value = "计划名称", required = false)
	private String planName;
}
