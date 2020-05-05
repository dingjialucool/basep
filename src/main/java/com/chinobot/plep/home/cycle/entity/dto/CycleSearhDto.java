package com.chinobot.plep.home.cycle.entity.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "周期查询参数")
@Data
public class CycleSearhDto {
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "开始日期最大值", required = false)
	private LocalDate startTimeMax;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "开始日期最小值", required = false)
	private LocalDate startTimeMin;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "截至日期最大值", required = false)
	private LocalDate endTimeMax;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "截至日期最小值", required = false)
	private LocalDate endTimeMin;
	
	@ApiModelProperty(value = "名称模糊查询", required = false)
	private String nameLike;
	
	@ApiModelProperty(value = "周期最大值", required = false)
	private Integer cycleMax;
	
	@ApiModelProperty(value = "周期最小值", required = false)
	private Integer cycleMin;
	
	@ApiModelProperty(value = "周期单位", required = false)
	private String unit;
	
	@ApiModelProperty(value = "状态", required = false)
	private String userStatus;
	
	@ApiModelProperty(value = "部门", required = false)
	private String deptId;
}
