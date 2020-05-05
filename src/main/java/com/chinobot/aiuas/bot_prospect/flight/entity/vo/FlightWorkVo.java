package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-03-06 15:57
 */
@ApiModel(description = "航班作业时间")
@Data
public class FlightWorkVo {

    @ApiModelProperty(value = "航班作业主键")
    private String id;

    @ApiModelProperty(value = "机场无人机人员主键")
    private String resourceId;

    @ApiModelProperty(value = "航班名称")
    private String title;

    @ApiModelProperty(value = "航班主键")
    private String flightUuid;

    @ApiModelProperty(value = "无人机主键")
    private String uavUuid;

    @ApiModelProperty(value = "人员主键")
    private String personUuid;

//    @ApiModelProperty(value = "航班日期")
//    private String flightDate;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime start;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime end;

    @ApiModelProperty(value = "是否删除： 0-未删除 1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "1:待安排 2:待执行 3: 待完成 4：已完成 0：已取消 5:执行中")
    private String workStatus;


}
