package com.chinobot.aiuas.bot_collect.airport.entity.dto;



import com.chinobot.aiuas.bot_collect.info.entity.dto.GeographyDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "机场+地址库")
@Data
public class AirportGeographyDTO {

	@ApiModelProperty(value = "机场对象")
	private AirportDTO airportDTO;
	
	@ApiModelProperty(value = "地址库对象")
	private GeographyDTO geography;
	
}
