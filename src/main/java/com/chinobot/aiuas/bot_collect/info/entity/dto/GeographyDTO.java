package com.chinobot.aiuas.bot_collect.info.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "地理信息")
@Data
public class GeographyDTO {

	@ApiModelProperty(value = "主键")
    private String geographyId;
	
	@ApiModelProperty(value = "经度")
    private double lng;
	
	@ApiModelProperty(value = "纬度")
    private double lat;
	
	@ApiModelProperty(value = "经纬度集合：;分隔")
    private String lnglats;
	
	@ApiModelProperty(value = "面积(k㎡)")
	private double area;
}
