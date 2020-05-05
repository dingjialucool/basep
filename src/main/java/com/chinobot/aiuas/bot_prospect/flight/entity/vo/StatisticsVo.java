package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-02-25 15:19
 */
@ApiModel("航班作业统计")
@Data
public class StatisticsVo {
    @ApiModelProperty("航班总数")
    private Integer flightTotal;

    @ApiModelProperty("策略总数")
    private Integer strategyTotal;

    @ApiModelProperty("无人机总数")
    private Integer uavTotal;

    @ApiModelProperty("驾驶员总数")
    private Integer personTotal;

    @ApiModelProperty("机场总数")
    private Integer airportTotal;

    @ApiModelProperty("飞行总时间")
    private Integer minuteTotal;

    @ApiModelProperty("待安排")
    private Integer standby;

    @ApiModelProperty("待执行")
    private Integer executed;

    @ApiModelProperty("待完成")
    private Integer completed;

    @ApiModelProperty("已完成")
    private Integer done;

    @ApiModelProperty("已取消")
    private Integer cancel;
}
