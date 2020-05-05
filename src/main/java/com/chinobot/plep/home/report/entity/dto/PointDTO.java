package com.chinobot.plep.home.report.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查报告-定点信息")
@Data
public class PointDTO {
	
	@ApiModelProperty(value = "主键")
	private String pointId;
	
	@ApiModelProperty(value = "定点名称")
	private String pointName;
	
	@ApiModelProperty(value = "定点经纬度")
	private String center;
	
}
