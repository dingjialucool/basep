package com.chinobot.aiuas.bot_collect.airport.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-03-06 15:41
 */
@ApiModel(description = "机场无人机人员")
@Data
public class AirportUavPersonVo {

    @ApiModelProperty(value = "机场无人机人员主键")
    private String id;

    @ApiModelProperty(value = "机场主键")
    private String airportUuid;

    @ApiModelProperty(value = "机场名称")
    private String airportName;

    @ApiModelProperty(value = "无人机主键")
    private String uavUuid;

    @ApiModelProperty(value = "无人机名称")
    private String uavName;

    @ApiModelProperty(value = "人员主键")
    private String personUuid;

    @ApiModelProperty(value = "人员名称")
    private String personName;
}
