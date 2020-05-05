package com.chinobot.aiuas.bot_collect.resource.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "天气DTO")
@Data
public class WeatherDTO {
	
		@ApiModelProperty(value = "城市编码")
		private String adcode;
		
}
