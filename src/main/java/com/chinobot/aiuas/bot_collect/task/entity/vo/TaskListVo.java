package com.chinobot.aiuas.bot_collect.task.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-13 09:47
 */
@ApiModel(description = "采查任务列表")
@Data
public class TaskListVo {

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "任务名称")
    private String tName;

    @ApiModelProperty(value = "预警类型（1-事件，2-线索）")
    private String resultType;

    @ApiModelProperty(value = "预警名称")
    private String resultName;

    @ApiModelProperty(value = "风险等级")
    private String dangerGrade;

    @ApiModelProperty(value = "采查场景名称")
    private String sceneName;
}
