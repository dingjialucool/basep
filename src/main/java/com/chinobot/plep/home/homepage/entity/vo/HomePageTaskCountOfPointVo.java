package com.chinobot.plep.home.homepage.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页起飞点任务数")
@Data
public class HomePageTaskCountOfPointVo {

	@ApiModelProperty(value = "起飞点名称")
	private String name;
	
	@ApiModelProperty(value = "经度")
	private Double longitude;
	
	@ApiModelProperty(value = "纬度")
	private Double latitude;
	
	@ApiModelProperty(value = "任务数")
	private Long taskCount;
}
