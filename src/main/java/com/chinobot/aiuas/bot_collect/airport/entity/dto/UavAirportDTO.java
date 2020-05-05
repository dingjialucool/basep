package com.chinobot.aiuas.bot_collect.airport.entity.dto;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "机场增加无人机")
@Data
public class UavAirportDTO {

	@ApiModelProperty(value = "机场主键")
    private String airportId;
	
	@ApiModelProperty(value = "无人机id数组")
    private List<String> uavList;
	
}
