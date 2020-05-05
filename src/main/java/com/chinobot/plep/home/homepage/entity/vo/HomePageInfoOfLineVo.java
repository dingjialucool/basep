package com.chinobot.plep.home.homepage.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "航线规划-区域扫描 下 航线规划覆盖率，航线数量")
@Data
public class HomePageInfoOfLineVo {
	
	
	@ApiModelProperty(value = "边界坐标字符串")
	private String polygon;
	
	@ApiModelProperty(value = "区域边界坐标集合")
	private List<String> list;
	
	@ApiModelProperty(value = "航线数量")
	private Long num;
}
