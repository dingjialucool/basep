package com.chinobot.aiuas.bot_collect.resource.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "天气管理标签页城市信息")
@Data
public class WeatherCityInfoVo {
	
  
    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "城市编码")
    private String adcode;
    
    @ApiModelProperty(value = "刷新时间")
    private String createTime;

    
}
