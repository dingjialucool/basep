package com.chinobot.plep.home.event.entity.dto;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "批量分派")
@Data
public class EvenMainDto {

	
	@ApiModelProperty(value = "事件主键", required = true)
    private List<String> list;
	
	@ApiModelProperty(value = "确认人", required = true)
    private String peronPatrol;
	
	@ApiModelProperty(value = "状态='1'", required = true)
    private String status;
	
}
