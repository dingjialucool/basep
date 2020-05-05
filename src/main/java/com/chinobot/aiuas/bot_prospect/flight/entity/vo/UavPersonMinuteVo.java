package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-02-25 12:00
 */
@ApiModel("无人机/飞手已安排时间")
@Data
public class UavPersonMinuteVo {

    @ApiModelProperty("无人机/飞手 id")
    private String uuid;
    @ApiModelProperty("无人机/飞手 名称")
    private String name;
    @ApiModelProperty("无人机/飞手 已安排时间")
    private Float minuteTotal;
    @ApiModelProperty("无人机/飞手 图片id")
    private String fileId;
}
