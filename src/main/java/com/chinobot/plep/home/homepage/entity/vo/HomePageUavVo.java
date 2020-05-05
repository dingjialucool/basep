package com.chinobot.plep.home.homepage.entity.vo;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页无人机信息")
@Data
public class HomePageUavVo {
	
	@ApiModelProperty(value = "无人机主键")
	private String uuid;
	
	@ApiModelProperty(value = "无人机名称")
	private String ename;
	
	@ApiModelProperty(value = "无人机编码")
	private String ecode;
	
	@ApiModelProperty(value = "无人机运行状态")
	private String runStatus;
	
	@ApiModelProperty(value = "经度")
	private Double longitude;
	
	@ApiModelProperty(value = "纬度")
	private Double latitude;

	@ApiModelProperty(value = "飞行速度")
	private Float flightSpeed;
	
	@ApiModelProperty(value = "飞行高度")
	private Float flyingHeight;
	
	@ApiModelProperty(value = "设备上传视频id")
	private List<Map> FileLists;

	@ApiModelProperty(value = "人员外键")
	private String personUuid;

	@ApiModelProperty(value = "无人机类型名称")
	private String uavTypeName;
}
