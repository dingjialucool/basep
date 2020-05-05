package com.chinobot.aiuas.bot_collect.resource.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "天气信息")
@Data
public class WeatherVo {
	
  
    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "城市编码")
    private String adcode;

    @ApiModelProperty(value = "发布时间")
    private String reportTime;
    
    @ApiModelProperty(value = "刷新时间")
    private String createTime;

    @ApiModelProperty(value = "日期")
    private String castDate;

    @ApiModelProperty(value = "白天天气气象")
    private String dayWeather;

    @ApiModelProperty(value = "晚上天气气象")
    private String nightWeather;

    @ApiModelProperty(value = "白天温度")
    private String dayTemp;

    @ApiModelProperty(value = "晚上温度")
    private String nightTemp;

    @ApiModelProperty(value = "白天风向")
    private String dayWind;

    @ApiModelProperty(value = "晚上风向")
    private String nightWind;

    @ApiModelProperty(value = "白天风力")
    private String dayPower;

    @ApiModelProperty(value = "晚上风力")
    private String nightPower;
}
