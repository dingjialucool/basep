package com.chinobot.aiuas.bot_collect.task.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-10 17:52
 */
@ApiModel(description = "领域场景列表")
@Data
public class DomainSceneVo {

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "类型（领域domain，场景scene）")
    private String type;
}
