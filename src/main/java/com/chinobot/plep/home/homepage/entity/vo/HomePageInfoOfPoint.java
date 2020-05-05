package com.chinobot.plep.home.homepage.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "航线规划-区域扫描 下 航线规划覆盖率，航线数量")
@Data
public class HomePageInfoOfPoint {
	
	@ApiModelProperty(value = "航线总耗时")
	private String totalTime;
	
	@ApiModelProperty(value = "定点数量(与航线有关)")
	private Long pointNumOfline;
	
	@ApiModelProperty(value = "定点数量")
	private Long pointNum;
	
	@ApiModelProperty(value = "航线数量")
	private Long LineNum;
}
