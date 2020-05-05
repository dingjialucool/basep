package com.chinobot.aiuas.bot_collect.airport.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "机场输出对象")
@Data
public class AirportVO {

	@ApiModelProperty(value = "机场主键")
    private String uuid;
	
	@ApiModelProperty(value = "机场名称")
    private String airportName;
	
	@ApiModelProperty(value = "行政区划名称")
    private String govAreaName;
	
	@ApiModelProperty(value = "详细地址")
    private String address;
	
	@ApiModelProperty(value = "机场下无人机数量")
    private Long uavNum;
	
	
	
}
