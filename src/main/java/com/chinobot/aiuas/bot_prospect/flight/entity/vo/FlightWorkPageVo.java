package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-02-21 18:04
 */
@ApiModel(description = "航班作业分页列表")
@Data
public class FlightWorkPageVo {

    @ApiModelProperty(value = "航班作业主键")
    private String workId;

    @ApiModelProperty(value = "航班主键")
    private String flightUuid;

    @ApiModelProperty(value = "航班名称")
    private String flightName;

    @ApiModelProperty(value = "策略主键")
    private String strategyUuid;

    @ApiModelProperty(value = "策略名称")
    private String strategyName;

    @ApiModelProperty(value = "无人机名称")
    private String uavName;

    @ApiModelProperty(value = "飞手名称")
    private String personName;

    @ApiModelProperty(value = "作业状态")
    private String workStatus;

    @ApiModelProperty(value = "机场名称")
    private String airportName;

    @ApiModelProperty(value = "起飞时间")
    private String flightTime;

    @ApiModelProperty(value = "飞行时间")
    private String flyMinute;

    @ApiModelProperty(value = "作业时间")
    private String workMinute;

}
