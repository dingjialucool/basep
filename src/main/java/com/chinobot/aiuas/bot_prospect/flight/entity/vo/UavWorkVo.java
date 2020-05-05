package com.chinobot.aiuas.bot_prospect.flight.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "无人机作业信息")
@Data
public class UavWorkVo {

	@ApiModelProperty(value = "无人机主键")
	private String uavCode;

	@ApiModelProperty(value = "是否处于作业：1-是 0-否")
	private String isWork;
	
	@ApiModelProperty(value = "作业信息明细")
	private UavWorkDetailVo workDetail;
}
