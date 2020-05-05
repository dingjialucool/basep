package com.chinobot.plep.home.routedd.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "紧急任务")
@Data
public class UrgentDto {

	@ApiModelProperty(value = "无人机经度")
	private Double uavLng;
	
	@ApiModelProperty(value = "无人机纬度")
	private Double uavLat;
	
	@ApiModelProperty(value = "无人机地址")
	private String uavAddress;
	
	@ApiModelProperty(value = "目标点经度")
	private Double pointLng;

	@ApiModelProperty(value = "目标点纬度")
	private Double pointLat;
	
	@ApiModelProperty(value = "目标点地址")
	private String pointAddress;
	
	@ApiModelProperty(value = "无人机主键")
	private String uavId;
	
	@ApiModelProperty(value = "飞行员主键")
	private String flyPersonId;
	
	@ApiModelProperty(value = "飞行高度(m)")
	private Float flyHeight;
	
	@ApiModelProperty(value = "飞行员速度（m/s）")
	private Float flySpeed;
	
	@ApiModelProperty(value = "飞行续航（分钟）")
	private Float timeMax;
	
	@ApiModelProperty(value = "预计耗时(分钟)")
	private Float timeExpect;
	
	
}
