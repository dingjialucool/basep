package com.chinobot.plep.home.homepage.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "人员在线信息")
@Data
public class OnlinePersonDetailVo {

	@ApiModelProperty(value = "在线人员主键")
	private String uuid;
	
	@ApiModelProperty(value = "状态 -- 1 在线 0 离线")
	private String onlineStatus;
	
	@ApiModelProperty(value = "姓名")
	private String pname;
	
	@ApiModelProperty(value = "经度")
	private double longitude;
	
	@ApiModelProperty(value = "维度")
	private double latitude;
	
	
}
