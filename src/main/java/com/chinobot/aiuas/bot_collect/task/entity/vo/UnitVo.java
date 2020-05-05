package com.chinobot.aiuas.bot_collect.task.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-14 17:23
 */
@ApiModel(description = "防治救单位")
@Data
public class UnitVo {

    @ApiModelProperty(value = "治理单位主键")
    private String manageUuid;

    @ApiModelProperty(value = "治理单位类型")
    private String manageUnitType;

    @ApiModelProperty(value = "治理负责人类型")
    private String managePersonType;

    @ApiModelProperty(value = "治理指定人主键")
    private String managePersonUuid;

    @ApiModelProperty(value = "治理指定人名称")
    private String managePersonName;

    @ApiModelProperty(value = "防范单位主键")
    private String againstUuid;

    @ApiModelProperty(value = "防范单位类型")
    private String againstUnitType;

    @ApiModelProperty(value = "防范单位类型")
    private String againstPersonType;

    @ApiModelProperty(value = "防范指定人主键")
    private String againstPersonUuid;

    @ApiModelProperty(value = "防范指定人名称")
    private String againstPersonName;

    @ApiModelProperty(value = "救援救灾单位主键")
    private String reliefUuid;

    @ApiModelProperty(value = "救援救灾单位类型")
    private String reliefUnitType;

    @ApiModelProperty(value = "救援救灾单位类型")
    private String reliefPersonType;

    @ApiModelProperty(value = "救援救灾指定人主键")
    private String reliefPersonUuid;

    @ApiModelProperty(value = "救援救灾指定人名称")
    private String reliefPersonName;
}
