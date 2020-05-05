package com.chinobot.plep.home.report.entity.vo;



import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查报告-航线规划")
@Data
public class RouteLineOfCheckPointVo {
	
	@ApiModelProperty(value = "起飞点主键")
	private String uuid;
	
	@ApiModelProperty(value = "起飞点名称")
	private String checkPointName;
	
	@ApiModelProperty(value = "起飞点经度")
	private double longitude;
	
	@ApiModelProperty(value = "起飞点纬度")
	private double latitude;
	
	@ApiModelProperty(value = "起飞点下所有航线集合")
	private List<RouteLineVo> routeLines;
}
