package com.chinobot.aiuas.bot_prospect.flight.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("修改作业状态")
@Data
public class WorkStateDto {

	@ApiModelProperty(value="作业主键" , required = true)
	private String workId;
	
	@ApiModelProperty(value="状态，5:下达 2:撤销 3: 飞行结束   （后续有操作可继续拓展）" , required = true)
	private String state;
}
