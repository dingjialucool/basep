package com.chinobot.aiuas.bot_collect.airport.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "机场输入对象")
@Data
public class AirportDTO {

	@ApiModelProperty(value = "机场主键")
    private String uuid;
	
	@ApiModelProperty(value = "机场名称")
    private String airportName;
	
	@ApiModelProperty(value = "行政区划code")
    private String addressAreaCode;
	
	@ApiModelProperty(value = "详细地址")
    private String address;
	
}
