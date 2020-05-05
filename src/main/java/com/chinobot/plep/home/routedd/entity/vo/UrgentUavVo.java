package com.chinobot.plep.home.routedd.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "紧急任务无人机信息")
@Data
public class UrgentUavVo {

	@ApiModelProperty(value = "无人机主键")
	private String uavId;
	
	@ApiModelProperty(value = "飞行员主键")
	private String  flyPersonId;
	
	@ApiModelProperty(value = "无人机名称")
	private String  uavName;
	
	@ApiModelProperty(value = "飞行员名称")
	private String flyPersonName;
	
	@ApiModelProperty(value = "经度")
	private Double uavLng;
	
	@ApiModelProperty(value = "纬度")
	private Double uavLat;
	
	@ApiModelProperty(value = "运行状态")
	private String runStatus;
}
