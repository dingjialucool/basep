package com.chinobot.plep.home.point.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "GPS信息")
@Data
public class LnglatVo {

	@ApiModelProperty(value = "经度")
	private Double lng;
	
	@ApiModelProperty(value = "纬度")
	private Double lat;
	
	@ApiModelProperty(value = "高度")
	private Float height;
}
