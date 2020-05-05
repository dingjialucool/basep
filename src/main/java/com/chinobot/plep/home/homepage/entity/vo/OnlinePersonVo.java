package com.chinobot.plep.home.homepage.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页区在线人员")
@Data
public class OnlinePersonVo {

	@ApiModelProperty(value = "在线人员主键")
	private String uuid;
	
	@ApiModelProperty(value = "经度")
	private double longitude;
	
	@ApiModelProperty(value = "维度")
	private double latitude;
	
	@ApiModelProperty(value = "状态 -- 1 在线 0 离线")
	private String onlineStatus;
	
	@ApiModelProperty(value = "姓名")
	private String pname;
	
	@ApiModelProperty(value = "头像文件")
	private String fileId;
	
	@ApiModelProperty(value = "人员职务")
	private String duties;
	
	@ApiModelProperty(value = "openId")
	private String openId;

	@ApiModelProperty(value = "无人机id")
	private String uavId;

	@ApiModelProperty(value = "无人机名称")
	private String uavName;
}
