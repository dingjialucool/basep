package com.chinobot.aiuas.bot_collect.task.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-15 09:22
 */
@ApiModel(description = "防治救单位")
@Data
public class UnitDto {

    @ApiModelProperty(value = "防治救单位主键")
    private String uuid;

    @ApiModelProperty(value = "采查任务主键")
    private String taskUuid;

    @ApiModelProperty(value = "负责人类型")
    private String unitPersonType;

    @ApiModelProperty(value = "负责人主键")
    private String unitPersonUuid;

    @ApiModelProperty(value = "单位类型")
    private String unitType;

    @ApiModelProperty(value = "机构")
    private String organization;

    @ApiModelProperty(value = "负责人姓名")
    private String pname;
}
