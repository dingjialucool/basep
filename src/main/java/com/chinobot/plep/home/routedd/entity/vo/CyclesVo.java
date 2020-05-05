package com.chinobot.plep.home.routedd.entity.vo;


import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "周期列表")
@Data
public class CyclesVo {

	@ApiModelProperty(value = "周期计划主键")
    private String uuid;
	
	@ApiModelProperty(value = "时间起")
    private LocalDate timeStart;

	@ApiModelProperty(value = "时间止")
    private LocalDate timeEnd;

	@ApiModelProperty(value = "周期")
    private Integer cycle;

	@ApiModelProperty(value = "周期单位：1小时 2天 3周")
    private String cycleUnit;
	
	@ApiModelProperty(value = "计划名称")
    private String planName;
	
	@ApiModelProperty(value = "状态")
	private String useStatus;
	
	@ApiModelProperty(value = "操作人")
	private String operateName;
	
	@ApiModelProperty(value = "操作时间")
	private LocalDateTime operateTime;
}
