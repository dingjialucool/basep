package com.chinobot.aiuas.bot_collect.task.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-02-24 14:38
 */
@ApiModel("任务场景")
@Data
public class TaskSceneDto {
    @ApiModelProperty("任务主键")
    private String taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("航班主键")
    private String flightId;

    @ApiModelProperty("是否选中")
    private String selected;

    @ApiModelProperty("场景主键")
    private String sceneId;

    @ApiModelProperty("场景名称")
    private String sceneName;
}
