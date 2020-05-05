package com.chinobot.plep.home.cycle.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "周期开关参数")
@Data
public class ChangeStateDto {

	@ApiModelProperty(value = "周期主键", required = true)
	private String uuid;
	
	@ApiModelProperty(value = "状态", required = true)
	private String state;
}
