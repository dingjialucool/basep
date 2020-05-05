package com.chinobot.aiuas.bot_collect.task.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-09 09:58
 */
@ApiModel(description = "采查领域列表")
@Data
public class DomainListVo {

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "名称")
    private String dName;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "父类外键")
    private String parentUuid;
}
