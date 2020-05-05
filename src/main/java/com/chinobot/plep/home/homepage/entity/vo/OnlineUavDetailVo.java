package com.chinobot.plep.home.homepage.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "无人机在线信息")
@Data
public class OnlineUavDetailVo {

	@ApiModelProperty(value = "在线无人机主键")
	private String uuid;
	
	@ApiModelProperty(value = "状态 -- 1 在线 0 离线")
	private String runStatus;
	
	@ApiModelProperty(value = "无人机型号")
	private String ename;
	
	@ApiModelProperty(value = "经度")
	private double longitude;
	
	@ApiModelProperty(value = "维度")
	private double latitude;
	
	@ApiModelProperty(value = "高度")
	private String flyingHeight;
	
	@ApiModelProperty(value = "速度")
	private String flightSpeed;
	
}
