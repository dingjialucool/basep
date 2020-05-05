package com.chinobot.plep.home.cycle.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "周期删除参数")
@Data
public class DeleteCycleDto {

	@ApiModelProperty(value = "周期主键", required = true)
	private String uuid;
	
	@ApiModelProperty(value = "是否删除已经生成的当日之后的任务", required = true)
	private boolean delAll;
}
